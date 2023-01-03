package wolf.astell.choco.recipes.compact;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

import static wolf.astell.choco.init.register.compact.CoFHRegister.poweredChocolate;

public class CoFHCompact {

    public static void init(){
        GameRegistry.addShapedRecipe(new ResourceLocation("powered_chocolate"), new ResourceLocation(Main.MODID), new ItemStack(poweredChocolate),
                "AAA",
                "ADA",
                "BCB",
                'A', ItemList.ingotChocolate,
                'B', Items.IRON_INGOT,
                'C', Blocks.REDSTONE_BLOCK,
                'D', ItemList.foodEnchantedChocolate);
    }
}
