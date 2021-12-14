package me.benfah.simpledrawers.api.drawer.blockentity;

import me.benfah.simpledrawers.api.drawer.holder.CombinedInventoryHandler;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockEntityAbstractDrawer extends BlockEntity
{

    public BlockEntityAbstractDrawer(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public abstract ItemHolder getItemHolderAt(float x, float y);

    public abstract List<ItemHolder> getItemHolders();

    public abstract void setItemHolders(List<ItemHolder> holders);

    public abstract CombinedInventoryHandler getInventoryHandler();

    public static void serverTick(World world, BlockPos pos, BlockState state, BlockEntityAbstractDrawer blockEntity)
    {
        blockEntity.getItemHolders().forEach((holder) -> holder.getInventoryHandler().transferItems());
    }

    // needed to sync to client
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    // needed to sync to client
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void sync()
    {
        markDirty();

        // sync to client
        BlockEntity blockEntity = this;
        if (blockEntity.hasWorld() && !blockEntity.getWorld().isClient)
            ((ServerWorld) blockEntity.getWorld()).getChunkManager().markForUpdate(blockEntity.getPos());
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        readClientNbt(nbt);
        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt)
    {
        writeClientNbt(nbt);
        super.writeNbt(nbt);
    }

    public void readClientNbt(NbtCompound nbt)
    {
        List<ItemHolder> holders = new ArrayList<>();
        if(nbt.contains("Holder"))
        {
            holders.add(ItemHolder.fromNBT(nbt.getCompound("Holder"), this));
        } else if(nbt.contains("Holders"))
        {
            NbtList listTag = nbt.getList("Holders", 10);
            holders.addAll(listTag.stream()
                    .map((holderTag) -> ItemHolder.fromNBT((NbtCompound) holderTag, this)).toList());
        }
        if(!holders.isEmpty())
            setItemHolders(holders);
    }

    public void writeClientNbt(NbtCompound nbt)
    {
        List<NbtCompound> holderList = getItemHolders().stream().map((holder) -> holder.toNBT(new NbtCompound())).toList();
        NbtList listTag = new NbtList();
        listTag.addAll(holderList);
        nbt.put("Holders", listTag);
    }
}
