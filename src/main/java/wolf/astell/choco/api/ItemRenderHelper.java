package wolf.astell.choco.api;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

import java.util.Objects;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid= Main.MODID)
public class ItemRenderHelper {
    @SubscribeEvent
    public static void Render(ModelRegistryEvent event) {
        for (Item item : ItemList.ITEM_LIST) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
        }
        for (Item item : ItemList.VARIED_ITEM_LIST) {
            ModelLoader.setCustomModelResourceLocation(item, 1, new ModelResourceLocation(item.getRegistryName() + "_1", "inventory"));
        }
        for (Item item : ItemList.VARIED_ITEM_LIST_3) {
            for(int i = 1; i < 4; i++){
                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName() + "_" + i, "inventory"));
            }
        }
    }
}
