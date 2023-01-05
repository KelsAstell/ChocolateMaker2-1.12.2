/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.choco.init.register.compact;

import net.minecraft.item.Item;
import wolf.astell.choco.items.linkage.ChocoSingularity;
import wolf.astell.choco.items.linkage.InfiniteBaubleChocolate;
import wolf.astell.choco.items.linkage.InfiniteChocolate;

public class AvaritiaRegister {
    public static Item infiniteChocolate;
    public static Item infiniteBaubleChocolate;
    public static Item chocolateSingularity;
    public static void init() {
        infiniteChocolate = new InfiniteChocolate("infinite_chocolate");
        infiniteBaubleChocolate = new InfiniteBaubleChocolate("infinite_bauble_chocolate");
        chocolateSingularity = new ChocoSingularity("singularity");
    }
}
