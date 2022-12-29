package wolf.astell.choco.recipes.brew;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipe;

public class BrewRecipe extends BrewingRecipe {

	public BrewRecipe(ItemStack input, ItemStack ingredient, ItemStack output) {
		super(input, ingredient, output);
	}

	@Override
	public boolean isInput(ItemStack stack) {
		return !stack.isEmpty() && stack.getItem().equals(this.getInput().getItem()) && PotionUtils.getPotionFromItem(stack).equals(PotionUtils.getPotionFromItem(getInput()));
	}

}
