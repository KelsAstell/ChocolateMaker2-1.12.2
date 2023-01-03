package wolf.astell.choco.items.linkage;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import com.mojang.authlib.GameProfile;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

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
						if(e.getAmount() > e.getEntityLiving().getHealth()-1){
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
		double speed = abs(player.motionX) + abs(player.motionY) + abs(player.motionZ);
		if (player instanceof EntityPlayer && !player.world.isRemote) {
			if(!((EntityPlayer) player).capabilities.isCreativeMode)
			{
				if(!((EntityPlayer) player).capabilities.allowFlying)
				{
					enableFlyingAbility((EntityPlayer) player);
				}
				if (player.isSprinting() && !((EntityPlayer) player).isSpectator() && ModConfig.AVARITIA_CONF.FONDANT_SPECTATOR_MODE){
					player.noClip = true;
					((EntityPlayer) player).setGameType(GameType.SPECTATOR);
					((EntityPlayer) player).capabilities.isFlying = true;
					((EntityPlayer) player).sendPlayerAbilities();
				}
				if(speed==0 && !player.isSprinting() && abs(player.motionY)==0 && ModConfig.AVARITIA_CONF.FONDANT_SPECTATOR_MODE && player.noClip){
					player.noClip = false;
					((EntityPlayer) player).setGameType(GameType.SURVIVAL);
					((EntityPlayer) player).sendPlayerAbilities();
				}
			}
	}
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