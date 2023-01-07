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
		this.setUnlocalizedName("name");
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer && ModConfig.TRINKET_CONF.REPAIR_AMOUNT > 0)
		{
			EntityPlayer player = (EntityPlayer)entity;
			if(player.experience >= 1 && player.isSneaking() && player.getEntityWorld().getWorldTime() %4 == 0)
			{
				ItemStack stack = player.getHeldItemMainhand();
				if (player.getHeldItemMainhand().isItemDamaged() && stack.isItemEnchantable()){
					player.addExperience(-1);
					stack.setItemDamage(stack.getItemDamage() - ModConfig.TRINKET_CONF.REPAIR_AMOUNT);
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