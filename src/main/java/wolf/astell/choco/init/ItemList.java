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

    public static void init() {

        foodChocolate = new IsFood("chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER, 1F, false, true);
        foodGoldenChocolate = new IsFood("golden_chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER * 2, 1F, false, true);
        foodEnchantedChocolate = new IsFood("enchanted_chocolate", ModConfig.SPECIAL_CONF.BASE_HUNGER * 9, 1F, false, true);
        foodChocolateIcecream = new IsFood("chocolate_icecream", ModConfig.SPECIAL_CONF.BASE_HUNGER, 1F, false, true, 8);
        baubleChocolate = new BaubleChocolate("bauble_chocolate");
        airChocolate = new AirChocolate("air_chocolate");
        miningChocolate = new MiningChocolate("mining_chocolate");
        carrotChocolate = new CarrotChocolate("carrot_chocolate");
        speedChocolate = new SpeedChocolate("speed_chocolate");
        ingotChocolate = new IngotChocolate("ingot_chocolate");
    }
}
