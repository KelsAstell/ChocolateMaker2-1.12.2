package wolf.astell.choco.items;

import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.Main;

import net.minecraft.advancements.CriteriaTriggers;
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

import javax.annotation.Nonnull;
import java.util.Objects;

public class IsFood extends ItemFood {

    private final PotionEffect[] effect;
    private final ItemStack returnItem;
    private final int maxUseDuration;

    public IsFood(String name, int food, float saturation, boolean wolfFood, boolean setAlwaysEdible, PotionEffect[] effects, ItemStack returnItem, int useDuration, int setMaxStackSize) {

        super(food, saturation, wolfFood);

        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        if (setAlwaysEdible)
            this.setAlwaysEdible();
        this.setMaxStackSize(setMaxStackSize);
        this.effect = effects;
        this.returnItem = returnItem;
        this.maxUseDuration = useDuration;
        this.setCreativeTab(Main.ProjectChocolate);

        ItemList.ITEM_LIST.add(this);
    }
    public IsFood(String name, int food, float saturation, boolean wolfFood, boolean setAlwaysEdible, ItemStack returnItem, int useDuration, int setMaxStackSize) {
        this(name, food, saturation, wolfFood, setAlwaysEdible, new PotionEffect[] {}, returnItem, useDuration,
                setMaxStackSize);
    }

    public IsFood(String name, int food, float saturation, boolean wolfFood, boolean setAlwaysEdible, ItemStack returnItem, int useDuration) {
        this(name, food, saturation, wolfFood, setAlwaysEdible, returnItem, useDuration, 64);
    }

    public IsFood(String name, int food, float saturation, boolean wolfFood, boolean setAlwaysEdible, int useDuration) {
        this(name, food, saturation, wolfFood, setAlwaysEdible, new PotionEffect[] {new PotionEffect(MobEffects.ABSORPTION, 200, 3, true, false),new PotionEffect(MobEffects.REGENERATION, 200, 2, true, false),new PotionEffect(MobEffects.RESISTANCE, 200, 1, true, false)}, new ItemStack(Item.getItemById(281)), useDuration,
                64);
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP,
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

        return maxUseDuration;
    }
}