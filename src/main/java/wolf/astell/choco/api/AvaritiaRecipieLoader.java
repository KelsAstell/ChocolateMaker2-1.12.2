package wolf.astell.choco.api;

import morph.avaritia.recipe.AvaritiaRecipeManager;
import morph.avaritia.recipe.extreme.ExtremeShapedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;

public class AvaritiaRecipieLoader {
    public static void addRecipe(ItemStack result, Object...objects) {
        Object[] list = new Object[objects.length + 1];
        list[0] = (Object) false;
        System.arraycopy(objects, 0, list, 1, objects.length);
        ShapedPrimer primer = CraftingHelper.parseShaped(list);
        primer.mirrored = false;
        ExtremeShapedRecipe recipe = new ExtremeShapedRecipe(result, primer);
        ResourceLocation key = result.getItem().getRegistryName();
        AvaritiaRecipeManager.EXTREME_RECIPES.put(key, recipe.setRegistryName(key));
    }

}
