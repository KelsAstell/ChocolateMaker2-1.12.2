/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.choco.init.register.compact;

import net.minecraft.item.Item;
import wolf.astell.choco.items.linkage.BatteryCase;
import wolf.astell.choco.items.linkage.Battery;

public class CoFHRegister {
    public static Item battery;
    public static Item batteryCase;
    public static void init() {
        battery = new Battery("battery");
        batteryCase = new BatteryCase("battery_case");
    }
}
