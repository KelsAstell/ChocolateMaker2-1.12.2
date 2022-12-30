package wolf.astell.choco.api;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class IsPotion extends ItemFood {

	private final PotionEffect[] effect;
	private final ItemStack returnItem;

	public IsPotion(String name, PotionEffect[] effects, ItemStack returnItem) {

		super(1, 0F, false);

		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setAlwaysEdible();
		this.setMaxStackSize(1);
		this.effect = effects;
		this.returnItem = returnItem;
		this.setCreativeTab(Main.ProjectChocolate);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH,
					SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
			player.getFoodStats().addStats(this, stack);
			this.onFoodEaten(stack, world, player);
			player.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));//In case of NPE

			if (player instanceof EntityPlayerMP)
				CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, stack);
		}

		for (PotionEffect effect : effect) {
			entity.addPotionEffect(new PotionEffect(effect));
		}


		stack.shrink(1);
		return returnItem != null ? returnItem : stack;
	}

	@Override
	public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
		return 32;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format(stack.getUnlocalizedName() +".desc"));
	}
}