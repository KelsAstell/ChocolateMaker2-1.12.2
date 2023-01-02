
package wolf.astell.choco.items.linkage;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
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

public class InfiniteChocolate extends Item {

	public InfiniteChocolate(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 50;
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
		if(player.canEat(true)) {
			player.setActiveHand(hand);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
		super.onUsingTick(stack, living, count);
		if(!(living instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) living;
		if (count % 8 == 0){
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE,72000,0,false,false));
			player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING,72000,0,false,false));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,72000,2,false,false));
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,72000,2,false,false));
			player.addPotionEffect(new PotionEffect(MobEffects.SATURATION,72000,2,false,false));
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH,72000,2,false,false));
			player.addPotionEffect(new PotionEffect(MobEffects.LUCK,72000,2,false,false));
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED,72000,2,false,false));
			player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST,72000,2,false,false));
			player.setAbsorptionAmount(player.getMaxHealth());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.infinite_chocolate.desc.0"));
		tooltip.add(I18n.format("item.infinite_chocolate.desc.1"));
		tooltip.add(I18n.format("item.infinite_chocolate.desc.2"));
	}
}
