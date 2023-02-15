package wolf.astell.choco.items.tools;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;

import java.util.List;

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
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        if(!entity.world.isRemote && entity instanceof EntityPlayer && ((EntityPlayer) entity).getHeldItemMainhand().getItem()==ItemList.chocoMachineGun) {
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
                if(Items.ARROW == stack.getItem()) {
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
                    NBTHelper.setInt(itemstack, TAG_ARROW_COUNT, count * 4 + NBTHelper.getInt(itemstack, TAG_ARROW_COUNT,0));
                    player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                }
            }
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        int count = NBTHelper.getInt(itemstack, TAG_ARROW_COUNT,0);
        if (!player.capabilities.isCreativeMode && count > 0) {
            NBTHelper.setInt(itemstack, TAG_ARROW_COUNT,  count - 1);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote) {
                EntityArrow arrow = new EntityArrow(world, player) {
                    @Override
                    protected ItemStack getArrowStack() {
                        return null;
                    }
                };
                arrow.setDamage(10);
                arrow.setNoGravity(true);
                arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 2.0F, 1.0F);
                world.spawnEntity(arrow);
            }
//            player.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.machine_gun.desc.0"));
        tooltip.add(I18n.format("item.machine_gun.desc.1"));
        tooltip.add(I18n.format("item.machine_gun.desc.2") + " " + NBTHelper.getInt(stack, TAG_ARROW_COUNT,0));
    }
}
