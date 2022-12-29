package wolf.astell.choco.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import javax.annotation.Nonnull;
import java.util.List;

public class PickaxeChocolate extends ItemPickaxe
{
    public static final Item.ToolMaterial CHOCOLATE = EnumHelper.addToolMaterial("CHOCOLATE", ModConfig.TOOL_CONF.TOOL_LEVEL, ModConfig.TOOL_CONF.TOOL_DURABILITY, ModConfig.TOOL_CONF.TOOL_EFFICIENCY, 0F, ModConfig.TOOL_CONF.TOOL_ENCHANT_ABILITY);
    private int ConfirmTeleport = 0;
    public PickaxeChocolate(String name)
    {
        super(CHOCOLATE);//Maker
        this.setUnlocalizedName(name);
        this.setCreativeTab(Main.ProjectChocolate);
        this.setContainerItem(this);
        this.setMaxStackSize(1);
        this.setRegistryName(name);

        ItemList.ITEM_LIST.add(this);
    }

    @SubscribeEvent
    public void onBlockDrops(BlockEvent.HarvestDropsEvent event) {
        if(event.getHarvester() != null && event.getState() != null && event.getDrops() != null && event.getDrops().isEmpty() && !event.getHarvester().getHeldItemMainhand().isEmpty() && event.getHarvester().getHeldItemMainhand().getItem() == this && event.getState().getMaterial() == Material.GLASS && event.getState().getBlock().canSilkHarvest(event.getWorld(), event.getPos(), event.getState(), event.getHarvester()))
            event.getDrops().add(new ItemStack(event.getState().getBlock(), 1, event.getState().getBlock().getMetaFromState(event.getState())));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote && (worldIn.getBlockState(pos).getBlock() == Blocks.BEDROCK)) {
            if(ModConfig.TOOL_CONF.BEDROCK_BREAKER) {
                worldIn.playSound(null, pos, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                worldIn.playEvent(2001, pos, Block.getStateId(Blocks.BEDROCK.getDefaultState()));
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                Block.spawnAsEntity(worldIn, pos,new ItemStack(Blocks.BEDROCK));
                return EnumActionResult.SUCCESS;
            }
        }
        if(!worldIn.isRemote && (worldIn.getBlockState(pos).getBlock() == Blocks.GLASS)) {
            worldIn.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            worldIn.playEvent(2001, pos, Block.getStateId(Blocks.GLASS.getDefaultState()));
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            Block.spawnAsEntity(worldIn, pos,new ItemStack(Blocks.GLASS));
            ConfirmTeleport=0;
            return EnumActionResult.SUCCESS;
        }
        if(player.isSneaking() && ModConfig.TOOL_CONF.TELEPORT_UP) {
            if(ConfirmTeleport==30){
                player.swingArm(hand);
                worldIn.playSound(null,pos,SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,SoundCategory.PLAYERS,1.0F, 1.0F);
                player.attemptTeleport(player.posX,72,player.posZ);
                player.sendMessage(new TextComponentTranslation("message.choco.teleport_confirm").setStyle(new Style().setColor(TextFormatting.YELLOW)));
                ConfirmTeleport=0;
            }
            ConfirmTeleport++;
            player.swingArm(hand);
        }else {
            ConfirmTeleport=0;
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, @Nonnull ItemStack par2ItemStack) {
        return par2ItemStack.getItem() == ItemList.ingotChocolate || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.pickaxe_chocolate.desc.0"));
        if(ModConfig.TOOL_CONF.BEDROCK_BREAKER){
            tooltip.add(I18n.format("item.pickaxe_chocolate.desc.1"));
        }
        if(ModConfig.TOOL_CONF.TELEPORT_UP){
            tooltip.add(I18n.format("item.pickaxe_chocolate.desc.2"));
        }
    }
}
