
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;

import java.util.List;

public class UndyingChocolate extends Item implements IBauble {

	public UndyingChocolate(String name) {
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
		ItemList.VARIED_ITEM_LIST.add(this);
	}
	public static final String TIMES = "times";
	public static final String COOLDOWN = "coolDown";

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
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		if (player instanceof EntityPlayer && !player.world.isRemote) {
			if (NBTHelper.getInt(stack,TIMES,0) < stack.getItemDamage()){
				if (NBTHelper.getInt(stack,COOLDOWN,0) == 0){
					NBTHelper.setInt(stack,TIMES, 1 + NBTHelper.getInt(stack,TIMES,0));
					NBTHelper.setInt(stack,COOLDOWN,100);
				}else{
					NBTHelper.setInt(stack,COOLDOWN,NBTHelper.getInt(stack,COOLDOWN,0) - 1);
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void EscapeDeath(LivingDeathEvent e)
	{
		if(e.getEntityLiving() instanceof EntityPlayer)
		{
			IBaublesItemHandler h = BaublesApi.getBaublesHandler((EntityPlayer) e.getEntityLiving());
			for(int i : BaubleType.CHARM.getValidSlots())
			{
				ItemStack stack = h.getStackInSlot(i);
				if(!e.isCanceled() && !stack.isEmpty() && stack.getItem() instanceof UndyingChocolate)
				{
					int times = NBTHelper.getInt(stack, TIMES, 0);
					if (times > 0){
						NBTHelper.setInt(stack,TIMES, -1 + NBTHelper.getInt(stack,TIMES,0));
						e.setCanceled(true);
						EntityLivingBase player = e.getEntityLiving();
						player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 200, 3, true, false));
						player.setAbsorptionAmount(6);
						player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 600, 0, true, true));
						player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 1, true, false));
					}
				}
			}
		}
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.CHARM;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.undying_chocolate.desc.0"));
		tooltip.add(I18n.format("item.undying_chocolate.desc.1"));
		tooltip.add(I18n.format("item.undying_chocolate.desc.2")
				+ " " + NBTHelper.getInt(stack, TIMES, 0) +
				" / " + (stack.getItemDamage()));
	}

}
