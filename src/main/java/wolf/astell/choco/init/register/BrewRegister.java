/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.choco.init.register;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.recipes.brew.RecipeManager;


@Mod.EventBusSubscriber
public class BrewRegister {
	public static void init() {
		RecipeManager.registerRecipe(new ItemStack(Items.GLASS_BOTTLE), new ItemStack(ItemList.foodHotChocolate), new ItemStack(ItemList.foodChocolate));
		RecipeManager.registerRecipe(new ItemStack(ItemList.foodHotChocolate), new ItemStack(ItemList.hazardPotion), new ItemStack(Items.GUNPOWDER));
		RecipeManager.registerRecipe(new ItemStack(ItemList.foodHotChocolate), new ItemStack(ItemList.animalPotion), new ItemStack(Items.EGG));
		RecipeManager.registerRecipe(new ItemStack(ItemList.foodHotChocolate), new ItemStack(ItemList.travellerPotion), new ItemStack(Items.MILK_BUCKET));
	}
}
