package wolf.astell.choco.init.register;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

public class DogeRegister {
    public static void init(){
        if(ModConfig.SPECIAL_CONF.LAZY_DOGE_MODE){
            GameRegistry.addSmelting(ItemList.ingotChocolate, new ItemStack(ItemList.miningChocolate), 5f);
            GameRegistry.addSmelting(ItemList.miningChocolate, new ItemStack(ItemList.speedChocolate), 5f);
            GameRegistry.addSmelting(ItemList.foodEnchantedChocolate, new ItemStack(Blocks.DRAGON_EGG),10f);
        }
    }
}