package wolf.astell.choco.items.tools;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;

import java.util.List;

public class VoidChocoBag extends ItemShears {
    public static final String TAG_CHOCOLATE_COUNT = "chocolateCount";

    public VoidChocoBag(String name) {
        super();
        this.setUnlocalizedName(name);
        this.setCreativeTab(Main.ProjectChocolate);
        this.setContainerItem(this);
        this.setMaxStackSize(1);
        this.setRegistryName(name);

        ItemList.ITEM_LIST.add(this);
        ItemList.VARIED_ITEM_LIST.add(this);
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        if (!entity.world.isRemote && entity instanceof EntityPlayer) {
            if (world.getTotalWorldTime() % 300 == 0){
                add(itemstack);
            }
        }
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (int i=0;i<2;i++){
                list.add(new ItemStack(this, 1, i));
            }
        }
    }

    private static void add(ItemStack stack) {
        NBTHelper.setInt(stack, TAG_CHOCOLATE_COUNT, 1 + NBTHelper.getInt(stack,TAG_CHOCOLATE_COUNT,0));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItemMainhand();
        if (!worldIn.isRemote && NBTHelper.getInt(stack, TAG_CHOCOLATE_COUNT, 0) > 0) {
            if (player.isSneaking()) {
                if (NBTHelper.getInt(stack, TAG_CHOCOLATE_COUNT, 0) >= 64) {
                    NBTHelper.setInt(stack, TAG_CHOCOLATE_COUNT, NBTHelper.getInt(stack, TAG_CHOCOLATE_COUNT, 0) - 64);
                    if (stack.getItemDamage() != 0){
                        player.addItemStackToInventory(new ItemStack(ItemList.foodGoldenChocolate, 64, stack.getItemDamage()));
                    }else{
                        player.addItemStackToInventory(new ItemStack(ItemList.foodChocolate, 64, stack.getItemDamage()));
                    }
                } else {
                    if (stack.getItemDamage() != 0){
                        player.addItemStackToInventory(new ItemStack(ItemList.foodGoldenChocolate, NBTHelper.getInt(stack, TAG_CHOCOLATE_COUNT, 0), stack.getItemDamage()));
                    }else{
                        player.addItemStackToInventory(new ItemStack(ItemList.foodChocolate, NBTHelper.getInt(stack, TAG_CHOCOLATE_COUNT, 0), stack.getItemDamage()));
                    }
                    NBTHelper.setInt(stack, TAG_CHOCOLATE_COUNT, 0);
                }
            }else {
                NBTHelper.setInt(stack, TAG_CHOCOLATE_COUNT, NBTHelper.getInt(stack, TAG_CHOCOLATE_COUNT, 0) - 1);
                if (stack.getItemDamage() != 0){
                    player.addItemStackToInventory(new ItemStack(ItemList.foodGoldenChocolate, 1, stack.getItemDamage()));
                }else{
                    player.addItemStackToInventory(new ItemStack(ItemList.foodChocolate, 1, stack.getItemDamage()));
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
        tooltip.add(I18n.format("item.void_choco_bag.desc.0"));
        tooltip.add(I18n.format("item.void_choco_bag.desc.1") +
                " " + NBTHelper.getInt(stack, TAG_CHOCOLATE_COUNT, 0));
        if (stack.getItemDamage() != 0){
            stack.setTranslatableName("item.void_choco_bag.name.1");
        }
    }

}
