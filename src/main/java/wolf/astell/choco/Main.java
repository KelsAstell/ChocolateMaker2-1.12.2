package wolf.astell.choco;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wolf.astell.choco.init.*;
import wolf.astell.choco.init.register.DogeRegister;
import wolf.astell.choco.init.register.ItemRegister;
import wolf.astell.choco.init.register.LootRegister;
import wolf.astell.choco.init.register.OreDictRegister;
import wolf.astell.choco.network.PacketHandler;
import wolf.astell.choco.init.register.BrewRegister;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
    public static final String MODID = "choco";
    public static final String VERSION = "1.0.2";
    public static CreativeTabs ProjectChocolate = new CreativeTabs("ProjectChocolate") {

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemList.foodChocolate);
        }
    };

    public Main() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ItemList.init();
//        BrewList.init();
        PacketHandler.init();
        MinecraftForge.EVENT_BUS.register(new ItemRegister());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        OreDictRegister.init();
        DogeRegister.init();
        BrewRegister.init();
        MinecraftForge.EVENT_BUS.register(new LootRegister());
    }
}
