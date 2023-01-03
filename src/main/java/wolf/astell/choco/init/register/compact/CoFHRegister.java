package wolf.astell.choco.init.register.compact;

import net.minecraft.item.Item;
import wolf.astell.choco.items.linkage.PoweredChocolate;

public class CoFHRegister {
    public static Item poweredChocolate;
    public static void init() {
        poweredChocolate = new PoweredChocolate("powered_chocolate");
    }
}
