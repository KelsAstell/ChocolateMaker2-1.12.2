
package wolf.astell.choco.items.linkage;

import cofh.core.item.ItemMultiRF;
import cofh.core.util.core.IInitializer;
import cofh.core.util.helpers.StringHelper;
import cofh.thermalfoundation.ThermalFoundation;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class PoweredChocolate extends ItemMultiRF implements IInitializer {

	public PoweredChocolate(String name)
	{
		super(Main.MODID);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(ThermalFoundation.tabUtils);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	protected int getCapacity(ItemStack itemStack) {
		return 300000;
	}

	@Override
	protected int getReceive(ItemStack itemStack) {
		return 1000;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 20;
	}

	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.EAT;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(player.canEat(false)) {
			player.setActiveHand(hand);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		}
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
		super.onUsingTick(stack, living, count);
		if(!(living instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) living;
		if (count % 5 == 0){
			if (player.isSneaking()){
				if(useSuccess(stack,50000)) {
					useEnergy(stack, 50000);
					float health = player.getMaxHealth();
					player.setHealth(health);
					player.setAbsorptionAmount(health/2);
					player.getFoodStats().addStats(20,1F);
					player.sendMessage(new TextComponentTranslation("message.choco.powered_chocolate.success").setStyle(new Style().setColor(TextFormatting.YELLOW)));
				}else if(!useSuccess(stack,50000)){
					player.sendMessage(new TextComponentTranslation("message.choco.power_not_sufficient").setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}else{
				if(useSuccess(stack,1000)) {
					useEnergy(stack, 1000);
					player.getFoodStats().addStats(1,1F);
				}else{
					player.sendMessage(new TextComponentTranslation("message.choco.power_not_sufficient").setStyle(new Style().setColor(TextFormatting.RED)));
				}
			}
		}
	}

	protected void useEnergy(ItemStack stack, int energy) {

		if (isCreative(stack)) {
			return;
		}
		extractEnergy(stack, energy, true);
	}
	protected boolean useSuccess(ItemStack stack, int requirement) {

		if (isCreative(stack)) {
			return true;
		}
		return getEnergyStored(stack) >= requirement;
	}
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			tooltip.add(StringHelper.shiftForDetails());
		}
		if (!StringHelper.isShiftKeyDown()) {
			return;
		}
		tooltip.add(StringHelper.getInfoText("item.powered_chocolate.desc.0"));
		tooltip.add(StringHelper.getInfoText("item.powered_chocolate.desc.1"));
		if (isCreative(stack)) {
			tooltip.add(StringHelper.localize("info.cofh.charge") + ": 1.21G RF");
		} else {
			tooltip.add(StringHelper.localize("info.cofh.charge") + ": " + StringHelper.getScaledNumber(getEnergyStored(stack)) + " / " + StringHelper.getScaledNumber(getMaxEnergyStored(stack)) + " RF");
		}
	}

	@Override
	public boolean preInit() {
//		ForgeRegistries.ITEMS.register(setRegistryName("powered_chocolate"));
//		ForgeRegistries.ITEMS.register(setCreativeTab(ThermalFoundation.tabUtils));
//		ForgeRegistries.ITEMS.register(setUnlocalizedName(name));
		poweredChocolate = addEntryItem();
		return true;
	}
	private void addEntry() {
		typeMap.put(0, new TypeEntry("powered_chocolate", 300000, 500));
	}
	private ItemStack addEntryItem() {
		addEntry();
		return addItem(0, "powered_chocolate", EnumRarity.EPIC);
	}

	@Override
	public boolean initialize() {
		return true;
	}
	public static ItemStack poweredChocolate;
	private static final Int2ObjectOpenHashMap<TypeEntry> typeMap = new Int2ObjectOpenHashMap<>();
	public static class TypeEntry {
		public final String name;
		public final int capacity;
		public final int recv;
		TypeEntry(String name, int capacity, int recv) {
			this.name = name;
			this.capacity = capacity;
			this.recv = recv;
		}
	}
}
