
package wolf.astell.choco.init.brew;

import net.minecraft.potion.PotionEffect;
import wolf.astell.choco.api.Brew;
import wolf.astell.choco.api.ChocoAPI;

public class BrewMod extends Brew {

	public BrewMod(String key, int color,  PotionEffect... effects) {
		super(key, key, color, effects);
		ChocoAPI.registerBrew(this);
	}

	@Override
	public String getUnlocalizedName() {
		return "choco.brew." + super.getUnlocalizedName();
	}

}
