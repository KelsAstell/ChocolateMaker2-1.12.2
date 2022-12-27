package wolf.astell.choco.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

@EventBusSubscriber
public class IngotChocolate extends Item
{
	public IngotChocolate(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

}