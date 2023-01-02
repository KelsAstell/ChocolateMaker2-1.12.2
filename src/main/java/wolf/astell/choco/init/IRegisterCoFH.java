package wolf.astell.choco.init;

import net.minecraft.item.Item;
import wolf.astell.choco.items.linkage.PoweredChocolate;

public class IRegisterCoFH {
    public static Item poweredChocolate;
    public static void init() {
        poweredChocolate = new PoweredChocolate("powered_chocolate");
    }
}
