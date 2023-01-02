package wolf.astell.choco.init;

import net.minecraft.item.Item;
import wolf.astell.choco.items.linkage.InfiniteChocolate;

public class IRegisterAvaritia {
    public static Item infiniteChocolate;
    public static void init() {
        infiniteChocolate = new InfiniteChocolate("infinite_chocolate");
    }
}
