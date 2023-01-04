
package wolf.astell.choco.items.foodfunctional;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Mod.EventBusSubscriber
public class WorldChocolate extends Item {
	public WorldChocolate(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 16;
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
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && !entity.getEntityWorld().isRemote) {
			EntityPlayer player = (EntityPlayer) entity;
			if (ModConfig.SPECIAL_CONF.WORLD_TRAVELLER) {
				if(player.dimension == 0){teleport((EntityPlayerMP) player, -1, ModConfig.SPECIAL_CONF.CUSTOM_COORDS[0], ModConfig.SPECIAL_CONF.CUSTOM_COORDS[1], ModConfig.SPECIAL_CONF.CUSTOM_COORDS[2], Objects.requireNonNull(player.getServer()).getPlayerList());}
				else if(player.dimension == 1){teleport((EntityPlayerMP) player, 0, ModConfig.SPECIAL_CONF.CUSTOM_COORDS[0], ModConfig.SPECIAL_CONF.CUSTOM_COORDS[1], ModConfig.SPECIAL_CONF.CUSTOM_COORDS[2], Objects.requireNonNull(player.getServer()).getPlayerList());}
				else if(player.dimension == -1){teleport((EntityPlayerMP) player, 1, ModConfig.SPECIAL_CONF.CUSTOM_COORDS[0], ModConfig.SPECIAL_CONF.CUSTOM_COORDS[1], ModConfig.SPECIAL_CONF.CUSTOM_COORDS[2], Objects.requireNonNull(player.getServer()).getPlayerList());}

			}
		}
		return stack;
	}

	public static void teleport(EntityPlayerMP player, int dim, double x, double y, double z, PlayerList playerList) {
		if (!ForgeHooks.onTravelToDimension(player, dim))
			return;
		int oldDim = player.dimension;
		WorldServer worldServer = playerList.getServerInstance().getWorld(player.dimension);
		player.dimension = dim;
		WorldServer worldServer1 = playerList.getServerInstance().getWorld(player.dimension);
		player.connection.sendPacket(new SPacketRespawn(player.dimension, player.world.getDifficulty(), player.world.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
		worldServer.removeEntityDangerously(player);
		if (player.isBeingRidden()) {
			player.removePassengers();
		}
		if (player.isRiding()) {
			player.dismountRidingEntity();
		}
		player.isDead = false;
		teleportEntity(player, worldServer, worldServer1);
		playerList.preparePlayer(player, worldServer);
		player.connection.setPlayerLocation(x + 0.5D, y, z + 0.5D, player.rotationYaw, player.rotationPitch);
		player.interactionManager.setWorld(worldServer1);
		playerList.updateTimeAndWeatherForPlayer(player, worldServer1);
		playerList.syncPlayerInventory(player);
		for (PotionEffect potioneffect : player.getActivePotionEffects()) {
			player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potioneffect));
		}
		FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldDim, dim);
		worldServer1.playSound(null, x, y + 0.5D, z, SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.MASTER, 0.25F, new Random().nextFloat() * 0.4F + 0.8F);
		if (!player.capabilities.isCreativeMode) {
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 120, 9, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 120, 0, false, false));
			player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 20, 0, false, false));}
	}

	public static void teleportEntity(Entity entity, WorldServer oldWorld, WorldServer newWorld) {
		WorldProvider oldProvider = oldWorld.provider;
		WorldProvider newProvider = newWorld.provider;
		double moveFactor = oldProvider.getMovementFactor() / newProvider.getMovementFactor();
		double x = entity.posX * moveFactor;
		double z = entity.posZ * moveFactor;
		oldWorld.profiler.startSection("teleporting_player");
		x = MathHelper.clamp(x, -29999872, 29999872);
		z = MathHelper.clamp(z, -29999872, 29999872);
		if (entity.isEntityAlive()) {
			entity.setLocationAndAngles(x, entity.posY, z, entity.rotationYaw, entity.rotationPitch);
			newWorld.spawnEntity(entity);
			newWorld.updateEntityWithOptionalForce(entity, false);
		}
		oldWorld.profiler.endSection();
		entity.setWorld(newWorld);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.world_chocolate.desc.0"));
		tooltip.add(I18n.format("item.world_chocolate.desc.1"));
	}
}
