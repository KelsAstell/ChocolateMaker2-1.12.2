package wolf.astell.choco.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.api.NBTHelper;

public class ChocoFlux extends Enchantment {
    public ChocoFlux() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("flux");
        this.setName("flux");
    }
    public static final String IS_NEW = "isNew";
    public static final String ORIGINAL_DAMAGE = "originalDamage";
    public static final String EXTRA_USAGE = "extraUsage";
    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    public boolean canApply(ItemStack stack) {
        return stack.isItemStackDamageable() || super.canApply(stack);
    }


    @SubscribeEvent
    public void onItemUse(LivingEntityUseItemEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            ItemStack stack = (((EntityPlayer) event.getEntity()).getHeldItemMainhand());
            if (EnchantmentHelper.getEnchantments(stack).containsKey(this)){
                if(NBTHelper.getBoolean(stack,IS_NEW,true)){
                    NBTHelper.setBoolean(stack,IS_NEW,false);
                    NBTHelper.setInt(stack,ORIGINAL_DAMAGE,stack.getItemDamage());
                    NBTHelper.setInt(stack,EXTRA_USAGE,stack.getMaxDamage() * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("choco:flux"),stack));
                }else{
                    if (NBTHelper.getInt(stack,EXTRA_USAGE,0) > 0 && stack.getItemDamage() != NBTHelper.getInt(stack,ORIGINAL_DAMAGE,0)){
                        int i = Math.max(0,NBTHelper.getInt(stack,EXTRA_USAGE,0) - Math.abs(stack.getItemDamage()-NBTHelper.getInt(stack,ORIGINAL_DAMAGE,0)));
                        NBTHelper.setInt(stack,EXTRA_USAGE,i);
                    }
                }
            }
        }
    }
}