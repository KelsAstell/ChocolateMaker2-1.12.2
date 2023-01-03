package wolf.astell.choco.init.register.compact;

import net.minecraft.item.Item;
import wolf.astell.choco.items.linkage.InfiniteChocolate;

public class AvaritiaRegister {
    public static Item infiniteChocolate;
    public static void init() {
        infiniteChocolate = new InfiniteChocolate("infinite_chocolate");
    }
}
