package wolf.astell.choco.recipes.compact;

import morph.avaritia.init.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import wolf.astell.choco.api.AvaritiaUtils;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;
import wolf.astell.choco.init.register.compact.AvaritiaRegister;

import static wolf.astell.choco.init.register.compact.AvaritiaRegister.infiniteBaubleChocolate;
import static wolf.astell.choco.init.register.compact.AvaritiaRegister.infiniteChocolate;

public class AvaritiaCompact {

    public static void init(){
        if (ModConfig.AVARITIA_CONF.ADD_CHOCO_TO_CATALYST){
            AvaritiaUtils.addCatalystIngredient(new ItemStack(ItemList.foodEnchantedChocolate));
        }
        if (ModConfig.AVARITIA_CONF.CHOCO_INFINITY){
            AvaritiaUtils.addShapedRecipe(
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
        if (ModConfig.AVARITIA_CONF.FONDANT_INFINITY){
            AvaritiaUtils.addShapedRecipe(
                new ItemStack(infiniteBaubleChocolate, 1, 0),
                " A    A  ",
                "AEA  AFA ",
                "A  AA  A ",
                "A      A ",
                "A C  C A ",
                "A C  C A ",
                "A  DD  A ",
                "A      AA",
                "A AA AABB",
                'A', ModItems.neutronium_ingot,
                'C', Blocks.EMERALD_BLOCK,
                'D', new ItemStack(AvaritiaRegister.chocolateSingularity, 1, 0),
                'B', ModItems.diamond_lattice,
                'E', new ItemStack(ItemList.baubleChocolate, 1, 0),
                'F', new ItemStack(ItemList.flightChocolate, 1, 0)
        );
        }
        AvaritiaUtils.addCompressorRecipe(new ItemStack(AvaritiaRegister.chocolateSingularity),300,true,new ItemStack(ItemList.foodChocolate));
    }
}
