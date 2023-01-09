/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.choco.init.register;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.Main;
import wolf.astell.choco.enchants.OverChoco;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class EnchantRegister {

	@SubscribeEvent
	public static void onEnchantmentRegistration(RegistryEvent.Register<Enchantment> event) {
		event.getRegistry().registerAll(new OverChoco());
	}
}
