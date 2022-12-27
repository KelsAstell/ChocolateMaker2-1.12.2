package wolf.astell.choco.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingList {
    public static void init(){
        GameRegistry.addSmelting(ItemList.foodChocolate, new ItemStack(ItemList.ingotChocolate), 5f);
    }
}
