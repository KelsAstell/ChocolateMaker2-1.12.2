
package wolf.astell.choco.init.brew;

import net.minecraft.potion.PotionEffect;
import wolf.astell.choco.api.Brew;
import wolf.astell.choco.init.register.EffectRegister;


public class BrewList {

	public static Brew waterCandle;


	public static void init() {
		waterCandle = new BrewMod("waterCandle", 0x232323, new PotionEffect(EffectRegister.waterCandle, 1800, 0));
	}
}
