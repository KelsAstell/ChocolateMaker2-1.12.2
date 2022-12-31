
package wolf.astell.choco.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import java.util.List;

public class MiningChocolate extends Item implements IBauble {

	public MiningChocolate(String name) {
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}
	int potionLevel = ModConfig.POTION_CONF.HASTE_LEVEL - 1;

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		if (player instanceof EntityPlayer && !player.world.isRemote && potionLevel >= 0) {
			if(!player.isSneaking()) {
				player.removePotionEffect(MobEffects.HASTE);
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, Integer.MAX_VALUE, potionLevel, true, false));
			}
			else {
				PotionEffect effect = player.getActivePotionEffect(MobEffects.HASTE);
				if(effect != null && effect.getAmplifier() == potionLevel)
					player.removePotionEffect(MobEffects.HASTE);
				}
			}
	}
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		PotionEffect effect = player.getActivePotionEffect(MobEffects.HASTE);
		if(effect != null && effect.getAmplifier() == potionLevel)
			player.removePotionEffect(MobEffects.HASTE);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.mining_chocolate.desc.0"));
		if (potionLevel >= 0){
			tooltip.add(I18n.format("item.mining_chocolate.desc.1"));
		}else{
			tooltip.add(I18n.format("message.choco.effect_off"));
		}
	}

}
