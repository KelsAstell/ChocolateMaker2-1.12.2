package wolf.astell.choco.items.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.api.SpecialDays;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

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
		this.setNoRepair();

		ItemList.ITEM_LIST.add(this);
		ItemList.VARIED_ITEM_LIST.add(this);
	}
	public static final String TAG_CHOCO_POWER = "chocolatePower";
	int potionLevel = ModConfig.POTION_CONF.RESISTANCE_LEVEL - 1;
	static int dmg;
	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
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

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void handleDamage(LivingAttackEvent e)
	{
		if(ModConfig.TRINKET_CONF.GODMODE && e.getEntityLiving() instanceof EntityPlayer)
		{
			IBaublesItemHandler h = BaublesApi.getBaublesHandler((EntityPlayer) e.getEntityLiving());
			for(int i : BaubleType.AMULET.getValidSlots())
			{
				ItemStack stack = h.getStackInSlot(i);
				if(!e.isCanceled() && !stack.isEmpty() && stack.getItem() instanceof BaubleChocolate)
				{
					int shield = NBTHelper.getInt(stack, TAG_CHOCO_POWER, 0);
					if (shield > 0){
						if (stack.getItemDamage()==1){
							dmg = shield - (int) Math.min(60, e.getAmount() * 3);
						}else{
							dmg = shield - (int) Math.min(100, e.getAmount() * 5);
						}
						if (dmg >= 0){
							e.setCanceled(true);
							NBTHelper.setInt(stack,TAG_CHOCO_POWER, dmg);
						}else{
							NBTHelper.setInt(stack,TAG_CHOCO_POWER, 0);
						}
					}
				}
			}
		}
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		if (player instanceof EntityPlayer && !player.world.isRemote){
			if (potionLevel >= 0) {
				player.removePotionEffect(MobEffects.RESISTANCE);
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, Integer.MAX_VALUE, potionLevel, true, false));
			}
			NBTHelper.setInt(stack, TAG_CHOCO_POWER, Math.min(NBTHelper.getInt(stack, TAG_CHOCO_POWER, 0) + 1, 10000));
		}
	}

	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (this.isInCreativeTab(tab)) {
			for (int i=0;i<2;i++){
				list.add(new ItemStack(this, 1, i));
			}
		}
	}
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		PotionEffect effect = player.getActivePotionEffect(MobEffects.RESISTANCE);
		if(effect != null && effect.getAmplifier() == potionLevel)
			player.removePotionEffect(MobEffects.RESISTANCE);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.bauble_chocolate.desc.0"));
		if(potionLevel >= 0){
			tooltip.add(I18n.format("item.bauble_chocolate.desc.2"));
		}
		if (stack.getItemDamage() == 1){
			tooltip.add("§bAhoge Installed Successfully. -40% Energy Cost.");
			stack.setTranslatableName("item.ahoge_bauble_chocolate.name");
		}
		if(ModConfig.TRINKET_CONF.GODMODE){
			tooltip.add(I18n.format("item.bauble_chocolate.desc.1"));
			tooltip.add(I18n.format("item.bauble_chocolate.desc.3") + NBTHelper.getInt(stack, TAG_CHOCO_POWER, 0));
		}
		if (!ModConfig.TRINKET_CONF.GODMODE && potionLevel < 0){
			tooltip.add(I18n.format("message.choco.effect_off"));
		}
	}
}