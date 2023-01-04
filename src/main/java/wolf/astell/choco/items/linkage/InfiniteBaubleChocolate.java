package wolf.astell.choco.items.linkage;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
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

@Mod.EventBusSubscriber
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
				if(!e.isCanceled() && !stack.isEmpty() && stack.getItem() instanceof InfiniteBaubleChocolate)
				{
					Entity attacker = e.getSource().getTrueSource();
					if(attacker instanceof EntityPlayer && ModConfig.AVARITIA_CONF.FONDANT_PERIMETER_MODE){
						EntityPlayer victim = (EntityPlayer) attacker;
						victim.capabilities.disableDamage =false;
						victim.playSound(SoundEvents.ENTITY_CHICKEN_DEATH, 0.9F, victim.world.rand.nextFloat() * 0.1F + 0.9F);
						victim.world.spawnEntity(new EntityLightningBolt(victim.world,victim.posX,victim.posY,victim.posZ,true));
						victim.setHealth(victim.getHealth()-e.getAmount());
						if(e.getAmount() > e.getEntityLiving().getHealth()-1){
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
			if(!((EntityPlayer) player).capabilities.isCreativeMode)
			{
				if(!((EntityPlayer) player).capabilities.allowFlying)
				{
					enableFlyingAbility((EntityPlayer) player);
				}
				float pitch = player.rotationPitch, yaw = player.rotationYaw;
				for (int p = 0; p < 3; p++) {
					float newYaw = yaw + getRand();
					float newPitch = pitch + getRand();
					Vec3d shootPosition = new Vec3d(
							-MathHelper.sin(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F),
							-MathHelper.sin(newPitch * 0.0174F),
							MathHelper.cos(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F));
					player.world.spawnParticle(EnumParticleTypes.TOTEM, player.posX,
							player.posY + player.getEyeHeight() + 0.8f, player.posZ, shootPosition.x, shootPosition.y , shootPosition.z , 1);
				}
				if (player.isSprinting() && !((EntityPlayer) player).isSpectator() && ModConfig.AVARITIA_CONF.FONDANT_SPECTATOR_MODE){
					player.noClip = true;
					((EntityPlayer) player).setGameType(GameType.SPECTATOR);
					((EntityPlayer) player).capabilities.isFlying = true;
					((EntityPlayer) player).sendPlayerAbilities();
				}
				if(!player.isSprinting() && !isStuck(player) && ModConfig.AVARITIA_CONF.FONDANT_SPECTATOR_MODE && player.noClip){
					player.noClip = false;
					((EntityPlayer) player).setGameType(GameType.SURVIVAL);
					((EntityPlayer) player).sendPlayerAbilities();
				}
			}
	}
	}
	private int getRand()
	{
		return new Random().nextInt(30 - -30)+ -30;
	}

	private boolean isStuck(EntityLivingBase player){//try to prevent player from stuck
		BlockPos.PooledMutableBlockPos b = BlockPos.PooledMutableBlockPos.retain();
		for(int i = 0; i < 8; ++i) {
			int j = MathHelper.floor(player.posY + (double)(((float)((i) % 2) - 0.5F) * 0.1F) + (double)player.getEyeHeight());
			int k = MathHelper.floor(player.posX + (double)(((float)((i >> 1) % 2) - 0.5F) * player.width * 0.8F));
			int l = MathHelper.floor(player.posZ + (double)(((float)((i >> 2) % 2) - 0.5F) * player.width * 0.8F));
			if (b.getX() != k || b.getY() != j || b.getZ() != l) {
				b.setPos(k, j, l);
				if (player.world.getBlockState(b).causesSuffocation()) {
					b.release();
					return true;
				}
			}
		}
		return false;
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

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.infinite_bauble_chocolate.desc.0"));
		tooltip.add(I18n.format("item.infinite_bauble_chocolate.desc.1"));
		if (ModConfig.AVARITIA_CONF.FONDANT_PERIMETER_MODE){
			tooltip.add(I18n.format("item.infinite_bauble_chocolate.desc.2"));
		}
		if (ModConfig.AVARITIA_CONF.FONDANT_SPECTATOR_MODE){
			tooltip.add(I18n.format("item.infinite_bauble_chocolate.desc.3"));
		}
	}
}