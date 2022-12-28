package wolf.astell.choco.init;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.Main;

import java.util.List;

public final class LootRegister {

	private static final List<String> TABLES = ImmutableList.of(
			"inject/abandoned_mineshaft", "inject/desert_pyramid",
			"inject/simple_dungeon", "inject/spawn_bonus_chest",
			"inject/village_blacksmith"
			);

	public LootRegister() {
		for (String s : TABLES) {
			LootTableList.register(new ResourceLocation(Main.MODID, s));
		}
		
	}

	@SubscribeEvent
	public void lootLoad(LootTableLoadEvent evt) {
		String prefix = "minecraft:chests/";
		String name = evt.getName().toString();

		if (name.startsWith(prefix)) {
			String file = name.substring(name.indexOf(prefix) + prefix.length());
			switch (file) {
			case "abandoned_mineshaft":
			case "desert_pyramid":
			case "simple_dungeon":
			case "spawn_bonus_chest":
			case "village_blacksmith": evt.getTable().addPool(getInjectPool(file)); break;
			default: break;
			}
		}
	}

	private LootPool getInjectPool(String entryName) {
		return new LootPool(new LootEntry[] { getInjectEntry(entryName, 1) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "choco_inject_pool");
	}

	private LootEntryTable getInjectEntry(String name, int weight) {
		return new LootEntryTable(new ResourceLocation(Main.MODID, "inject/" + name), weight, 0, new LootCondition[0], "choco_inject_entry");
	}

}
