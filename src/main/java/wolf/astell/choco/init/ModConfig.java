package wolf.astell.choco.init;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.Main;

@Config(modid = Main.MODID, category = "")
public class ModConfig {
    @Mod.EventBusSubscriber(modid = Main.MODID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Main.MODID)) {
                ConfigManager.sync(Main.MODID, Config.Type.INSTANCE);
            }
        }
    }

    @Config.LangKey("choco.PotionConf")
    @Config.Comment("Choco Trinkets Potion Settings")
    public static final PotionConf POTION_CONF = new PotionConf();

    public static class PotionConf {
        @Config.LangKey("config.mining_chocolate.potion.name")
        @Config.Comment("Mining Chocolate Haste Effect Level, default 2")
        @Config.RequiresWorldRestart
        public int HASTE_LEVEL = 2;

        @Config.LangKey("config.speed_chocolate.potion.name")
        @Config.Comment("Speed Chocolate Speeed Effect Level, default 2")
        @Config.RequiresWorldRestart
        public int SPEED_LEVEL = 2;

        @Config.LangKey("config.bauble_chocolate.potion.name")
        @Config.Comment("Fondant Chocolate Resistance Effect Level, default 2")
        @Config.RequiresWorldRestart
        public int RESISTANCE_LEVEL = 2;
    }
    @Config.LangKey("choco.TrinketConf")
    @Config.Comment("Choco Trinkets Settings")
    public static final TrinketConf TRINKET_CONF = new TrinketConf();

    public static class TrinketConf {

        @Config.LangKey("config.bauble_chocolate.godmode.name")
        @Config.Comment("Enable Fondant Chocolate God Mode, default true")
        @Config.RequiresWorldRestart
        public boolean GODMODE = true;

        @Config.LangKey("config.air_chocolate.descend.name")
        @Config.Comment("Air Chocolate Ascend Rate, default 0.15")
        @Config.RequiresWorldRestart
        public double DESCEND_RATE = 0.15;

        @Config.LangKey("config.air_chocolate.maxjump.name")
        @Config.Comment("Air Chocolate Max Allowed Jumps, default 5")
        @Config.RequiresWorldRestart
        public int MAX_ALLOWED_JUMPS = 5;
    }
    @Config.LangKey("choco.SpecialConf")
    @Config.Comment("DANGER Zone! Edit at your own risk.")
    public static final SpecialConf SPECIAL_CONF = new SpecialConf();

    public static class SpecialConf {

        @Config.LangKey("config.chocolate.lazydoge.name")
        @Config.Comment("Enable Super Lazy Doge Mode, default false")
        @Config.RequiresWorldRestart
        public boolean LAZY_DOGE_MODE = false;

        @Config.LangKey("config.chocolate.basehunger.name")
        @Config.Comment("Set Base Hunger Provided by Chocolate, default 4")
        @Config.RequiresWorldRestart
        public int BASE_HUNGER = 4;

        @Config.LangKey("config.chocolate.consumespeed.name")
        @Config.Comment("Set Consume Speed of Chocolate, default 20")
        @Config.RequiresWorldRestart
        public int CONSUME_SPEED = 20;

        @Config.LangKey("config.chocolate.isChocoIron.name")
        @Config.Comment("Chocolate Ingot = Iron Ingot. Default true")
        @Config.RequiresWorldRestart
        public boolean CHOCO_IRON = true;

    }

    @Config.LangKey("choco.ToolConf")
    @Config.Comment("Edit Chocolate AIO Tool Features Here.")
    public static final ToolConf TOOL_CONF = new ToolConf();

    public static class ToolConf {

        @Config.LangKey("config.chocolate.tool_level.name")
        @Config.Comment("Tool Harvest Level, default 3")
        @Config.RequiresWorldRestart
        public int TOOL_LEVEL = 3;

        @Config.LangKey("config.chocolate.tool_durability.name")
        @Config.Comment("Tool Durability, default 2560")
        @Config.RequiresWorldRestart
        public int TOOL_DURABILITY = 2560;

        @Config.LangKey("config.chocolate.tool_efficiency.name")
        @Config.Comment("Tool Efficiency, default 16")
        @Config.RequiresWorldRestart
        public float TOOL_EFFICIENCY = 16.0F;

        @Config.LangKey("config.chocolate.tool_enchant_ability.name")
        @Config.Comment("Tool Enchant Ability, default 22")
        @Config.RequiresWorldRestart
        public int TOOL_ENCHANT_ABILITY = 22;

        @Config.LangKey("config.chocolate.tool_bedrock_break.name")
        @Config.Comment("Can A.I.O Break Bedrock, default true")
        @Config.RequiresWorldRestart
        public boolean BEDROCK_BREAKER = true;

        @Config.LangKey("config.chocolate.tool_teleport.name")
        @Config.Comment("Can A.I.O Teleport UP, default true")
        @Config.RequiresWorldRestart
        public boolean TELEPORT_UP = true;

    }

}

