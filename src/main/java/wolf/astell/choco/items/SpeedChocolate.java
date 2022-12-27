
package wolf.astell.choco.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

public class SpeedChocolate extends Item implements IBauble {

	public SpeedChocolate(String name) {
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		PotionEffect effect = player.getActivePotionEffect(MobEffects.SPEED);
		if (player instanceof EntityPlayer && !player.world.isRemote) {
			if(!player.isSneaking()) {
				player.removePotionEffect(MobEffects.SPEED);
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, Integer.MAX_VALUE, ModConfig.POTION_CONF.SPEED_LEVEL - 1, true, false));
			}
			else {
				if(effect != null && effect.getAmplifier() == ModConfig.POTION_CONF.SPEED_LEVEL - 1)
					player.removePotionEffect(MobEffects.SPEED);
				}
			}
	}
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		PotionEffect effect = player.getActivePotionEffect(MobEffects.SPEED);
		if(effect != null && effect.getAmplifier() == ModConfig.POTION_CONF.SPEED_LEVEL - 1)
			player.removePotionEffect(MobEffects.SPEED);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

}
