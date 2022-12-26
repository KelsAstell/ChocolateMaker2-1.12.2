
package wolf.astell.choco.itemtool;

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

public class MiningChocolate extends Item implements IBauble {

	public MiningChocolate(String name) {
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
		if (player instanceof EntityPlayer && !player.world.isRemote) {
			if(!player.isSneaking()) {
				player.removePotionEffect(MobEffects.HASTE);
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, Integer.MAX_VALUE, 120, true, false));
			}
			else {
				PotionEffect effect = player.getActivePotionEffect(MobEffects.HASTE);
				if(effect != null && effect.getAmplifier() == 120)
					player.removePotionEffect(MobEffects.HASTE);
				}
			}
	}
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		PotionEffect effect = player.getActivePotionEffect(MobEffects.HASTE);
		if(effect != null && effect.getAmplifier() == 120)
			player.removePotionEffect(MobEffects.HASTE);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

}
