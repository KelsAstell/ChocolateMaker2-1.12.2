
package wolf.astell.choco.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import java.util.List;

public class TimeChocolate extends Item implements IBauble {

	public TimeChocolate(String name) {
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}
	public static final String FROM_YEAR = "ticks";
	public static final String SINCE_YEAR = "since";
	public static final String STATS = "stats";
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		if (player instanceof EntityPlayer && !player.world.isRemote) {
			if(NBTHelper.getLong(stack, FROM_YEAR, 0) == 0){
				NBTHelper.setLong(stack, SINCE_YEAR, player.getEntityWorld().getTotalWorldTime());
				NBTHelper.setLong(stack, FROM_YEAR, player.getEntityWorld().getTotalWorldTime());
			}else{
				long eclipsed = player.getEntityWorld().getTotalWorldTime() - NBTHelper.getLong(stack, FROM_YEAR, 0);
				if (eclipsed % 36000 == 0){
					NBTHelper.setLong(stack, FROM_YEAR, player.getEntityWorld().getTotalWorldTime());
					NBTHelper.setInt(stack, STATS, Math.min(NBTHelper.getInt(stack, STATS, 0) + 1, ModConfig.TRINKET_CONF.ABSORB_AMOUNT));
				}
				if (eclipsed % 1200 == 0){
					player.setAbsorptionAmount(NBTHelper.getInt(stack, STATS, 0));
				}
			}
		}
	}
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (ModConfig.TRINKET_CONF.ABSORB_AMOUNT ==0){
			tooltip.add(I18n.format("message.choco.effect_off"));
		}else{
			tooltip.add(I18n.format("item.time_chocolate.desc.0"));
			tooltip.add(I18n.format("item.time_chocolate.desc.1"));
			tooltip.add(I18n.format("item.time_chocolate.desc.2") + " " + NBTHelper.getLong(stack, SINCE_YEAR, 0)/ 24000 );
		}
	}

}
