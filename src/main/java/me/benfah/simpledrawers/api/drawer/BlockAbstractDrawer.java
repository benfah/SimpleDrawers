package me.benfah.simpledrawers.api.drawer;

import me.benfah.simpledrawers.api.border.Border;
import me.benfah.simpledrawers.api.border.BorderRegistry;
import me.benfah.simpledrawers.api.border.BorderRegistry.BorderProperty;
import me.benfah.simpledrawers.api.container.DrawerContainer;
import me.benfah.simpledrawers.api.drawer.blockentity.BlockEntityAbstractDrawer;
import me.benfah.simpledrawers.item.DrawerInteractable;
import me.benfah.simpledrawers.utils.BlockUtils;
import me.benfah.simpledrawers.utils.ITapeable;
import me.benfah.simpledrawers.utils.model.BorderModelProvider;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class BlockAbstractDrawer extends BlockWithEntity implements InventoryProvider, BorderModelProvider, ITapeable<BlockEntityAbstractDrawer>
{

    protected BlockAbstractDrawer(Settings settings)
    {
        super(settings);
    }

    public static DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static BorderProperty BORDER_TYPE = BorderRegistry.BORDER_TYPE;
    public static EnumProperty<DrawerType> DRAWER_TYPE = DrawerType.DRAWER_TYPE;

    public Identifier borderIdentifier; // TODO unused

    private UUID lastUsedPlayer;
    private long lastUsedTime;

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : (w, p, s, blockEntity) ->
            BlockEntityAbstractDrawer.serverTick(w, p, s, (BlockEntityAbstractDrawer) blockEntity);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation)
    {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
                              BlockHitResult hit)
    {
        if(!world.isClient)
        {
            if(hand.equals(Hand.MAIN_HAND))
            {
            	if(player.getMainHandStack().isEmpty() && !(player.getUuid().equals(lastUsedPlayer) && world.getTime() - lastUsedTime < 10))
            	{
                    BlockEntityAbstractDrawer drawer = (BlockEntityAbstractDrawer) world.getBlockEntity(pos);

                    player.openHandledScreen(new ExtendedScreenHandlerFactory()
                    {
                        @Override
                        public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                            buf.writeBlockPos(pos);
                        }

                        @Override
                        public Text getDisplayName() {
                            return getName();
                        }

                        @Override
                        public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player)
                        {
                            return new DrawerContainer(getContainerType(), syncId, inventory, drawer);
                        }
                    });
            	}
            	else if(state.get(FACING).equals(hit.getSide()))
            	{
	                BlockEntityAbstractDrawer drawer = (BlockEntityAbstractDrawer) world.getBlockEntity(pos);
	                Vec2f interactPos = BlockUtils.getCoordinatesFromHitResult(hit);
	                if(player.getMainHandStack().getItem() instanceof DrawerInteractable)
	                {
	                    ((DrawerInteractable) player.getMainHandStack().getItem()).interact(drawer, player,
	                            drawer.getItemHolderAt(interactPos.x, interactPos.y));
	                    return ActionResult.SUCCESS;
	                }
                        //If this is a valid double right click, store all valid items from the inventory
                    if (player.getUuid().equals(lastUsedPlayer) && world.getTime() - lastUsedTime < 30)
                    {
                        lastUsedPlayer = player.getUuid();
                        lastUsedTime = world.getTime();
                        return drawer.getItemHolderAt(interactPos.x, interactPos.y).offerAll(player.getMainHandStack(), player);
                    }
                        //If this isn't a valid double right click, store only the held stack
                    else
                    {
                        lastUsedPlayer = player.getUuid();
                        lastUsedTime = world.getTime();
                        return drawer.getItemHolderAt(interactPos.x, interactPos.y).offer(player.getMainHandStack());
                    }
            	}
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player)
    {
        if(!world.isClient)
        {
            BlockHitResult result = rayTrace(player);

            if(!result.getSide().equals(state.get(FACING)))
                return;

            Vec2f interactPos = BlockUtils.getCoordinatesFromHitResult(result);
            BlockEntityAbstractDrawer blockEntity = (BlockEntityAbstractDrawer) world.getBlockEntity(pos);

            blockEntity.getItemHolderAt(interactPos.x, interactPos.y).tryInsertIntoInventory(player,
                    !player.isSneaking());
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        BlockEntityAbstractDrawer drawer = (BlockEntityAbstractDrawer) world.getBlockEntity(pos);

        if(!player.isCreative())
        {
            ItemEntity drawerEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                    getStack(this, state.get(BORDER_TYPE)));
            drawerEntity.setToDefaultPickupDelay();
            world.spawnEntity(drawerEntity);
        }

        drawer.getItemHolders().stream().filter((holder) -> !holder.isEmpty()).forEach((holder) ->
        {

            int fullStacksCount = Math.floorDiv(holder.getAmount(), holder.getItemType().getMaxCount());
            int remainderStackSize = holder.getAmount() % holder.getItemType().getMaxCount();

            ItemStack stack = new ItemStack(holder.getItemType(), holder.getItemType().getMaxCount());



            if(holder.getTag() != null)
                stack.setNbt(holder.getTag());

            for(int i = 0; i < fullStacksCount; i++)
            {
                ItemStack localStack = stack.copy();
                ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, localStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }


            if(remainderStackSize > 0)
            {
                stack.setCount(remainderStackSize);
                ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, stack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }
        });

        super.onBreak(world, pos, state, player);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
    {
        if(itemStack != null && !itemStack.equals(ItemStack.EMPTY))
            state = deserializeStack(itemStack, state);

        world.setBlockState(pos, state);

        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos)
    {
        BlockHitResult result = rayTrace(player);

        if(result.getSide().equals(state.get(FACING)) && !(player.getMainHandStack().getItem() instanceof AxeItem))
            return 0;

        return super.calcBlockBreakingDelta(state, player, world, pos);
    }

    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    public BlockState mirror(BlockState state, BlockMirror mirror)
    {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void appendProperties(Builder<Block, BlockState> builder)
    {
        builder.add(FACING).add(BORDER_TYPE).add(DRAWER_TYPE);
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos)
    {
        return ((BlockEntityAbstractDrawer) world.getBlockEntity(pos)).getInventoryHandler();
    }

    @Override
    public Identifier getBorderModel()
    {
        return borderIdentifier;
    }

    private BlockHitResult rayTrace(PlayerEntity player)
    {
        return (BlockHitResult) player.raycast(4.5, 0, false);
    }

    private static BlockState deserializeStack(ItemStack stack, BlockState state)
    {
        DeserializedInfo info = deserializeInfo(stack);

        if(info.getBorder() != null)
            state = state.with(BORDER_TYPE, info.getBorder());
        return state;
    }

    public static DeserializedInfo deserializeInfo(ItemStack stack)
    {
        if(stack.getSubNbt("DrawerInfo") != null)
        {
            NbtCompound data = stack.getSubNbt("DrawerInfo");
            
            
            String border = data.getString("Border");
            if(border != null)
            {
                Border b = BorderRegistry.getBorder(border);
                return new DeserializedInfo(b);
            }
        }
        return new DeserializedInfo(null);
    }

    public abstract ScreenHandlerType<? extends DrawerContainer> getContainerType();

    public static ItemStack getStack(BlockAbstractDrawer drawer, Border border)
    {
        ItemStack result = new ItemStack(drawer.asItem());
        NbtCompound data = new NbtCompound();

        data.putString("Border", BorderRegistry.getName(border));

        result.setSubNbt("DrawerInfo", data);
        return result;
    }

    public static class DeserializedInfo
    {

        private Border border;

        private DeserializedInfo(Border border)
        {
            this.border = border;
        }

        public Border getBorder()
        {
            return border;
        }

    }

}
