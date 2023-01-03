package wolf.astell.choco.api;

import morph.avaritia.recipe.AvaritiaRecipeManager;
import morph.avaritia.recipe.compressor.CompressorRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapedRecipe;
import morph.avaritia.recipe.extreme.IExtremeRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;

public class AvaritiaRecipieLoader {
    public static void addShapedRecipe(ItemStack result, Object...objects) {
        Object[] list = new Object[objects.length + 1];
        list[0] = (Object) false;
        System.arraycopy(objects, 0, list, 1, objects.length);
        ShapedPrimer primer = CraftingHelper.parseShaped(list);
        primer.mirrored = false;
        ExtremeShapedRecipe recipe = new ExtremeShapedRecipe(result, primer);
        ResourceLocation key = result.getItem().getRegistryName();
        assert key != null;
        AvaritiaRecipeManager.EXTREME_RECIPES.put(key, recipe.setRegistryName(key));
    }
    public static void addCompressorRecipe(ItemStack result, int cost, boolean absolute, ItemStack ingredient) {
        NonNullList<Ingredient> ing = NonNullList.create();
        ing.add(0,Ingredient.fromItem(ingredient.getItem()));
        CompressorRecipe recipe = new CompressorRecipe(result,cost,absolute,ing);
        ResourceLocation key = result.getItem().getRegistryName();
        assert key != null;
        AvaritiaRecipeManager.COMPRESSOR_RECIPES.put(key, recipe.setRegistryName(key));
    }
    public static void addCatalystIngredient(ItemStack stack) {
        IExtremeRecipe recipe = AvaritiaRecipeManager.EXTREME_RECIPES.get(new ResourceLocation("avaritia:items/infinity_catalyst"));
        if(recipe != null) {
            recipe.getIngredients().add(CraftingHelper.getIngredient(stack));
        }
    }

}
