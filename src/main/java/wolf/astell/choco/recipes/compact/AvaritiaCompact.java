package wolf.astell.choco.recipes.compact;

import morph.avaritia.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import wolf.astell.choco.api.AvaritiaRecipieLoader;
import wolf.astell.choco.init.ItemList;

import static wolf.astell.choco.init.register.compact.AvaritiaRegister.infiniteChocolate;

public class AvaritiaCompact {

    public static void init(){
        AvaritiaRecipieLoader.addCatalystIngredient(new ItemStack(ItemList.foodEnchantedChocolate));
        AvaritiaRecipieLoader.addCompressorRecipe(new ItemStack(ItemList.foodChocolateIcecream),300,true,new ItemStack(Blocks.DRAGON_EGG));
        AvaritiaRecipieLoader.addShapedRecipe(
                new ItemStack(infiniteChocolate, 1, 0),
                "    Z    ",
                " NDDDDDN ",
                " DNDNDND ",
                " DDNDNDD ",
                "ZDNDEDNDZ",
                " DDNDNDD ",
                " DNDNDND ",
                " NDDDDDN ",
                "    Z    ",
                'N', ModItems.neutronium_ingot,
                'E', ModItems.infinity_catalyst,
                'D', new ItemStack(ItemList.foodEnchantedChocolate, 1, 0),
                'Z', new ItemStack(ItemList.flightChocolate, 1, 0)
        );
    }
}
