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
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import java.util.List;

public class MendingChocolate extends Item implements IBauble {
	public MendingChocolate(String name)
	{
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
		if(ModConfig.TRINKET_CONF.REPAIR_AMOUNT > 0)
		{
			EntityPlayer p = (EntityPlayer)player;
			if(p.experience >= 1 && player.isSneaking() && player.getEntityWorld().getWorldTime() %4 == 0)
			{
				ItemStack mh = player.getHeldItemMainhand();
				if (mh.isItemEnchantable()&&mh.getItemDamage()<mh.getMaxDamage()){
					p.addExperience(-1);
					mh.setItemDamage(mh.getItemDamage() - ModConfig.TRINKET_CONF.REPAIR_AMOUNT);
				}
			}
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.TRINKET;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (ModConfig.TRINKET_CONF.REPAIR_AMOUNT > 0){
			tooltip.add(I18n.format("item.mending_chocolate.desc.0"));
			tooltip.add(I18n.format("item.mending_chocolate.desc.1"));
		}else{
			tooltip.add(I18n.format("message.choco.effect_off"));
		}

	}
}