package me.benfah.simpledrawers.api.drawer.holder;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CombinedInventoryHandler implements SidedInventory
{

    Supplier<List<ItemHolder>> holderSupplier;

    List<Pair<ItemHolder, Integer>> slotMap = new ArrayList<>();

    public CombinedInventoryHandler(Supplier<List<ItemHolder>> holderSupplier)
    {
        this.holderSupplier = holderSupplier;
        generateSlotList();
    }

    public void generateSlotList()
    {
        slotMap.clear();
        for(ItemHolder holder : holderSupplier.get())
        {
            for(int i : holder.getInventoryHandler().getInvAvailableSlots(null))
                slotMap.add(new Pair<>(holder, i));
        }

    }

    @Override
    public int getInvSize()
    {
        return slotMap.size();
    }

    @Override
    public boolean isInvEmpty()
    {
        return holderSupplier.get().stream().allMatch((holder) -> holder.getInventoryHandler().isInvEmpty());
    }

    @Override
    public ItemStack getInvStack(int slot)
    {
        Pair<ItemHolder, Integer> pair = slotMap.get(slot);
        return pair.getFirst().getInventoryHandler().getInvStack(pair.getSecond());
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount)
    {
        Pair<ItemHolder, Integer> pair = slotMap.get(slot);
        return pair.getFirst().getInventoryHandler().takeInvStack(pair.getSecond(), amount);
    }

    @Override
    public ItemStack removeInvStack(int slot)
    {
        Pair<ItemHolder, Integer> pair = slotMap.get(slot);
        return pair.getFirst().getInventoryHandler().removeInvStack(pair.getSecond());
    }

    @Override
    public void setInvStack(int slot, ItemStack stack)
    {
        Pair<ItemHolder, Integer> pair = slotMap.get(slot);
        pair.getFirst().getInventoryHandler().setInvStack(pair.getSecond(), stack);
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player)
    {
        return false;
    }

    @Override
    public void clear()
    {

    }

    @Override
    public int[] getInvAvailableSlots(Direction side)
    {
        return IntStream.rangeClosed(1, slotMap.size()).map((i) -> i - 1).toArray();
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir)
    {
        Pair<ItemHolder, Integer> pair = slotMap.get(slot);
        return pair.getFirst().getInventoryHandler().canInsertInvStack(pair.getSecond(), stack, dir);
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir)
    {
        Pair<ItemHolder, Integer> pair = slotMap.get(slot);
        return pair.getFirst().getInventoryHandler().canExtractInvStack(pair.getSecond(), stack, dir);
    }

    @Override
    public void markDirty()
    {
        holderSupplier.get().forEach((holder) -> holder.blockEntity.sync());
    }

}
