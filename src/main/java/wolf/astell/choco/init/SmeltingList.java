package wolf.astell.choco.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingList {
    public static void init(){
        GameRegistry.addSmelting(ItemList.foodChocolate, new ItemStack(ItemList.ingotChocolate), 5f);
        if(ModConfig.SPECIAL_CONF.LAZY_DOGE_MODE){
            GameRegistry.addSmelting(ItemList.ingotChocolate, new ItemStack(ItemList.miningChocolate), 5f);
            GameRegistry.addSmelting(ItemList.miningChocolate, new ItemStack(ItemList.speedChocolate), 5f);
        }
    }
}
