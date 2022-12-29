package wolf.astell.choco.init.register;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

public class DogeRegister {
    public static void init(){
        if(ModConfig.SPECIAL_CONF.LAZY_DOGE_MODE){
            GameRegistry.addSmelting(ItemList.ingotChocolate, new ItemStack(ItemList.miningChocolate), 5f);
            GameRegistry.addSmelting(ItemList.miningChocolate, new ItemStack(ItemList.speedChocolate), 5f);
            GameRegistry.addSmelting(ItemList.foodEnchantedChocolate, new ItemStack(Blocks.DRAGON_EGG),10f);
            GameRegistry.addShapedRecipe(new ResourceLocation(Main.MODID, "test_recipe"), null,
                    new ItemStack(ItemList.ingotChocolate, 3),
                    " F ","G G"," G ",
                    'G', "blockGlass",
                    'F', Items.FLINT);
        }
    }
}
