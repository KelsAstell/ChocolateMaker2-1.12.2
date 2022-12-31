package wolf.astell.choco.init.register;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.Log;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

public class DogeRegister {
    public static void init(){
        if(ModConfig.SPECIAL_CONF.LAZY_DOGE_MODE){
            GameRegistry.addShapedRecipe(new ResourceLocation("lazydoge_aio"), new ResourceLocation(Main.MODID), new ItemStack(ItemList.pickaxeChocolate),
                    "AAA",
                    " C ",
                    " B ",
                    'A', ItemList.foodGoldenChocolate,
                    'B', Items.STICK,
                    'C', Items.IRON_PICKAXE);
            GameRegistry.addShapedRecipe(new ResourceLocation("lazydoge_mining_chocolate"), new ResourceLocation(Main.MODID), new ItemStack(ItemList.miningChocolate),
                    "AAA",
                    "ACA",
                    "ABA",
                    'A', Blocks.COBBLESTONE,
                    'B', Items.STONE_PICKAXE,
                    'C', ItemList.foodChocolate);
            GameRegistry.addShapedRecipe(new ResourceLocation("lazydoge_speed_chocolate"), new ResourceLocation(Main.MODID), new ItemStack(ItemList.speedChocolate),
                    "AAA",
                    "A A",
                    "ABA",
                    'A', Items.SUGAR,
                    'B', ItemList.foodChocolate);
            GameRegistry.addShapedRecipe(new ResourceLocation("lazydoge_goldblock"), new ResourceLocation(Main.MODID), new ItemStack(Blocks.GOLD_BLOCK),
                    "AAA",
                    "ABA",
                    "AAA",
                    'A', ItemList.foodChocolate,
                    'B', Items.GOLD_INGOT);
            GameRegistry.addShapedRecipe(new ResourceLocation("lazydoge_redstoneblock"), new ResourceLocation(Main.MODID), new ItemStack(Blocks.REDSTONE_BLOCK),
                    "AAA",
                    "BBB",
                    "AAA",
                    'A', ItemList.foodChocolate,
                    'B', Items.REDSTONE);
            GameRegistry.addShapedRecipe(new ResourceLocation("lazydoge_emerald"), new ResourceLocation(Main.MODID), new ItemStack(Items.EMERALD),
                    " B ",
                    "BAB",
                    " B ",
                    'A', Blocks.GRASS,
                    'B', Blocks.GLASS);
            GameRegistry.addShapedRecipe(new ResourceLocation("lazydoge_dragonegg"), new ResourceLocation(Main.MODID), new ItemStack(Blocks.DRAGON_EGG),
                    "BBB",
                    "BAB",
                    "BBB",
                    'A', ItemList.foodEnchantedChocolate,
                    'B', Blocks.OBSIDIAN);
            GameRegistry.addSmelting(ItemList.foodChocolate, new ItemStack(ItemList.ingotChocolate), 10f);
            Log.i("Super Chocolate Maker is Running in Dogemode, Recipe Inject Succeed.");
        }
    }
}
