
package wolf.astell.choco.items.linkage;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cofh.core.util.helpers.BaublesHelper;
import cofh.core.util.helpers.EnergyHelper;
import cofh.redstoneflux.api.IEnergyContainerItem;
import com.google.common.collect.Iterables;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;
import wolf.astell.choco.init.register.compact.CoFHRegister;

import java.util.Arrays;
import java.util.List;

public class BatteryCase extends Item implements IBauble {

	public BatteryCase(String name) {
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);
		this.setNoRepair();

		ItemList.ITEM_LIST.add(this);
		ItemList.VARIED_ITEM_LIST_3.add(this);
	}
	public static final String ENERGY = "energy";
	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		if (player instanceof EntityPlayer && !player.world.isRemote) {
			Iterable<ItemStack> stackIterable;
			stackIterable = Iterables.concat(Arrays.asList(((EntityPlayer) player).inventory.mainInventory, ((EntityPlayer) player).inventory.armorInventory, ((EntityPlayer) player).inventory.offHandInventory, BaublesHelper.getBaubles(player)));
			for (ItemStack current : stackIterable) {
				if (current.equals(stack)) {
					continue;
				}
				if (EnergyHelper.isEnergyContainerItem(current)) {
					((IEnergyContainerItem) current.getItem()).receiveEnergy(current, Math.min(NBTHelper.getInt(stack,ENERGY,0),ModConfig.TRINKET_CONF.MAX_OUTPUT), false);
				} else if (EnergyHelper.isEnergyHandler(current)) {
					IEnergyStorage handler = EnergyHelper.getEnergyHandler(current);
					if (handler != null) {
						handler.receiveEnergy(Math.min(NBTHelper.getInt(stack,ENERGY,0),ModConfig.TRINKET_CONF.MAX_OUTPUT),false);
					}
				}
			}
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
				if(CoFHRegister.battery == stack.getItem()) {
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
					if (NBTHelper.getInt(itemstack,ENERGY,0) < ModConfig.TRINKET_CONF.MAX_OUTPUT){
						add(itemstack, count);
						player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
					}
				}
			}
		}
	}

	private static void add(ItemStack stack, int count) {
		NBTHelper.setInt(stack, ENERGY, count + NBTHelper.getInt(stack,ENERGY,0));
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (ModConfig.TRINKET_CONF.MAX_OUTPUT == 0){
			tooltip.add(I18n.format("message.choco.effect_off"));
		}else{
			tooltip.add(I18n.format("item.battery_case.desc.0"));
			tooltip.add(I18n.format("item.battery_case.desc.1") + " " + NBTHelper.getInt(stack,ENERGY,0)+ " RF/t");
			if(NBTHelper.getInt(stack,ENERGY,0) >= ModConfig.TRINKET_CONF.MAX_OUTPUT){
				tooltip.add(I18n.format("item.battery_case.desc.maxed"));
			}
		}
		if (stack.getItemDamage() != 0){
			stack.setTranslatableName("item.battery_case.name." + stack.getItemDamage());
		}
	}

}
