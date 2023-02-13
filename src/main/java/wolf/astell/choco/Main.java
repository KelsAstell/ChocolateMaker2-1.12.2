package wolf.astell.choco;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.register.*;
import wolf.astell.choco.init.register.compact.AvaritiaRegister;
import wolf.astell.choco.init.register.compact.CoFHRegister;
import wolf.astell.choco.network.PacketHandler;
import wolf.astell.choco.recipes.compact.AvaritiaCompact;
import wolf.astell.choco.recipes.compact.CoFHCompact;



@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
    public static final String MODID = "choco";
    public static final String VERSION = "1.0.11";
    public static Main INSTANCE;
    public static CreativeTabs ProjectChocolate = new CreativeTabs("ProjectChocolate") {

        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemList.foodChocolate);
        }
    };

    public Main() {
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ItemList.init();
        if (Loader.isModLoaded("redstoneflux") && Loader.isModLoaded(("cofhcore"))){
            CoFHRegister.init();
            CoFHCompact.init();
        }
        if (Loader.isModLoaded("avaritia")){
            AvaritiaRegister.init();
            AvaritiaCompact.init();
        }
        PacketHandler.init();
        AIORegister.init();
        MinecraftForge.EVENT_BUS.register(new ItemRegister());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        OreDictRegister.init();
        CraftRegister.init();
        BrewRegister.init();
        MinecraftForge.EVENT_BUS.register(new LootRegister());
    }
}
