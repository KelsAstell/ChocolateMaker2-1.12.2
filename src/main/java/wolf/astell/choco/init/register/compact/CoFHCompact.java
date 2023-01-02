package wolf.astell.choco.init.register.compact;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.items.linkage.PoweredChocolate;

public class CoFHCompact {
    public static Item poweredChocolate;
    public static void init(){
        poweredChocolate = new PoweredChocolate("powered_chocolate");
        GameRegistry.addShapedRecipe(new ResourceLocation("powered_chocolate"), new ResourceLocation(Main.MODID), new ItemStack(CoFHCompact.poweredChocolate),
                "AAA",
                "ACA",
                "BBB",
                'A', ItemList.ingotChocolate,
                'B', Items.IRON_INGOT,
                'C', Blocks.REDSTONE_BLOCK);
    }
}
