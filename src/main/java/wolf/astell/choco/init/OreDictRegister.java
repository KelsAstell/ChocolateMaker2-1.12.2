package wolf.astell.choco.init;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictRegister {
    public static void init(){
        OreDictionary.registerOre("foodChocolate", ItemList.foodChocolate);
        OreDictionary.registerOre("forge:ingots", ItemList.ingotChocolate);
        //should chocolate ingot be iron, for lazydoge like me.
        if (ModConfig.SPECIAL_CONF.CHOCO_IRON){
            OreDictionary.registerOre("ingotIron", ItemList.ingotChocolate);
            OreDictionary.registerOre("minecraft:beacon_payment_items", ItemList.ingotChocolate);
        }
        //add gtceu support, jk
        if (Loader.isModLoaded("GTCEU"))
        {
            OreDictionary.registerOre("chunkGtChocolate", ItemList.ingotChocolate);
        }
    }
}
