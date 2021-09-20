package me.benfah.simpledrawers.api.drawer.blockentity;

import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.CombinedInventoryHandler;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BlockEntityAbstractDrawer extends BlockEntity implements BlockEntityClientSerializable
{

    public BlockEntityAbstractDrawer(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public abstract ItemHolder getItemHolderAt(float x, float y);

    public abstract List<ItemHolder> getItemHolders();

    public abstract void setItemHolders(List<ItemHolder> holders);

    public abstract CombinedInventoryHandler getInventoryHandler();

    public void tick()
    {
        if(!world.isClient)
            getItemHolders().forEach((holder) -> holder.getInventoryHandler().transferItems());
    }

    @Override
    public void sync()
    {
        markDirty();
        BlockEntityClientSerializable.super.sync();
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        fromClientTag(nbt);
        super.readNbt(nbt);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag)
    {
        toClientTag(tag);
        return super.writeNbt(tag);
    }

    @Override
    public void fromClientTag(NbtCompound tag)
    {
        List<ItemHolder> holders = new ArrayList<>();
        if(tag.contains("Holder"))
        {
            holders.add(ItemHolder.fromNBT(tag.getCompound("Holder"), this));
        } else if(tag.contains("Holders"))
        {
            NbtList listTag = tag.getList("Holders", 10);
            holders.addAll(listTag.stream()
                    .map((holderTag) -> ItemHolder.fromNBT((NbtCompound) holderTag, this)).collect(Collectors.toList()));
        }
        if(!holders.isEmpty())
            setItemHolders(holders);
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag)
    {
        List<NbtCompound> holderList = getItemHolders().stream().map((holder) -> holder.toNBT(new NbtCompound()))
                .collect(Collectors.toList());
        NbtList listTag = new NbtList();
        listTag.addAll(holderList);
        tag.put("Holders", listTag);
        return tag;
    }

}
