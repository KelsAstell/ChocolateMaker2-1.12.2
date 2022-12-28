package wolf.astell.choco.init;

import net.minecraft.item.Item;
import wolf.astell.choco.items.*;

import java.util.ArrayList;
import java.util.List;

public class ItemList{
    public static final List<Item> ITEM_LIST = new ArrayList<>();
    public static Item foodChocolate;
    public static Item foodGoldenChocolate;
    public static Item foodEnchantedChocolate;
    public static Item foodChocolateIcecream;
    public static Item baubleChocolate;
    public static Item airChocolate;
    public static Item miningChocolate;
    public static Item carrotChocolate;
    public static Item speedChocolate;
    public static Item ingotChocolate;
    public static Item pickaxeChocolate;


    public static void init() {

        foodChocolate = new IsFood("chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER, 1F, false, true, null,ModConfig.SPECIAL_CONF.CONSUME_SPEED);
        foodGoldenChocolate = new IsFood("golden_chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER * 2, 1F, false, true,null,ModConfig.SPECIAL_CONF.CONSUME_SPEED);
        foodEnchantedChocolate = new IsFood("enchanted_chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER * 5, 1F, false, true,null,ModConfig.SPECIAL_CONF.CONSUME_SPEED);
        foodChocolateIcecream = new IsFood("chocolate_icecream", ModConfig.SPECIAL_CONF.BASE_HUNGER, 1F, false, true, 8);
        baubleChocolate = new BaubleChocolate("bauble_chocolate");
        airChocolate = new AirChocolate("air_chocolate");
        miningChocolate = new MiningChocolate("mining_chocolate");
        carrotChocolate = new CarrotChocolate("carrot_chocolate");
        speedChocolate = new SpeedChocolate("speed_chocolate");
        ingotChocolate = new IngotChocolate("ingot_chocolate");
        pickaxeChocolate = new PickaxeChocolate("pickaxe_chocolate");
    }
}
