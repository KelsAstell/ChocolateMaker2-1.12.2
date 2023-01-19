/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.choco.init.register;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;
import wolf.astell.choco.init.register.compact.AvaritiaRegister;

public class OreDictRegister {
    public static void init(){
        OreDictionary.registerOre("foodChocolatebar", ItemList.foodChocolate);
        OreDictionary.registerOre("foodChocolate", ItemList.foodChocolate);
        OreDictionary.registerOre("forge:ingots", ItemList.ingotChocolate);
        //should chocolate ingot be iron, for lazydoge like me.
        if (ModConfig.SPECIAL_CONF.CHOCO_IRON){
            OreDictionary.registerOre("ingotIron", ItemList.ingotChocolate);
            OreDictionary.registerOre("minecraft:beacon_payment_items", ItemList.ingotChocolate);
        }
        //add gtce/gtceu support
        if (Loader.isModLoaded("gregtech"))
        {
            OreDictionary.registerOre("chunkGtChocolate", ItemList.ingotChocolate);
        }
        //add Nuclear Craft support
        if (Loader.isModLoaded("NuclearCraft"))
        {
            OreDictionary.registerOre("ingotChocolate", ItemList.foodChocolate);
        }
        //add Avaritia support
        if (Loader.isModLoaded("avaritia"))
        {
            OreDictionary.registerOre("singularityChocolate", AvaritiaRegister.chocolateSingularity);
        }
        //add TF support
        if (Loader.isModLoaded("thermalfoundation"))
        {
            OreDictionary.registerOre("coinGold", ItemList.foodGoldenChocolate);
        }
    }
}
