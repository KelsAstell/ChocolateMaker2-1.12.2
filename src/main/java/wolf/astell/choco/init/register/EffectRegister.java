/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.choco.init.register;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.Main;
import wolf.astell.choco.items.potions.PotionAnimalBoost;
import wolf.astell.choco.items.potions.PotionWaterCandle;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class EffectRegister {
	
	public static final Potion waterCandle = new PotionWaterCandle();
	public static final Potion animalBoost = new PotionAnimalBoost();

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> evt)
	{
		evt.getRegistry().register(waterCandle);
		evt.getRegistry().register(animalBoost);
	}
}
