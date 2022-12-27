package wolf.astell.choco.items;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

@EventBusSubscriber
public class BaubleChocolate extends Item implements IBauble
{
	public BaubleChocolate(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void handleDamage(LivingAttackEvent e)
	{
		if(e.getEntityLiving() instanceof EntityPlayer)
		{
			IBaublesItemHandler h = BaublesApi.getBaublesHandler((EntityPlayer) e.getEntityLiving());
			for(int i : BaubleType.AMULET.getValidSlots())
			{
				ItemStack stack = h.getStackInSlot(i);
				if(!e.isCanceled() && !stack.isEmpty() && stack.getItem() instanceof BaubleChocolate)
				{
						e.setCanceled(true);
				}
			}
		}
	}
}