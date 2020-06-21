package me.benfah.simpledrawers.utils;

import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

public interface ITapeable<T extends BlockEntityAbstractDrawer>
{

    default CompoundTag toTapeTag(CompoundTag tag, T drawer)
    {
        ListTag holderTag = drawer.getItemHolders().stream().map(holder -> holder.toNBT(new CompoundTag())).collect(Collectors.toCollection(ListTag::new));

        String variantString = ModelUtils.variantMapToString(drawer.getCachedState().getEntries());
        tag.putString("Id", Registry.BLOCK.getId(drawer.getCachedState().getBlock()).toString());
        tag.put("ItemHolders", holderTag);
        tag.putString("Variant", variantString);

        return tag;
    }

    default void setBlockFromTape(CompoundTag tag, World world, BlockPos pos, PlayerEntity player)
    {
        Block drawer = Registry.BLOCK.get(new Identifier(tag.getString("Id")));
        BlockState blockToSet = PropertyStateConverter.fromPropertyString(drawer, tag.getString("Variant")).with(BlockAbstractDrawer.FACING, player.getHorizontalFacing().getOpposite());

        world.setBlockState(pos, blockToSet);

        List<ItemHolder> holders = tag.getList("ItemHolders", NbtType.COMPOUND).stream().map(childTag -> ItemHolder.fromNBT((CompoundTag) childTag, (BlockEntityAbstractDrawer) world.getBlockEntity(pos))).collect(Collectors.toList());

        ((BlockEntityAbstractDrawer)world.getBlockEntity(pos)).setItemHolders(holders);
    }

}
