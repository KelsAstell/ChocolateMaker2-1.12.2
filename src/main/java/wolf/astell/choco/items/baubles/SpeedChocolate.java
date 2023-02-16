
package wolf.astell.choco.items.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
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
import wolf.astell.choco.init.ModConfig;

import java.util.List;

public class SpeedChocolate extends Item implements IBauble {

	public SpeedChocolate(String name) {
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);

			for(int i = 0; i < baubles.getSlots(); ++i) {
				baubles.getStackInSlot(i);
				if (baubles.getStackInSlot(i).isEmpty() && baubles.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
					baubles.setStackInSlot(i, player.getHeldItem(hand).copy());
					if (!player.capabilities.isCreativeMode) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
					}

					this.onEquipped(player.getHeldItem(hand), player);
					break;
				}
			}
		}

		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	int potionLevel = ModConfig.POTION_CONF.SPEED_LEVEL - 1;

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		PotionEffect effect = player.getActivePotionEffect(MobEffects.SPEED);
		if (player instanceof EntityPlayer && !player.world.isRemote && potionLevel >= 0) {
			if(!player.isSneaking()) {
				player.removePotionEffect(MobEffects.SPEED);
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, Integer.MAX_VALUE, potionLevel, true, false));
			}
			else {
				if(effect != null && effect.getAmplifier() == potionLevel)
					player.removePotionEffect(MobEffects.SPEED);
				}
			}
	}
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		PotionEffect effect = player.getActivePotionEffect(MobEffects.SPEED);
		if(effect != null && effect.getAmplifier() == potionLevel)
			player.removePotionEffect(MobEffects.SPEED);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(I18n.format("item.speed_chocolate.desc.1"));
		if (potionLevel > 4){
			tooltip.add(I18n.format("item.speed_chocolate.desc.0"));
		}
		if (potionLevel < 0){
			tooltip.add(I18n.format("message.choco.effect_off"));
		}
	}

}
