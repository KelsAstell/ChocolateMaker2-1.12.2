package wolf.astell.choco.init;

import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import wolf.astell.choco.api.IsFood;
import wolf.astell.choco.api.IsPotion;
import wolf.astell.choco.api.SpecialDays;
import wolf.astell.choco.init.register.EffectRegister;
import wolf.astell.choco.items.IngotChocolate;
import wolf.astell.choco.items.baubles.*;
import wolf.astell.choco.items.foodfunctional.ExpChocolate;
import wolf.astell.choco.items.foodfunctional.ExplosiveChocolate;
import wolf.astell.choco.items.foodfunctional.WorldChocolate;
import wolf.astell.choco.items.tools.PickaxeChocolate;

import java.util.ArrayList;
import java.util.List;

public class ItemList{
    public static final List<Item> ITEM_LIST = new ArrayList<>();
    public static Item foodChocolate;
    public static Item foodGoldenChocolate;
    public static Item foodEnchantedChocolate;
    public static Item foodHotChocolate;
    public static Item foodChocolateIcecream;
    public static Item baubleChocolate;
    public static Item airChocolate;
    public static Item miningChocolate;
    public static Item carrotChocolate;
    public static Item speedChocolate;
    public static Item flightChocolate;
    public static Item ingotChocolate;
    public static Item pickaxeChocolate;
    public static Item hazardPotion;
    public static Item animalPotion;
    public static Item travellerPotion;
    public static Item expChocolate;
    public static Item worldChocolate;
    public static Item explosiveChocolate;
    public static Item mendingChocolate;


    public static void init() {
        foodChocolate = new IsFood("chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER, 0.5F, false, false, null,ModConfig.SPECIAL_CONF.CONSUME_SPEED);
        foodGoldenChocolate = new IsFood("golden_chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER * 2, 0.5F, false, true,new PotionEffect[] {new PotionEffect(MobEffects.REGENERATION, 600, 0),new PotionEffect(MobEffects.ABSORPTION, 600, 1)},ModConfig.SPECIAL_CONF.CONSUME_SPEED,64);
        foodEnchantedChocolate = new IsFood("enchanted_chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER * 5, 0.5F, false, true,new PotionEffect[] {new PotionEffect(MobEffects.STRENGTH, 1800, 1),new PotionEffect(MobEffects.REGENERATION, 1800, 2),new PotionEffect(MobEffects.ABSORPTION, 1800, 3),new PotionEffect(MobEffects.RESISTANCE, 1800, 2),new PotionEffect(MobEffects.FIRE_RESISTANCE, 1800, 0)},ModConfig.SPECIAL_CONF.CONSUME_SPEED,64);
        foodHotChocolate = new IsFood("hot_chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER, 0.5F, false, true,new ItemStack(Items.GLASS_BOTTLE),ModConfig.SPECIAL_CONF.CONSUME_SPEED);
        foodChocolateIcecream = new IsFood("chocolate_icecream", ModConfig.SPECIAL_CONF.BASE_HUNGER, 0.5F, false, true, 20);
        baubleChocolate = new BaubleChocolate("bauble_chocolate");
        airChocolate = new AirChocolate("air_chocolate");
        miningChocolate = new MiningChocolate("mining_chocolate");
        carrotChocolate = new CarrotChocolate("carrot_chocolate");
        speedChocolate = new SpeedChocolate("speed_chocolate");
        flightChocolate = new FlightChocolate("flight_chocolate");
        ingotChocolate = new IngotChocolate("ingot_chocolate");
        pickaxeChocolate = new PickaxeChocolate("pickaxe_chocolate");
        if (SpecialDays.getToday().equals("APRIL_FOOLS_DAY")){
            hazardPotion = new IsPotion("hazard_potion", new PotionEffect[] {new PotionEffect(EffectRegister.animalBoost, 1200, 0)},new ItemStack(Items.GLASS_BOTTLE));
            animalPotion = new IsPotion("animal_potion", new PotionEffect[] {new PotionEffect(EffectRegister.waterCandle, 1200, 0)},new ItemStack(Items.GLASS_BOTTLE));}
        else{
            hazardPotion = new IsPotion("hazard_potion", new PotionEffect[] {new PotionEffect(EffectRegister.waterCandle, 1200, 0)},new ItemStack(Items.GLASS_BOTTLE));
            animalPotion = new IsPotion("animal_potion", new PotionEffect[] {new PotionEffect(EffectRegister.animalBoost, 1200, 0)},new ItemStack(Items.GLASS_BOTTLE));}
        travellerPotion = new IsPotion("chocolate_milk", new PotionEffect[] {new PotionEffect(MobEffects.NIGHT_VISION, 12000, 0), new PotionEffect(MobEffects.JUMP_BOOST, 12000, 1), new PotionEffect(MobEffects.SPEED, 12000, 0), new PotionEffect(MobEffects.SATURATION, 12000, 0)},new ItemStack(Items.GLASS_BOTTLE));
        expChocolate = new ExpChocolate("exp_chocolate");
        worldChocolate = new WorldChocolate("world_chocolate");
        explosiveChocolate = new ExplosiveChocolate("explosive_chocolate");
        mendingChocolate = new MendingChocolate("mending_chocolate");
    }
}
