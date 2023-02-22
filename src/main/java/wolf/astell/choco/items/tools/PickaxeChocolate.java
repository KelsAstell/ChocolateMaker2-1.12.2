package wolf.astell.choco.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.api.SpecialDays;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class PickaxeChocolate extends ItemPickaxe {
    public static final Item.ToolMaterial CHOCOLATE = EnumHelper.addToolMaterial("CHOCOLATE", ModConfig.TOOL_CONF.TOOL_LEVEL, ModConfig.TOOL_CONF.TOOL_DURABILITY, ModConfig.TOOL_CONF.TOOL_EFFICIENCY, (float) ModConfig.TOOL_CONF.TOOL_ATTACK_DAMAGE - 2, ModConfig.TOOL_CONF.TOOL_ENCHANT_ABILITY);
    private int ConfirmTeleport = 0;
    public static final String TAG_CHOCOLATE_COUNT = "chocolateCount";
    DecimalFormat df = new DecimalFormat("#0.00");
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
    static Random r = new Random();

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
    public static class AIOExtraLoot {
        @SubscribeEvent
        public void onEntityKilled(LivingDropsEvent event) {
            if (event.getEntityLiving().getEntityWorld().isRemote) return;

            World world = event.getEntityLiving().getEntityWorld();
            if (SpecialDays.getToday().equals("BIRTHDAY_ASTELL")){
                Vec3d vector = event.getEntity().getPositionVector();
                EntityItem item = new EntityItem(world, vector.x, vector.y + 0.5D, vector.z, new ItemStack(ItemList.baubleChocolate, 1));
                item.setDefaultPickupDelay();
                item.makeFakeItem();
                item.lifespan = 620;
                item.motionY = 0.3;
                world.spawnEntity(item);
            }
            if (event.getSource().getTrueSource() instanceof EntityPlayer){
                EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
                if (attacker.getHeldItemMainhand().getItem()==ItemList.pickaxeChocolate && attacker.getHeldItemOffhand().getItem()==ItemList.pickaxeChocolate){
                    attacker.world.spawnEntity(new EntityTNTPrimed(attacker.world,attacker.posX, attacker.posY + 100, attacker.posZ,event.getEntityLiving()));
                    attacker.sendMessage(new TextComponentTranslation("message.choco.watch_out"));
                    attacker.addPotionEffect(new PotionEffect(MobEffects.GLOWING,100,0,true,false));
                    attacker.addPotionEffect(new PotionEffect(MobEffects.LEVITATION,100,8,true,false));
                    attacker.setHealth(1);
                    world.playSound(attacker, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.PLAYERS, 0.8F, world.rand.nextFloat() * 0.1F + 0.9F);
                }
                if (rollChance() && attacker.getHeldItemOffhand().getItem()==ItemList.pickaxeChocolate && !event.getDrops().isEmpty()) {
                    ItemStack bonus = getBonusLoot(event.getEntityLiving(), event.getDrops().get(r.nextInt(event.getDrops().size())).getItem());
                    EntityItem eni = new EntityItem(event.getEntityLiving().getEntityWorld(), event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, bonus);
                    eni.setDefaultPickupDelay();
                    eni.lifespan = 6000;
                    event.getDrops().add(eni);
                }
            }
        }
        private boolean rollChance() {
            double ran = Math.random();
            double chance = ModConfig.TOOL_CONF.EX_LOOT_CHANCE;
            return ran < chance;
        }

        private ItemStack getBonusLoot(EntityLivingBase entity, ItemStack loot) {
            if(entity instanceof EntityMob) {
                return new ItemStack(loot.getItem(), Math.max(10,loot.getCount() * 2));
            } else if(entity instanceof EntityPlayer) {
                ItemStack egg = new ItemStack(Items.EGG, 1, 0);
                egg.addEnchantment(Enchantments.UNBREAKING,1);
                egg.setStackDisplayName(I18n.format("message.choco.player_egg"));
                return egg;
            }
            return ItemStack.EMPTY;
        }
    }


    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
        if(!entity.world.isRemote && entity instanceof EntityPlayer && ((EntityPlayer) entity).getHeldItemMainhand().getItem()==ItemList.pickaxeChocolate) {
            EntityPlayer player = (EntityPlayer) entity;
            int highest = -1;
            int[] counts = new int[player.inventory.getSizeInventory() - player.inventory.armorInventory.size()];

            for(int i = 0; i < counts.length; i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if(stack.isEmpty()) {
                    continue;
                }
                if(ItemList.foodChocolate == stack.getItem()) {
                    counts[i] = stack.getCount();
                    if(highest == -1)
                        highest = i;
                    else highest = counts[i] > counts[highest] && highest > 8 ? i : highest;
                }
            }
            if(highest == -1) {
            } else {
                for(int i = 0; i < counts.length; i++) {
                    int count = counts[i];
                    if(count == 0)
                        continue;
                    add(itemstack, count * (EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("choco:overchoco"),itemstack) + 1));
                    player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                }
            }
        }
    }
    private static void add(ItemStack stack, int count) {
        NBTHelper.setInt(stack, TAG_CHOCOLATE_COUNT, count + NBTHelper.getInt(stack,TAG_CHOCOLATE_COUNT,0));
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
                player.sendMessage(new TextComponentTranslation("message.choco.teleport_confirm"));
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
        return par2ItemStack.getItem() == ItemList.foodChocolate || super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        float bonus = (float) Math.pow(NBTHelper.getInt(stack, PickaxeChocolate.TAG_CHOCOLATE_COUNT, 0), 1/3.0);
        if (state.getMaterial() == Material.GOURD || state.getMaterial() == Material.SAND || state.getMaterial() == Material.CLAY || state.getMaterial() == Material.WOOD){
            return (float) (15 + 0.3 * bonus);
        }
        return (float) (2 + 0.2 * bonus);
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
        if(ModConfig.TOOL_CONF.EX_LOOT){
            tooltip.add(I18n.format("item.pickaxe_chocolate.desc.5"));
        }
        tooltip.add(I18n.format("item.pickaxe_chocolate.desc.6"));
        if(ModConfig.TOOL_CONF.BEHEADING && ModConfig.TOOL_CONF.BEHEADING_CHANCE != 0.0){
            tooltip.add(ModConfig.TOOL_CONF.BEHEADING_CHANCE * 100 + "%" + I18n.format("item.pickaxe_chocolate.desc.4"));
            if(ModConfig.TOOL_CONF.BEDROCK_BREAKER &&
                    ModConfig.TOOL_CONF.TELEPORT_UP &&
                    ModConfig.TOOL_CONF.TSOT &&
                    ModConfig.TOOL_CONF.EX_LOOT){
                tooltip.add(I18n.format("item.pickaxe_chocolate_aio.desc"));
            }
        }
        tooltip.add(I18n.format("item.pickaxe_chocolate.desc.7") + " " + df.format(NBTHelper.getInt(stack, PickaxeChocolate.TAG_CHOCOLATE_COUNT, 0)));
        tooltip.add(I18n.format("item.pickaxe_chocolate.desc.8") + " " + df.format((float) 0.3* Math.pow(NBTHelper.getInt(stack, PickaxeChocolate.TAG_CHOCOLATE_COUNT, 0), 1/3.0)));
    }
}
