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

        private EventHandler() {
        }

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
        @Config.Comment("Mining Chocolate Haste Effect Level")
        @Config.RequiresWorldRestart
        public int HASTE_LEVEL = 2;

        @Config.LangKey("config.speed_chocolate.potion.name")
        @Config.Comment("Speed Chocolate Speeed Effect Level")
        @Config.RequiresWorldRestart
        public int SPEED_LEVEL = 2;

        @Config.LangKey("config.bauble_chocolate.potion.name")
        @Config.Comment("Fondant Chocolate Resistance Effect Level")
        @Config.RequiresWorldRestart
        public int RESISTANCE_LEVEL = 2;
    }
    @Config.LangKey("choco.TrinketConf")
    @Config.Comment("Choco Trinkets Settings")
    public static final TrinketConf TRINKET_CONF = new TrinketConf();

    public static class TrinketConf {

        @Config.LangKey("config.bauble_chocolate.godmode.name")
        @Config.Comment("Enable Fondant Chocolate God Mode")
        @Config.RequiresWorldRestart
        public boolean GODMODE = true;

        @Config.LangKey("config.air_chocolate.descend.name")
        @Config.Comment("Air Chocolate Descend Rate")
        @Config.RequiresWorldRestart
        public double DESCEND_RATE = -0.15;

        @Config.LangKey("config.air_chocolate.maxjump.name")
        @Config.Comment("Air Chocolate Max Allowed Jumps")
        @Config.RequiresWorldRestart
        public int MAX_ALLOWED_JUMPS = 5;
    }
    @Config.LangKey("choco.SpecialConf")
    @Config.Comment("DANGER Zone! Edit at your own risk.")
    public static final SpecialConf SPECIAL_CONF = new SpecialConf();

    public static class SpecialConf {

        @Config.LangKey("config.chocolate.lazydoge.name")
        @Config.Comment("Enable Super Lazy Doge Mode")
        @Config.RequiresWorldRestart
        public boolean LAZY_DOGE_MODE = false;

    }
}

