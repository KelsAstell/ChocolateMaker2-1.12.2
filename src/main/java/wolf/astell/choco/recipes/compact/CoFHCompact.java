package wolf.astell.choco.recipes.compact;

import net.minecraft.init.Blocks;
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
                "ADC",
                "BBB",
                'A', Items.IRON_NUGGET,
                'B', Items.IRON_INGOT,
                'C', ItemList.ringOfAir,
                'D', Items.MELON_SEEDS);
        GameRegistry.addShapedRecipe(new ResourceLocation("battery_case_1"), new ResourceLocation(Main.MODID), new ItemStack(batteryCase,1,1),
                "BBB",
                "ADC",
                "BBB",
                'A', Items.IRON_NUGGET,
                'B', Items.IRON_INGOT,
                'C', ItemList.ringOfAir,
                'D', Blocks.OBSIDIAN);
        GameRegistry.addShapedRecipe(new ResourceLocation("battery_case_2"), new ResourceLocation(Main.MODID), new ItemStack(batteryCase,1,2),
                "BBB",
                "ADC",
                "BBB",
                'A', Items.IRON_NUGGET,
                'B', Items.IRON_INGOT,
                'C', ItemList.ringOfAir,
                'D', Items.FEATHER);
        GameRegistry.addShapedRecipe(new ResourceLocation("battery_case_3"), new ResourceLocation(Main.MODID), new ItemStack(batteryCase,1,3),
                "BBB",
                "ADC",
                "BBB",
                'A', Items.IRON_NUGGET,
                'B', Items.IRON_INGOT,
                'C', ItemList.ringOfAir,
                'D', Items.GOLDEN_APPLE);
    }
}
