package wolf.astell.choco.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import java.util.List;
import java.util.Random;

public class FlightChocolate extends Item implements IBauble {
	public FlightChocolate(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName("flight_chocolate");
		if (rollChance()){
			this.setUnlocalizedName("flight_chocolate_egg");
		}
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	private boolean rollChance() {
		double ran = Math.random();
		double chance = 0.01;
		return ran < chance;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer && ModConfig.TRINKET_CONF.ENABLE_FLIGHT)
		{
			EntityPlayer player = (EntityPlayer)entity;
			if(!player.isSpectator() && !player.capabilities.isCreativeMode)
			{
				if(!player.capabilities.allowFlying)
				{
					enableFlyingAbility(player);
				}
				else
				{
					if(player.capabilities.isFlying)
					{
						if(player.getFoodStats().getFoodLevel() <= 2)
						{
							disableFlyingAbility(player);
						}else
						{
							if(player.isSprinting())
							{
								Vec3d vec3d = player.getLookVec();
								player.motionX += vec3d.x * 0.2D + (vec3d.x * 1.5D - player.motionX) * 0.5D;
								player.motionY += vec3d.y * 0.2D + (vec3d.y * 1.5D - player.motionY) * 0.5D;
								player.motionZ += vec3d.z * 0.2D + (vec3d.z * 1.5D - player.motionZ) * 0.5D;
								if (ModConfig.TRINKET_CONF.DO_FLIGHT_EXHAUST){
									player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel()-1);
								}
								float pitch = player.rotationPitch, yaw = player.rotationYaw;
								for (int p = 0; p < 3; p++) {
									float newYaw = yaw + getRand();
									float newPitch = pitch + getRand();
									Vec3d shootPosition = new Vec3d(
											-MathHelper.sin(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F),
											-MathHelper.sin(newPitch * 0.0174F),
											MathHelper.cos(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F));
									player.world.spawnParticle(EnumParticleTypes.END_ROD, player.posX,
											player.posY + player.getEyeHeight() - 0.8f, player.posZ, shootPosition.x, shootPosition.y , shootPosition.z , 1);
								}
							}
						}
					}
				}
			}
		}
	}

	private int getRand()
	{
		return new Random().nextInt(30 - -30)+ -30;
	}


	private void enableFlyingAbility(EntityPlayer player)
	{
		player.capabilities.allowFlying = true;
		player.capabilities.isFlying = false;
		player.sendPlayerAbilities();
	}

	private void disableFlyingAbility(EntityPlayer player)
	{
		player.capabilities.allowFlying = false;
		player.capabilities.isFlying = false;
		player.sendPlayerAbilities();
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		IBauble.super.onUnequipped(itemstack, player);
		if(player instanceof EntityPlayer)
		{
			disableFlyingAbility((EntityPlayer)player);
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
		if (ModConfig.TRINKET_CONF.ENABLE_FLIGHT){
			tooltip.add(I18n.format("item.flight_chocolate.desc.0"));
			tooltip.add(I18n.format("item.flight_chocolate.desc.1"));}else{
			tooltip.add(I18n.format("message.choco.effect_off"));
		}

	}
}