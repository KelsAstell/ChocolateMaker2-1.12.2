package wolf.astell.choco.recipes.brew;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class RecipeManager {
	
	public static void registerRecipe(ItemStack in, ItemStack ingredient, ItemStack out ) {
		BrewingRecipeRegistry.addRecipe(new BrewRecipe(in, out, ingredient));
	}

}
