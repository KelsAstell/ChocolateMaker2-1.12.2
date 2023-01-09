package wolf.astell.choco.enchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.items.tools.PickaxeChocolate;

public class OverChoco extends Enchantment {
    public OverChoco() {
        super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("overchoco");
        this.setName("overchoco");
    }
    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event){
        if (event.getItem().getItem().getItem() == ItemList.foodChocolate){
            ItemStack chocolate = event.getItem().getItem();
            ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
            boolean isOverchoco = EnchantmentHelper.getEnchantments(stack).containsKey(this);
            if (stack.getItem() == ItemList.pickaxeChocolate && isOverchoco){
                NBTHelper.setInt(stack, PickaxeChocolate.TAG_CHOCOLATE_COUNT, NBTHelper.getInt(stack, PickaxeChocolate.TAG_CHOCOLATE_COUNT, 0) + chocolate.getCount() * EnchantmentHelper.getEnchantmentLevel(this,stack));
                chocolate.shrink(chocolate.getCount());
            }
        }
    }
}