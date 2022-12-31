package wolf.astell.choco.init.register;

import net.minecraftforge.common.MinecraftForge;
import wolf.astell.choco.init.ModConfig;
import wolf.astell.choco.items.tools.PickaxeChocolate;

public class AIORegister {
    public static void init(){
        if(ModConfig.TOOL_CONF.BEHEADING){
            MinecraftForge.EVENT_BUS.register(new PickaxeChocolate.AIOBeheadingHandler());
        }
        if(ModConfig.TOOL_CONF.EX_LOOT){
            MinecraftForge.EVENT_BUS.register(new PickaxeChocolate.AIOExtraLoot());
        }
    }
}
