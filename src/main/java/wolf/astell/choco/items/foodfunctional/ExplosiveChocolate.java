
package wolf.astell.choco.items.foodfunctional;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

import javax.annotation.Nonnull;
import java.util.List;

public class ExplosiveChocolate extends Item {

	public ExplosiveChocolate(String name)
	{
		this.setMaxStackSize(64);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 40;
	}

	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.EAT;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		player.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && !entity.getEntityWorld().isRemote) {
			EntityPlayer player = (EntityPlayer) entity;
			stack.shrink(1);
			player.getFoodStats().addStats(-12,0.5F);
			if (player.getFoodStats().getFoodLevel() <= 6){
				player.getEntityWorld().createExplosion(null, player.posX, player.posY + 0.4, player.posZ, 0.1F, false);
				player.setHealth(0);
				return stack;
			}
			player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().grow(16),
					e -> (!e.equals(player) && e.getDistance(player) > 1f)).forEach(e -> explode(e, player));
		}
		return stack;
	}

	private void explode(EntityLivingBase e, EntityPlayer player) {
		if (!(e instanceof EntityPlayer)){
			e.getEntityWorld().createExplosion(player, e.posX, e.posY+1, e.posZ, 2, false);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.explosive_chocolate.desc.0"));
		tooltip.add(I18n.format("item.explosive_chocolate.desc.1"));
	}
}
