package wolf.astell.choco.items.linkage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

import javax.annotation.Nonnull;

public class TheMace extends Item {

	public TheMace(String name) {
		super();
		this.setUnlocalizedName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);
		this.setMaxStackSize(1);
		this.setRegistryName(name);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	public @Nonnull EnumActionResult onItemUseFirst(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing side,
													float hitX, float hitY, float hitZ, @Nonnull EnumHand hand) {
		Block block = world.getBlockState(pos).getBlock();
		PlayerInteractEvent.RightClickBlock e = new PlayerInteractEvent.RightClickBlock(player, hand, pos, side, new Vec3d(hitX, hitY, hitZ));
		if (MinecraftForge.EVENT_BUS.post(e) || e.getResult() == Event.Result.DENY || e.getUseBlock() == Event.Result.DENY || e.getUseItem() == Event.Result.DENY) {
			return EnumActionResult.PASS;
		}
		if (!player.isSneaking()){
			incrementBlockMetadata(world,pos,block,e);
		}else{
			decrementBlockMetadata(world,pos,block,e);
		}
		player.swingArm(hand);
		return !world.isRemote ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
	}

	public void incrementBlockMetadata(World world, BlockPos pos, Block block, PlayerInteractEvent.RightClickBlock e) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == block) {
			int metadata = state.getBlock().getMetaFromState(state);
			if (metadata < 16) {
				IBlockState newState = block.getStateFromMeta(metadata + 1);
				e.getWorld().playSound(null, pos, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				e.getWorld().playEvent(2001, pos, Block.getStateId(state));
				world.setBlockState(pos, newState);
			}
			else {
				IBlockState newState = block.getStateFromMeta(0);
				world.setBlockState(pos, newState);
			}
		}
	}

	public void decrementBlockMetadata(World world, BlockPos pos, Block block, PlayerInteractEvent.RightClickBlock e) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == block) {
			int metadata = state.getBlock().getMetaFromState(state);
			if (metadata > 0) {
				IBlockState newState = block.getStateFromMeta(metadata - 1);
				e.getWorld().playSound(null, pos, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				e.getWorld().playEvent(2001, pos, Block.getStateId(state));
				world.setBlockState(pos, newState);
			}
		}
	}

}
