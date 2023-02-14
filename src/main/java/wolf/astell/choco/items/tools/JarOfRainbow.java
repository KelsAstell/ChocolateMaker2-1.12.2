package wolf.astell.choco.items.tools;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import java.util.List;

import static net.minecraft.block.BlockStainedGlass.COLOR;

public class JarOfRainbow extends ItemShears {
    public static final String TAG_GLASS_COUNT = "glassCount";
    public static final String TAG_WOOL_COUNT = "woolCount";

    public JarOfRainbow(String name) {
        super();
        this.setUnlocalizedName(name);
        this.setCreativeTab(Main.ProjectChocolate);
        this.setContainerItem(this);
        this.setMaxStackSize(1);
        this.setRegistryName(name);

        ItemList.ITEM_LIST.add(this);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        if (!entity.world.isRemote && entity instanceof EntityPlayer && ((EntityPlayer) entity).getHeldItemOffhand().getItem() == ItemList.jarOfRainbow) {
            EntityPlayer player = (EntityPlayer) entity;
            int highest = -1;
            int[] glass_counts = new int[player.inventory.getSizeInventory() - player.inventory.armorInventory.size()];
            int[] wool_counts = new int[player.inventory.getSizeInventory() - player.inventory.armorInventory.size()];

            for (int i = 0; i < glass_counts.length; i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack.isEmpty()) {
                    continue;
                }
                if (getItemFromBlock(Blocks.GLASS) == stack.getItem() || getItemFromBlock(Blocks.STAINED_GLASS) == stack.getItem()) {
                    glass_counts[i] = stack.getCount();
                    if (highest == -1)
                        highest = i;
                    else highest = glass_counts[i] > glass_counts[highest] && highest > 8 ? i : highest;
                }
                if (getItemFromBlock(Blocks.WOOL) == stack.getItem()) {
                    wool_counts[i] = stack.getCount();
                    if (highest == -1)
                        highest = i;
                    else highest = wool_counts[i] > wool_counts[highest] && highest > 8 ? i : highest;
                }
            }
            if (highest == -1) {
            } else {
                for (int i = 0; i < glass_counts.length; i++) {
                    int count_glass = glass_counts[i];
                    if (count_glass == 0)
                        continue;
                    add(itemstack, count_glass, TAG_GLASS_COUNT);
                    player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                }
                for (int i = 0; i < wool_counts.length; i++) {
                    int count_wool = wool_counts[i];
                    if (count_wool == 0)
                        continue;
                    add(itemstack, count_wool, TAG_WOOL_COUNT);
                    player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                }
            }
        }
    }

    private static void add(ItemStack stack, int count, String tag) {
        NBTHelper.setInt(stack, tag, count + NBTHelper.getInt(stack, tag, 0));
    }

//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
//        ItemStack stack = player.getHeldItemOffhand();
//        if (player.isSneaking() && NBTHelper.getInt(stack, TAG_WOOL_COUNT, 0) > 0) {
//            if (NBTHelper.getInt(stack, TAG_WOOL_COUNT, 0) >= 64) {
//                NBTHelper.setInt(stack, TAG_WOOL_COUNT, NBTHelper.getInt(stack, TAG_WOOL_COUNT, 0) - 64);
//                player.addItemStackToInventory(new ItemStack(Blocks.WOOL, 64, stack.getItemDamage()));
//            } else {
//                player.addItemStackToInventory(new ItemStack(Blocks.WOOL, NBTHelper.getInt(stack, TAG_WOOL_COUNT, 0), stack.getItemDamage()));
//                NBTHelper.setInt(stack, TAG_WOOL_COUNT, 0);
//            }
//        } else if(NBTHelper.getInt(stack, TAG_GLASS_COUNT, 0) > 0) {
//            if (NBTHelper.getInt(stack, TAG_GLASS_COUNT, 0) >= 64) {
//                NBTHelper.setInt(stack, TAG_GLASS_COUNT, NBTHelper.getInt(stack, TAG_GLASS_COUNT, 0) - 64);
//                player.addItemStackToInventory(new ItemStack(Blocks.STAINED_GLASS, 64, stack.getItemDamage()));
//            } else {
//                player.addItemStackToInventory(new ItemStack(Blocks.STAINED_GLASS, NBTHelper.getInt(stack, TAG_GLASS_COUNT, 0), stack.getItemDamage()));
//                NBTHelper.setInt(stack, TAG_GLASS_COUNT, 0);
//            }
//        }
//        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
//    }



    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItemMainhand();
        if (!worldIn.isRemote && (worldIn.getBlockState(pos).getBlock() == Blocks.GLASS || worldIn.getBlockState(pos).getBlock() == Blocks.STAINED_GLASS)) {
            if (ModConfig.TOOL_CONF.COLOR_CHANGE) {
                worldIn.playSound(null, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.playEvent(2001, pos, Block.getStateId(Blocks.STAINED_GLASS.getDefaultState()));
                worldIn.setBlockState(pos, Blocks.STAINED_GLASS.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(stack.getItemDamage())));
                return EnumActionResult.SUCCESS;
            }
        }
        if (!worldIn.isRemote && (worldIn.getBlockState(pos).getBlock() == Blocks.WOOL)) {
            if (ModConfig.TOOL_CONF.COLOR_CHANGE) {
                worldIn.playSound(null, pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.playEvent(2001, pos, Block.getStateId(Blocks.WOOL.getDefaultState()));
                worldIn.setBlockState(pos, Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(stack.getItemDamage())));
                return EnumActionResult.SUCCESS;
            }
        }
        if (!worldIn.isRemote) {
            if (player.isSneaking() && NBTHelper.getInt(stack, TAG_WOOL_COUNT, 0) > 0) {
                if (NBTHelper.getInt(stack, TAG_WOOL_COUNT, 0) >= 64) {
                    NBTHelper.setInt(stack, TAG_WOOL_COUNT, NBTHelper.getInt(stack, TAG_WOOL_COUNT, 0) - 64);
                    player.addItemStackToInventory(new ItemStack(Blocks.WOOL, 64, stack.getItemDamage()));
                } else {
                    player.addItemStackToInventory(new ItemStack(Blocks.WOOL, NBTHelper.getInt(stack, TAG_WOOL_COUNT, 0), stack.getItemDamage()));
                    NBTHelper.setInt(stack, TAG_WOOL_COUNT, 0);
                }
            } else if(NBTHelper.getInt(stack, TAG_GLASS_COUNT, 0) > 0) {
                if (NBTHelper.getInt(stack, TAG_GLASS_COUNT, 0) >= 64) {
                    NBTHelper.setInt(stack, TAG_GLASS_COUNT, NBTHelper.getInt(stack, TAG_GLASS_COUNT, 0) - 64);
                    player.addItemStackToInventory(new ItemStack(Blocks.STAINED_GLASS, 64, stack.getItemDamage()));
                } else {
                    player.addItemStackToInventory(new ItemStack(Blocks.STAINED_GLASS, NBTHelper.getInt(stack, TAG_GLASS_COUNT, 0), stack.getItemDamage()));
                    NBTHelper.setInt(stack, TAG_GLASS_COUNT, 0);
                }
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.jar_of_rainbow.desc.0"));
        tooltip.add(I18n.format("item.jar_of_rainbow.desc.1"));
        tooltip.add(I18n.format("item.jar_of_rainbow.desc.2"));
        tooltip.add(I18n.format("item.jar_of_rainbow.desc.3") +
                " " + NBTHelper.getInt(stack, TAG_GLASS_COUNT, 0) +
                " " + I18n.format("item.jar_of_rainbow.desc.5") +
                " | §f" + NBTHelper.getInt(stack, TAG_WOOL_COUNT, 0) +
                I18n.format("item.jar_of_rainbow.desc.4"));
        if (stack.getItemDamage() > 15){
            stack.setItemDamage(0);
        }
        stack.setTranslatableName("item.jar_of_rainbow.name." + stack.getItemDamage());
    }

}
