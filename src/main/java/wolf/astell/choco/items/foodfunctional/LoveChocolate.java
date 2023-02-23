
package wolf.astell.choco.items.foodfunctional;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.register.AdvancementRegister;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
			if (NBTHelper.getBoolean(stack, NTR, false)){
				AdvancementRegister.NTR.trigger((EntityPlayerMP) player);
				player.getEntityWorld().createExplosion(null, player.posX, player.posY + 0.2, player.posZ, 0.5F, false);
				player.setHealth(0);
			}else{
				float pitch = player.rotationPitch, yaw = player.rotationYaw;
				for (int p = 0; p < 3; p++) {
					float newYaw = yaw + getRand();
					float newPitch = pitch + getRand();
					Vec3d shootPosition = new Vec3d(
							-MathHelper.sin(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F),
							-MathHelper.sin(newPitch * 0.0174F),
							MathHelper.cos(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F));
					player.world.spawnParticle(EnumParticleTypes.HEART, player.posX,
							player.posY + player.getEyeHeight() - 0.8f, player.posZ, shootPosition.x, shootPosition.y , shootPosition.z , 1);
				}
				player.setAbsorptionAmount(2);
			}
		}
		stack.shrink(1);
		return stack;
	}
	private int getRand()
	{
		return new Random().nextInt(20 - -20)+ -20;
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
