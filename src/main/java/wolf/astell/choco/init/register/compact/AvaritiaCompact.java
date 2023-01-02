package wolf.astell.choco.init.register.compact;

import morph.avaritia.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import wolf.astell.choco.api.AvaritiaRecipieLoader;
import wolf.astell.choco.init.ItemList;

public class AvaritiaCompact {
    public static void init(){
        if (Loader.isModLoaded("avaritia"))
        {
            AvaritiaRecipieLoader.addRecipe(
                    new ItemStack(ItemList.foodChocolate, 1, 0),
                    "    S    ",
                    " NDDDDDN ",
                    " DNDDDND ",
                    " DDNDNDD ",
                    "SDDDEDDDS",
                    " DDNDNDD ",
                    " DNDDDND ",
                    " NDDDDDN ",
                    "    S    ",
                    'N', ModItems.neutron_nugget,
                    'S', Items.NETHER_STAR,
                    'E', new ItemStack(ItemList.foodEnchantedChocolate, 1, 0),
                    'D', new ItemStack(ItemList.foodGoldenChocolate, 1, 0)
            );
        }
    }
}
