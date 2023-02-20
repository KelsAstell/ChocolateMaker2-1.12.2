package wolf.astell.choco.items.tools;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class OnePunch extends ItemSword {
    public static final ToolMaterial ONEPUNCH = EnumHelper.addToolMaterial("ONEPUNCH", 1, 1, 32, 1, 1);
    public OnePunch(String name)
    {
        super(ONEPUNCH);//Man
        this.setUnlocalizedName(name);
        this.setCreativeTab(Main.ProjectChocolate);
        this.setContainerItem(this);
        this.setMaxStackSize(1);
        this.setRegistryName(name);

        ItemList.ITEM_LIST.add(this);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        if(!entity.world.isRemote && entity instanceof EntityPlayer && ((EntityPlayer) entity).getHeldItemMainhand().getItem()==ItemList.onePunch) {
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("choco:annihilate"),itemstack) != 1){
                itemstack.addEnchantment(Objects.requireNonNull(Enchantment.getEnchantmentByLocation("choco:annihilate")),1);
            }
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, @Nonnull ItemStack par2ItemStack) {
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player) {
        if (player.world.isRemote) {
            return true;
        }
        target.getCombatTracker().trackDamage(new EntityDamageSource("player",player), target.getHealth(), target.getHealth());
        target.setHealth(0);
        target.onDeath(new EntityDamageSource("player", player));
        stack.shrink(1);
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!entity.world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer target = (EntityPlayer) entity;
            if (target.capabilities.isCreativeMode && !target.isDead && target.getHealth() > 0) {
                target.getCombatTracker().trackDamage(new EntityDamageSource("player",player), target.getHealth(), target.getHealth());
                target.setHealth(0);
                target.onDeath(new EntityDamageSource("player", player));
                stack.shrink(1);
                return true;
            }
        }
        if (!entity.world.isRemote && !entity.isDead) {
            entity.setDead();
            stack.shrink(1);
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.one_punch.desc.0"));
        tooltip.add(I18n.format("item.one_punch.desc.1"));
    }
}
