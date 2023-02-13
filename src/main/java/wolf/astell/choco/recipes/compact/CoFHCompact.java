package wolf.astell.choco.recipes.compact;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

import static wolf.astell.choco.init.register.compact.CoFHRegister.battery;
import static wolf.astell.choco.init.register.compact.CoFHRegister.batteryCase;

public class CoFHCompact {

    public static void init(){
        GameRegistry.addShapedRecipe(new ResourceLocation("battery"), new ResourceLocation(Main.MODID), new ItemStack(battery),
                " A ",
                "DBD",
                "DCD",
                'A', Items.IRON_NUGGET,
                'B', Items.REDSTONE,
                'C', ItemList.ringOfAir,
                'D', ItemList.foodChocolate);
        GameRegistry.addShapedRecipe(new ResourceLocation("battery_case"), new ResourceLocation(Main.MODID), new ItemStack(batteryCase),
                "BBB",
                "A C",
                "BBB",
                'A', Items.IRON_NUGGET,
                'B', Items.IRON_INGOT,
                'C', ItemList.ringOfAir);
    }
}
