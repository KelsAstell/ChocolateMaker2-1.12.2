package wolf.astell.choco.items.linkage;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import morph.avaritia.init.ModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.InjectDamageSource;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import java.util.List;
import java.util.Random;

@EventBusSubscriber
public class InfiniteBaubleChocolate extends Item implements IBauble
{
	public InfiniteBaubleChocolate(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}
	int potionLevel = ModConfig.POTION_CONF.RESISTANCE_LEVEL - 1;

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
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
				if(!e.isCanceled() && !stack.isEmpty() && stack.getItem() instanceof InfiniteBaubleChocolate)
				{
					Entity attacker = e.getSource().getTrueSource();
					if(attacker instanceof EntityPlayer){
						EntityPlayer victim = (EntityPlayer) attacker;
						if(e.getAmount() > e.getEntityLiving().getHealth()-1 || victim.getHeldItemMainhand().equals(new ItemStack(ModItems.infinity_sword))){
							victim.playSound(SoundEvents.ENTITY_CHICKEN_DEATH, 0.9F, victim.world.rand.nextFloat() * 0.1F + 0.9F);
							victim.world.spawnEntity(new EntityLightningBolt(victim.world,victim.posX,victim.posY,victim.posZ,true));
							victim.getCombatTracker().trackDamage(new InjectDamageSource(e.getEntityLiving()), victim.getHealth(), victim.getHealth());
							victim.setHealth(0);
							victim.onDeath(new EntityDamageSource("chocolate", e.getEntityLiving()));
						}
					}
					e.setCanceled(true);
				}
			}
		}
	}


	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);
		if (player instanceof EntityPlayer && !player.world.isRemote) {
			if(!((EntityPlayer) player).isSpectator() && !((EntityPlayer) player).capabilities.isCreativeMode)
			{
				if(!((EntityPlayer) player).capabilities.allowFlying)
				{
					enableFlyingAbility((EntityPlayer) player);
				}
			float pitch = player.rotationPitch, yaw = player.rotationYaw;
			float newYaw = yaw + getRand();
			float newPitch = pitch + getRand();
			Vec3d shootPosition = new Vec3d(
					-MathHelper.sin(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F),
					-MathHelper.sin(newPitch * 0.0174F),
					MathHelper.cos(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F));
			player.world.spawnParticle(EnumParticleTypes.TOTEM, player.posX,
					player.posY + player.getEyeHeight() + 1f, player.posZ, shootPosition.x, shootPosition.y, shootPosition.z, 1);

		}
	}
	}
	private int getRand()
	{
		return new Random().nextInt(50 - -50)+ -50;
	}
	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		disableFlyingAbility((EntityPlayer) player);
		((EntityPlayer) player).setGameType(GameType.SURVIVAL);
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

//	@SideOnly(Side.CLIENT)
//	@Override
//	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//		super.addInformation(stack, worldIn, tooltip, flagIn);
//		tooltip.add(I18n.format("item.bauble_chocolate.desc.0"));
//		if(potionLevel >= 0){
//			tooltip.add(I18n.format("item.bauble_chocolate.desc.2"));
//		}
//		if(ModConfig.TRINKET_CONF.GODMODE){
//			tooltip.add(I18n.format("item.bauble_chocolate.desc.1"));
//		}
//		if (!ModConfig.TRINKET_CONF.GODMODE && potionLevel < 0){
//			tooltip.add(I18n.format("message.choco.effect_off"));
//		}
//	}
}