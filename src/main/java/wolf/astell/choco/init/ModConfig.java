/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
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
    public static class ConfigSyncHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
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
        @Config.RequiresMcRestart
        @Config.RangeInt(min = -1)
        public int HASTE_LEVEL = 2;

        @Config.LangKey("config.speed_chocolate.potion.name")
        @Config.Comment("Speed Chocolate Speeed Effect Level, default 2")
        @Config.RequiresMcRestart
        @Config.RangeInt(min = -1)
        public int SPEED_LEVEL = 2;

        @Config.LangKey("config.bauble_chocolate.potion.name")
        @Config.Comment("Fondant Chocolate Resistance Effect Level, default 2")
        @Config.RequiresMcRestart
        @Config.RangeInt(min = -1)
        public int RESISTANCE_LEVEL = 2;
    }
    @Config.LangKey("choco.TrinketConf")
    @Config.Comment("Choco Trinkets Settings")
    public static final TrinketConf TRINKET_CONF = new TrinketConf();

    public static class TrinketConf {

        @Config.LangKey("config.bauble_chocolate.godmode.name")
        @Config.Comment("Enable Fondant Chocolate God Mode, default true")
        public boolean GODMODE = true;

        @Config.LangKey("config.air_chocolate.descend.name")
        @Config.Comment("Air Chocolate Ascend Rate, default 0.15")
        @Config.RangeDouble(min = -0.15)
        public double DESCEND_RATE = 0.15;

        @Config.LangKey("config.air_chocolate.glide.name")
        @Config.Comment("Enable Air Chocolate Glide Up, default true")
        public boolean GLIDE_UP = true;

        @Config.LangKey("config.air_chocolate.maxjump.name")
        @Config.Comment("Air Chocolate Max Allowed Jumps, default 5")
        @Config.RangeInt(min = 0)
        public int MAX_ALLOWED_JUMPS = 5;

        @Config.LangKey("config.flight_chocolate.doExhaust.name")
        @Config.Comment("Should Flight Chocolate Drain Player Food Level, default true")
        public boolean DO_FLIGHT_EXHAUST = true;

        @Config.LangKey("config.flight_chocolate.enable.name")
        @Config.Comment("Enable Flight Chocolate Flight Ability, default true")
        public boolean ENABLE_FLIGHT = true;

        @Config.LangKey("config.time_chocolate.enable.name")
        @Config.Comment("Time Chocolate MAX Absorption Amount, 0 to disable")
        @Config.RangeInt(min = 0)
        public int ABSORB_AMOUNT = 10;

        @Config.LangKey("config.reach_chocolate.enable.name")
        @Config.Comment("Reach Chocolate MAX Reach Bonus Distance")
        @Config.RangeDouble(min = 0)
        public double REACH_AMOUNT = 24.0;
    }
    @Config.LangKey("choco.SpecialConf")
    @Config.Comment("DANGER Zone! Edit at your own risk.")
    public static final SpecialConf SPECIAL_CONF = new SpecialConf();

    public static class SpecialConf {

        @Config.LangKey("config.chocolate.lazydoge.name")
        @Config.Comment("Enable Super Lazy Doge Mode, default false")
        @Config.RequiresMcRestart
        public boolean LAZY_DOGE_MODE = false;

        @Config.LangKey("config.chocolate.basehunger.name")
        @Config.Comment("Set Base Hunger Provided by Chocolate, default 4")
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 1)
        public int BASE_HUNGER = 4;

        @Config.LangKey("config.chocolate.consumespeed.name")
        @Config.Comment("Set Consume Speed of Chocolate, default 20")
        @Config.RangeInt(min = 10)
        public int CONSUME_SPEED = 20;

        @Config.LangKey("config.chocolate.isChocoIron.name")
        @Config.Comment("Chocolate Ingot = Iron Ingot. Default true")
        @Config.RequiresMcRestart
        public boolean CHOCO_IRON = true;

        @Config.LangKey("config.chocolate.doWorldTeleport.name")
        @Config.Comment("Enable World Chocolate World Teleport Function, default true")
        public boolean WORLD_TRAVELLER = true;
        @Config.LangKey("config.chocolate.customDim.name")
        @Config.Comment("Enable World Chocolate Custom Dim ID. CAN CAUSE CRASH IF YOU DON'T EDIT CORRECTLY, DO NOT REPORT THIS AS AN ISSUE")
        public int[] CUSTOM_DIM_ID = {-1,0,1};
        @Config.LangKey("config.chocolate.customWorldTeleport.name")
        @Config.Comment("Set a Custom Coord for World Chocolate")
        public int[] CUSTOM_COORDS = {0,95,0};

    }

    @Config.LangKey("choco.ToolConf")
    @Config.Comment("Edit Chocolate AIO Tool Features Here.")
    public static final ToolConf TOOL_CONF = new ToolConf();

    public static class ToolConf {

        @Config.LangKey("config.chocolate.tool_level.name")
        @Config.Comment("Tool Harvest Level, default 3")
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 1)
        public int TOOL_LEVEL = 3;

        @Config.LangKey("config.chocolate.tool_durability.name")
        @Config.Comment("Tool Durability, default 2560")
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 1)
        public int TOOL_DURABILITY = 2560;

        @Config.LangKey("config.chocolate.tool_efficiency.name")
        @Config.Comment("Tool Efficiency, default 16")
        @Config.RequiresMcRestart
        public float TOOL_EFFICIENCY = 16.0F;

        @Config.LangKey("config.chocolate.tool_enchant_ability.name")
        @Config.Comment("Tool Enchant Ability, default 22")
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 1)
        public int TOOL_ENCHANT_ABILITY = 22;

        @Config.LangKey("config.chocolate.tool_attack_damage.name")
        @Config.Comment("A.I.O Attack Damage, default 12")
        @Config.RangeInt(min = 2)
        public int TOOL_ATTACK_DAMAGE = 12;

        @Config.LangKey("config.chocolate.tool_bedrock_break.name")
        @Config.Comment("Can A.I.O Break Bedrock, default true")
        public boolean BEDROCK_BREAKER = true;

        @Config.LangKey("config.chocolate.tool_teleport.name")
        @Config.Comment("Can A.I.O Teleport UP, default true")
        public boolean TELEPORT_UP = true;

        @Config.LangKey("config.chocolate.tool_time_change.name")
        @Config.Comment("Can A.I.O Change World Time, default false")
        public boolean TSOT = false;//The Sand of Time

        @Config.LangKey("config.chocolate.tool_beheading.name")
        @Config.Comment("Can A.I.O Loot Mob Heads, default true")
        @Config.RequiresMcRestart
        public boolean BEHEADING = true;

        @Config.LangKey("config.chocolate.tool_beheading_chance.name")
        @Config.Comment("A.I.O Heads Looting Chance, default 0.3")
        public double BEHEADING_CHANCE = 0.3;

        @Config.LangKey("config.chocolate.tool_extra_loot.name")
        @Config.Comment("Can A.I.O Gain Extra Loot When in Offhand, default true")
        @Config.RequiresMcRestart
        public boolean EX_LOOT = true;

        @Config.LangKey("config.chocolate.tool_extra_loot_chance.name")
        @Config.Comment("A.I.O Extra Loot Chance, default 0.5")
        public double EX_LOOT_CHANCE = 0.5;

    }

    @Config.LangKey("choco.AvaritiaConf")
    @Config.Comment("Choco Avaritia Integration Settings.")
    public static final AvaritiaConf AVARITIA_CONF = new AvaritiaConf();

    public static class AvaritiaConf {

        @Config.LangKey("config.chocolate.infinity_chocolate.name")
        @Config.Comment("Enable Infinity Chocolate Recipe, default true")
        @Config.RequiresMcRestart
        public boolean CHOCO_INFINITY = true;

        @Config.LangKey("config.chocolate.infinity_bauble_chocolate.name")
        @Config.Comment("Enable Infinity Fondant Chocolate Recipe, default true")
        @Config.RequiresMcRestart
        public boolean FONDANT_INFINITY = true;

        @Config.LangKey("config.chocolate.overpowered_fondant.name")
        @Config.Comment("Enable Infinity Fondant Chocolate 'Anti-Hardcoded Death' Algorithm, MAY NOT WORK!")
        public boolean FONDANT_PERIMETER_MODE = true;

        @Config.LangKey("config.chocolate.fondant_spmode.name")
        @Config.Comment("Enable Infinity Fondant Chocolate Spectator Mode when Dash, default true")
        public boolean FONDANT_SPECTATOR_MODE = true;

        @Config.LangKey("config.chocolate.chocky_catalyst.name")
        @Config.Comment("Add Chocolate to Infinity Catalyst Recipe, Why Not?")
        @Config.RequiresMcRestart
        public boolean ADD_CHOCO_TO_CATALYST = true;
    }
    @Config.LangKey("choco.InWorldCrafting")
    @Config.Comment("Choco In-World Crafting Settings.")
    public static final InWorldCrafting IN_WORLD_CRAFTING = new InWorldCrafting();

    public static class InWorldCrafting {

        @Config.LangKey("config.chocolate.gold_break.name")
        @Config.Comment("When Making Golden Chocolate, the Break Chance should be, default 0.06")
        public double GOLD_BREAK_CHANCE = 0.06;

        @Config.LangKey("config.chocolate.enchant_break.name")
        @Config.Comment("When Making Enchanted Chocolate, the Break Chance should be, default 0.06")
        public double ENCHANT_BREAK_CHANCE = 0.06;

        @Config.LangKey("config.chocolate.bookshelf_break.name")
        @Config.Comment("When Making Exp Chocolate, the Break Chance should be, default 0.06")
        public double BOOKSHELF_BREAK_CHANCE = 0.06;
    }

}

