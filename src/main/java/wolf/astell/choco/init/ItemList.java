package wolf.astell.choco.init;

import net.minecraft.item.Item;
import wolf.astell.choco.itemtool.*;

import java.util.ArrayList;
import java.util.List;

public class ItemList{
    public static final List<Item> ITEM_LIST = new ArrayList<>();
    public static Item foodChocolate;
    public static Item foodGoldenChocolate;
    public static Item foodEnchantedChocolate;
    public static Item baubleChocolate;
    public static Item airChocolate;
    public static Item miningChocolate;
    public static Item carrotChocolate;
    public static Item speedChocolate;

    public static void init() {

        foodChocolate = new IsFood("chocolate", 8, 1F, false, true);
        foodGoldenChocolate = new IsFood("golden_chocolate", 19, 1F, false, true);
        foodEnchantedChocolate = new IsFood("enchanted_chocolate", 2560, 1F, false, true);

        baubleChocolate = new BaubleChocolate("bauble_chocolate");
        airChocolate = new AirChocolate("air_chocolate");
        miningChocolate = new MiningChocolate("mining_chocolate");
        carrotChocolate = new CarrotChocolate("carrot_chocolate");
        speedChocolate = new SpeedChocolate("speed_chocolate");
    }
}
