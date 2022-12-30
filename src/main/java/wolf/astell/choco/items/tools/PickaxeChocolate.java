package wolf.astell.choco.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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
    public static final Item.ToolMaterial CHOCOLATE = EnumHelper.addToolMaterial("CHOCOLATE", ModConfig.TOOL_CONF.TOOL_LEVEL, ModConfig.TOOL_CONF.TOOL_DURABILITY, ModConfig.TOOL_CONF.TOOL_EFFICIENCY, (float) ModConfig.TOOL_CONF.TOOL_ATTACK_DAMAGE - 2, ModConfig.TOOL_CONF.TOOL_ENCHANT_ABILITY);
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
    public static class AIOBeheadingHandler {

        @SubscribeEvent
        public void onEntityKilled(LivingDropsEvent event) {
            if (event.getEntityLiving().getEntityWorld().isRemote) return;
            if (event.getSource().getTrueSource() instanceof EntityPlayer){
                EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
                if (rollChance() && attacker.getHeldItemMainhand().getItem()==ItemList.pickaxeChocolate) {
                    ItemStack head = getHead(event.getEntityLiving());
                    if (!head.isEmpty()) {
                        for (EntityItem ei:event.getDrops()) {
                            if (Block.getBlockFromItem(ei.getItem().getItem()) instanceof BlockSkull) {
                                return;
                            }
                        }
                        EntityItem eni = new EntityItem(event.getEntityLiving().getEntityWorld(), event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, head);
                        eni.setDefaultPickupDelay();
                        eni.lifespan = 6000;
                        event.getDrops().add(eni);
                    }
                }
            }
        }

        private boolean rollChance() {
            double ran = Math.random();
            double chance = ModConfig.TOOL_CONF.BEHEADING_CHANCE;
            return ran < chance;
        }

        private ItemStack getHead(EntityLivingBase entity) {
            if(entity instanceof EntitySkeleton) {
                return new ItemStack(Items.SKULL, 1, 0);
            } else if(entity instanceof EntityWitherSkeleton) {
                return new ItemStack(Items.SKULL, 1, 1);
            } else if(entity instanceof EntityZombie) {
                return new ItemStack(Items.SKULL, 1, 2);
            } else if(entity instanceof EntityCreeper) {
                return new ItemStack(Items.SKULL, 1, 4);
            } else if(entity instanceof EntityPlayer) {
                if (entity.getDisplayName().getFormattedText().equals("Kels_Astell")){
                    ItemStack chocolate = new ItemStack(ItemList.foodEnchantedChocolate);
                    chocolate.addEnchantment(Enchantments.UNBREAKING,1);
                    chocolate.setTranslatableName("item.true_enchanted_chocolate.name");
                    return chocolate;
                }
                ItemStack head = new ItemStack(Items.SKULL, 1, 3);
                NBTTagCompound nametag = new NBTTagCompound();
                nametag.setString("SkullOwner", entity.getDisplayName().getFormattedText());
                head.setTagCompound(nametag);
                return head;
            }
            return ItemStack.EMPTY;
        }

    }

    @SubscribeEvent
    public void onBlockDrops(BlockEvent.HarvestDropsEvent event) {
        if(event.getHarvester() != null && event.getState() != null && event.getDrops() != null && event.getDrops().isEmpty() && event.getHarvester().getHeldItemMainhand().getItem() == this && event.getState().getMaterial() == Material.GLASS && event.getState().getBlock().canSilkHarvest(event.getWorld(), event.getPos(), event.getState(), event.getHarvester()))
            event.getDrops().add(new ItemStack(event.getState().getBlock(), 1, event.getState().getBlock().getMetaFromState(event.getState())));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (ModConfig.TOOL_CONF.TSOT && !world.isRemote && !player.onGround && player.isSneaking()) {
            player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE,600));
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,600,2));
            player.onGround=true;
            world.setWorldTime(6000);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
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
        if(!worldIn.isRemote && (worldIn.getBlockState(pos).getBlock() == Blocks.OBSIDIAN)) {
            worldIn.playSound(null, pos, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            worldIn.playEvent(2001, pos, Block.getStateId(Blocks.OBSIDIAN.getDefaultState()));
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            Block.spawnAsEntity(worldIn, pos,new ItemStack(Blocks.OBSIDIAN));
            ConfirmTeleport=0;
            return EnumActionResult.SUCCESS;
        }
        if(player.onGround && player.isSneaking() && ModConfig.TOOL_CONF.TELEPORT_UP) {
            if(ConfirmTeleport==25){
                player.swingArm(hand);
                worldIn.playSound(null,pos,SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,SoundCategory.PLAYERS,1.0F, 1.0F);
                player.attemptTeleport(player.posX,72,player.posZ);
                player.sendMessage(new TextComponentTranslation("message.choco.teleport_confirm").setStyle(new Style().setColor(TextFormatting.YELLOW)));
                ConfirmTeleport=0;
            }
            ConfirmTeleport++;
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
        if(ModConfig.TOOL_CONF.TSOT){
            tooltip.add(I18n.format("item.pickaxe_chocolate.desc.3"));
        }
        if(ModConfig.TOOL_CONF.BEHEADING && ModConfig.TOOL_CONF.BEHEADING_CHANCE != 0.0){
            tooltip.add(ModConfig.TOOL_CONF.BEHEADING_CHANCE * 100 + "%" + I18n.format("item.pickaxe_chocolate.desc.4"));
            if(ModConfig.TOOL_CONF.BEDROCK_BREAKER && ModConfig.TOOL_CONF.TELEPORT_UP && ModConfig.TOOL_CONF.TSOT){
                tooltip.add(I18n.format("item.pickaxe_chocolate_aio.desc"));
            }
        }

    }
}
