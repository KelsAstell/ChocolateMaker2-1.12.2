/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.choco.init.register;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.Log;
import wolf.astell.choco.init.ItemList;

public class CraftRegister {
    public static void init(){
        DogeCraftRegister.init();
        GameRegistry.addShapedRecipe(new ResourceLocation("world_chocolate"), new ResourceLocation(Main.MODID), new ItemStack(ItemList.worldChocolate),
                "AAA",
                "DBD",
                "CDC",
                'A', Blocks.GRASS,
                'B', Items.NETHER_STAR,
                'C', Blocks.END_STONE,
                'D', ItemList.foodGoldenChocolate);
        Log.i("Crafting Recipe Inject Succeed.");
    }
}
