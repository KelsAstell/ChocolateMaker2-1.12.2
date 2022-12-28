package wolf.astell.choco.init;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictList {
    public static void init(){
        OreDictionary.registerOre("foodChocolate", ItemList.foodChocolate);
        OreDictionary.registerOre("ingotIron", ItemList.ingotChocolate);
        OreDictionary.registerOre("minecraft:beacon_payment_items", ItemList.ingotChocolate);
        OreDictionary.registerOre("forge:ingots", ItemList.ingotChocolate);
        if (Loader.isModLoaded("GTCE"))
        {
            OreDictionary.registerOre("chunkGtChocolate", ItemList.ingotChocolate);
        }
    }
}
