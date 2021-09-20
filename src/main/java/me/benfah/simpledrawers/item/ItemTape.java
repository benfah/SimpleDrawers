package me.benfah.simpledrawers.item;

import me.benfah.simpledrawers.api.drawer.BlockAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.api.drawer.holder.ItemHolder;
import me.benfah.simpledrawers.init.SDItems;
import me.benfah.simpledrawers.utils.ITapeable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;

public class ItemTape extends Item implements DrawerInteractable
{



    public ItemTape(Settings settings)
    {
        super(settings);
    }

    @Override
    public void interact(BlockEntityAbstractDrawer drawer, PlayerEntity player, ItemHolder holder)
    {
        BlockState state = drawer.getWorld().getBlockState(drawer.getPos());



        ItemStack stack = new ItemStack(SDItems.PACKAGED_DRAWER);
        ((BlockAbstractDrawer) state.getBlock()).toTapeTag(stack.getOrCreateSubNbt("DrawerInfo"), drawer);
        stack.getNbt().putString("Id", Registry.BLOCK.getId(state.getBlock()).toString());

        drawer.getWorld().spawnEntity(new ItemEntity(drawer.getWorld(), drawer.getPos().getX(), drawer.getPos().getY(), drawer.getPos().getZ(), stack));
        drawer.getWorld().setBlockState(drawer.getPos(), Blocks.AIR.getDefaultState());
        player.getMainHandStack().damage(1, player, playerCallback -> playerCallback.sendToolBreakStatus(Hand.MAIN_HAND));
    }
}
