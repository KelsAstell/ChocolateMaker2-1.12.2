
package wolf.astell.choco.items.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import java.text.DecimalFormat;
import java.util.List;

public class ReachChocolate extends Item implements IBauble {

	public ReachChocolate(String name) {
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
	public static final String BONUS = "bonus";
	public static final String BASE = "base";
	DecimalFormat df = new DecimalFormat("#0.00");
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		if (player instanceof EntityPlayer && !player.world.isRemote) {
			if (NBTHelper.getDouble(stack, BASE, 0) == 0){
				NBTHelper.setDouble(stack, BASE, player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getBaseValue());
			}
			player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(NBTHelper.getDouble(stack, BASE, 0) + NBTHelper.getDouble(stack,BONUS,0));
		}
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
		if(!entity.world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			int highest = -1;
			int[] counts = new int[player.inventory.getSizeInventory() - player.inventory.armorInventory.size()];

			for(int i = 0; i < counts.length; i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if(stack.isEmpty()) {
					continue;
				}
				if(ItemList.goldPawChocolate == stack.getItem()) {
					counts[i] = stack.getCount();
					if(highest == -1)
						highest = i;
					else highest = counts[i] > counts[highest] && highest > 8 ? i : highest;
				}
			}
			if(highest == -1) {
			} else {
				for(int i = 0; i < counts.length; i++) {
					int count = counts[i];
					if(count == 0)
						continue;
					if (NBTHelper.getDouble(itemstack,BONUS,0) < ModConfig.TRINKET_CONF.REACH_AMOUNT){
						add(itemstack, count);
						player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
					}
				}
			}
		}
	}
	private static void add(ItemStack stack, int count) {
		NBTHelper.setDouble(stack, BONUS, count * 0.01 + NBTHelper.getDouble(stack,BONUS,0));
	}
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).setBaseValue(NBTHelper.getDouble(stack, BASE, 0));
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (ModConfig.TRINKET_CONF.REACH_AMOUNT == 0){
			tooltip.add(I18n.format("message.choco.effect_off"));
		}else{
			tooltip.add(I18n.format("item.reach_chocolate.desc.0"));
			tooltip.add(I18n.format("item.reach_chocolate.desc.1") + " " +df.format(NBTHelper.getDouble(stack,BONUS,0)));
		}
	}

}
