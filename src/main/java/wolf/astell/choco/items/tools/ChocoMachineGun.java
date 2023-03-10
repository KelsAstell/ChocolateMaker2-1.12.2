package wolf.astell.choco.items.tools;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.register.AdvancementRegister;

import java.util.List;
import java.util.Objects;

public class ChocoMachineGun extends Item {
    public ChocoMachineGun(String name) {
        super();
        this.setUnlocalizedName(name);
        this.setCreativeTab(Main.ProjectChocolate);
        this.setContainerItem(this);
        this.setMaxStackSize(1);
        this.setRegistryName(name);

        ItemList.ITEM_LIST.add(this);
    }
    public static final String TAG_ARROW_COUNT = "arrowCount";
    public static final String COOL_DOWN = "coolDown";
    public static final String LASER = "laser";
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        if (!entity.world.isRemote && entity instanceof EntityPlayer ){
            if (NBTHelper.getInt(itemstack,COOL_DOWN,0) > 0 && world.getTotalWorldTime() % 10 == 0){
                NBTHelper.setInt(itemstack,COOL_DOWN,NBTHelper.getInt(itemstack,COOL_DOWN,0) - 1);
            }
            if(((EntityPlayer) entity).getHeldItemOffhand().getItem()==ItemList.chocoMachineGun) {
                EntityPlayer player = (EntityPlayer) entity;
                int highest = -1;
                int[] arrow_count = new int[player.inventory.getSizeInventory() - player.inventory.armorInventory.size()];
                int[] choco_count = new int[player.inventory.getSizeInventory() - player.inventory.armorInventory.size()];
                for(int i = 0; i < arrow_count.length; i++) {
                    ItemStack stack = player.inventory.getStackInSlot(i);
                    if(stack.isEmpty()) {
                        continue;
                    }
                    if(Items.ARROW == stack.getItem()) {
                        arrow_count[i] = stack.getCount();
                        if(highest == -1)
                            highest = i;
                        else highest = arrow_count[i] > arrow_count[highest] && highest > 8 ? i : highest;
                    }
                    if(ItemList.foodChocolate == stack.getItem()) {
                        choco_count[i] = stack.getCount();
                        if(highest == -1)
                            highest = i;
                        else highest = choco_count[i] > choco_count[highest] && highest > 8 ? i : highest;
                    }

                }
                if(highest == -1) {
                } else {
                    for(int i = 0; i < arrow_count.length; i++) {
                        int count = arrow_count[i];
                        if(count == 0)
                            continue;
                        NBTHelper.setInt(itemstack, TAG_ARROW_COUNT, count + NBTHelper.getInt(itemstack, TAG_ARROW_COUNT,0));
                        player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                    }
                    for(int i = 0; i < choco_count.length; i++) {
                        int count = choco_count[i];
                        if(count == 0)
                            continue;
                        NBTHelper.setInt(itemstack, TAG_ARROW_COUNT, count * 3 + NBTHelper.getInt(itemstack, TAG_ARROW_COUNT,0));
                        player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                    }
                }
            }
        }

    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        int count = NBTHelper.getInt(itemstack, TAG_ARROW_COUNT,0);
        if ((count > 0 || player.capabilities.isCreativeMode) && NBTHelper.getInt(itemstack,COOL_DOWN,0) <= 100) {
            if (!player.capabilities.isCreativeMode){
                NBTHelper.setInt(itemstack, TAG_ARROW_COUNT,  count - 1);
                NBTHelper.setInt(itemstack,COOL_DOWN,3 + NBTHelper.getInt(itemstack,COOL_DOWN,0));
            }
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote) {
                ItemArrow itemarrow = (ItemArrow) (Items.ARROW);
                EntityArrow arrow = itemarrow.createArrow(world, itemstack, player);
                arrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
                arrow.setDamage(6);
                arrow.setAlwaysRenderNameTag(false);
                if (player.isSneaking()){
                    if (player.getName().equals("Kels_Astell")){
                        arrow.setDamage(300);
                        arrow.setNoGravity(true);
                        arrow.setIsCritical(true);
                        NBTHelper.setInt(itemstack,COOL_DOWN,17 + NBTHelper.getInt(itemstack,COOL_DOWN,0));
                        arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 5.0F, 1.0F);
                        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
                    }
                    if (NBTHelper.getBoolean(itemstack,LASER,false)){
                        arrow.setDamage(500);
                        arrow.setNoGravity(true);
                        arrow.setIsCritical(true);
                        NBTHelper.setInt(itemstack,COOL_DOWN,57 + NBTHelper.getInt(itemstack,COOL_DOWN,0));
                        arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 6.0F, 1.0F);
                        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
                    }
                }
                arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 1.0F);
                world.spawnEntity(arrow);
                if (NBTHelper.getInt(itemstack,TAG_ARROW_COUNT,0) % 8 == 0){
                    ItemArrow spectralArrow = (ItemArrow) (Items.ARROW);
                    EntityArrow arrow1 = spectralArrow.createArrow(world,itemstack,player);
                    arrow1.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
                    arrow1.setIsCritical(true);
                    arrow1.setKnockbackStrength(2);
                    arrow1.setDamage(12);
                    arrow1.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 1.0F);
                }
            }
            player.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        if (NBTHelper.getInt(itemstack,COOL_DOWN,0) > 100){
            player.sendMessage(new TextComponentTranslation("item.machine_gun.overheat"));
        }
        return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }

    @SubscribeEvent
    public void onEntityHurt(LivingDamageEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer entity = (EntityPlayer)event.getSource().getTrueSource();
            if (entity.getHeldItemMainhand().getItem()==ItemList.chocoMachineGun && event.getAmount() >= 200){
                AdvancementRegister.STAR_SHOOTER.trigger((EntityPlayerMP) entity);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.machine_gun.desc.0"));
        tooltip.add(I18n.format("item.machine_gun.desc.1"));
        tooltip.add(I18n.format("item.machine_gun.desc.4"));
        if (NBTHelper.getBoolean(stack,LASER,false)){
            tooltip.add(I18n.format("item.machine_gun.desc.5"));
        }
        tooltip.add(I18n.format("item.machine_gun.desc.3") + " " + NBTHelper.getInt(stack, COOL_DOWN,0) + "%");
        tooltip.add(I18n.format("item.machine_gun.desc.2") + " " + NBTHelper.getInt(stack, TAG_ARROW_COUNT,0));
    }
}
