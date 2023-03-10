package wolf.astell.choco.init.register;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;


@Mod.EventBusSubscriber(modid = Main.MODID)
public class ItemRegister {
    @SubscribeEvent
    public void register(RegistryEvent.Register<Item> event) {

        IForgeRegistry<Item> registry = event.getRegistry();
        for (Item object : ItemList.ITEM_LIST)
        {
            registry.register(object);
        }
    }
}