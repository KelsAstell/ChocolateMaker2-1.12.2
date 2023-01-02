package wolf.astell.choco.init.register.compact;

import morph.avaritia.init.ModItems;
import net.minecraft.item.ItemStack;
import wolf.astell.choco.api.AvaritiaRecipieLoader;
import wolf.astell.choco.init.ItemList;

import static wolf.astell.choco.init.IRegisterAvaritia.infiniteChocolate;

public class AvaritiaCompact {

    public static void init(){
        AvaritiaRecipieLoader.addRecipe(
                new ItemStack(infiniteChocolate, 1, 0),
                "    Z    ",
                " NDDDDDN ",
                " DNDNDND ",
                " DDNDNDD ",
                "YDNDEDNDY",
                " DDNDNDD ",
                " DNDNDND ",
                " NDDDDDN ",
                "    Z    ",
                'N', ModItems.neutronium_ingot,
                'E', ModItems.infinity_catalyst,
                'D', new ItemStack(ItemList.foodEnchantedChocolate, 1, 0),
                'Z', new ItemStack(ItemList.flightChocolate, 1, 0),
                'Y', new ItemStack(ItemList.foodChocolate, 1, 0)
        );
    }
}
