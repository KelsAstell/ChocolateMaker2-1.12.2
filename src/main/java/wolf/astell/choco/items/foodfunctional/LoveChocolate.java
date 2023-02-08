
package wolf.astell.choco.items.foodfunctional;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
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

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class LoveChocolate extends Item {

	public LoveChocolate(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}
	public static final String GIFTER = "gifter";
	public static final String NONE = "none";
	public static final String RECEIVER = "receiver";
	public static final String NTR = "ntr";

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 24;
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
		player.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
		if(!entity.world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			String name = player.getName();
			if (Objects.equals(NBTHelper.getString(itemstack, GIFTER, NONE), "none")){
				addGifterName(itemstack, name);
			}
			if (!Objects.equals(NBTHelper.getString(itemstack, GIFTER, NONE), name)){
				if (Objects.equals(NBTHelper.getString(itemstack, RECEIVER, NONE), "none")){
					addReceiverName(itemstack, name);
				}
				if (!Objects.equals(NBTHelper.getString(itemstack, RECEIVER, NONE), name)){
					addGifterName(itemstack, NBTHelper.getString(itemstack, RECEIVER, NONE));
					addReceiverName(itemstack, name);
					NBTHelper.setBoolean(itemstack, NTR, true);
				}
			}
		}
	}
	private static void addGifterName(ItemStack stack, String name) {
		NBTHelper.setString(stack, GIFTER, name);
	}
	private static void addReceiverName(ItemStack stack, String name) {
		NBTHelper.setString(stack, RECEIVER, name);
	}
	@Override
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && !entity.getEntityWorld().isRemote) {
			EntityPlayer player = (EntityPlayer) entity;
			stack.shrink(1);
			if (NBTHelper.getBoolean(stack, NTR, false)){
				player.getEntityWorld().createExplosion(null, player.posX, player.posY + 0.2, player.posZ, 0.5F, false);
				player.setHealth(0);
			}
		}
		return stack;
	}


	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (NBTHelper.getBoolean(stack,NTR,false)){
			tooltip.add(I18n.format("item.love_chocolate.desc.0.ntr"));
		}else{tooltip.add(I18n.format("item.love_chocolate.desc.0"));}
		tooltip.add(NBTHelper.getString(stack, GIFTER, NONE) + " & "  + NBTHelper.getString(stack, RECEIVER, NONE));
	}
}
