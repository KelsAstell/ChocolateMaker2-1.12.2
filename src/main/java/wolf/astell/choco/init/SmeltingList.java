package wolf.astell.choco.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class SmeltingList {
    public static void init(){
        GameRegistry.addSmelting(ItemList.foodChocolate, new ItemStack(ItemList.ingotChocolate), 5f);
        OreDictionary.registerOre("foodChocolate", ItemList.foodChocolate);
        if (Loader.isModLoaded("GTCE"))
        {
            OreDictionary.registerOre("chunkGtChocolate", ItemList.ingotChocolate);
        }
        if(ModConfig.SPECIAL_CONF.LAZY_DOGE_MODE){
            GameRegistry.addSmelting(ItemList.ingotChocolate, new ItemStack(ItemList.miningChocolate), 5f);
            GameRegistry.addSmelting(ItemList.miningChocolate, new ItemStack(ItemList.speedChocolate), 5f);
        }
    }
}
