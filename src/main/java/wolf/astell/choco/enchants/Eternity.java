package wolf.astell.choco.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.Main;

@Mod.EventBusSubscriber(modid = Main.MODID)

public class Eternity extends Enchantment {
    public Eternity() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("eternity");
        this.setName("eternity");
    }
    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    public boolean canApply(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @SubscribeEvent
    public void setEternal(ItemExpireEvent e) {
        ItemStack stack = e.getEntityItem().getItem();
        if (EnchantmentHelper.getEnchantments(stack).containsKey(this)) {
            e.setExtraLife(Integer.MAX_VALUE);
            e.setCanceled(true);
        }
    }
}