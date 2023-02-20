package wolf.astell.choco.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.Main;

@Mod.EventBusSubscriber(modid = Main.MODID)

public class Annihilate extends Enchantment {
    public Annihilate() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("annihilate");
        this.setName("annihilate");
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
    public boolean isCurse(){
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public void doAnnihilate(ItemTossEvent e) {
        if (e.getEntity() != null){
            ItemStack stack = e.getEntityItem().getItem();
            if (EnchantmentHelper.getEnchantmentLevel(this,stack) != 0) {
                e.getEntityItem().setDead();
            }
        }

    }
}