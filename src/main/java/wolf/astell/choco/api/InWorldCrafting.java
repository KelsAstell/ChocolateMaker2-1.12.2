/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.choco.api;

import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class InWorldCrafting {
    @SubscribeEvent
    public static void playerLeftClickedBlock(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("choco", "chocolate")) && isBlock("minecraft:gold_block", event.getWorld().getBlockState(event.getPos()).getBlock())) {
            if(event.getSide() == Side.SERVER) {
                craftGoldenChocolate(event);
                stack.shrink(1);
                if (shouldBreak(ModConfig.IN_WORLD_CRAFTING.GOLD_BREAK_CHANCE)){
                    BlockPos pos = event.getPos();
                    event.getWorld().playSound(null, pos, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    event.getWorld().playEvent(2001, pos, Block.getStateId(Blocks.GOLD_BLOCK.getDefaultState()));
                    event.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
        if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("choco", "golden_chocolate")) && isBlock("minecraft:enchanting_table", event.getWorld().getBlockState(event.getPos()).getBlock())) {
            if(event.getSide() == Side.SERVER) {
                craftEnchantedChocolate(event);
                stack.shrink(1);
                if (shouldBreak(ModConfig.IN_WORLD_CRAFTING.ENCHANT_BREAK_CHANCE)){
                    BlockPos pos = event.getPos();
                    event.getWorld().playSound(null, pos, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    event.getWorld().playEvent(2001, pos, Block.getStateId(Blocks.ENCHANTING_TABLE.getDefaultState()));
                    event.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
        if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("choco", "golden_chocolate")) && isBlock("minecraft:bookshelf", event.getWorld().getBlockState(event.getPos()).getBlock())) {
            if(event.getSide() == Side.SERVER) {
                craftExpChocolate(event);
                stack.shrink(1);
                if (shouldBreak(ModConfig.IN_WORLD_CRAFTING.BOOKSHELF_BREAK_CHANCE)){
                    BlockPos pos = event.getPos();
                    event.getWorld().playSound(null, pos, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    event.getWorld().playEvent(2001, pos, Block.getStateId(Blocks.BOOKSHELF.getDefaultState()));
                    event.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
        if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("choco", "golden_chocolate")) && isBlock("minecraft:tnt", event.getWorld().getBlockState(event.getPos()).getBlock())) {
            if(event.getSide() == Side.SERVER) {
                craftExplosiveChocolate(event);
                stack.shrink(1);
                BlockPos pos = event.getPos();
                event.getWorld().playSound(null, pos, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                event.getWorld().playEvent(2001, pos, Block.getStateId(Blocks.TNT.getDefaultState()));
                event.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        if(Objects.equals(stack.getItem().getRegistryName(), new ResourceLocation("choco", "pickaxe_chocolate")) && isBlock("minecraft:anvil", event.getWorld().getBlockState(event.getPos()).getBlock())) {
            if(event.getSide() == Side.SERVER) {
                craftTimeChocolate(event);

            }
        }
    }

    private static boolean shouldBreak(double chance) {
        double ran = Math.random();
        return ran < chance;
    }
    @SuppressWarnings("ConstantConditions")
    private static boolean isBlock(String unlocalizedPath, Block block) {
        ResourceLocation loc = block.getRegistryName();
        String fullPath = loc.getResourceDomain() + ":" + loc.getResourcePath();
        return fullPath.equals(unlocalizedPath);
    }

    private static void craftGoldenChocolate(PlayerInteractEvent.LeftClickBlock event) {
        Vec3d vector = event.getHitVec();
        EntityItem item = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5D, vector.z, new ItemStack(ItemList.foodGoldenChocolate, 1));
        item.setDefaultPickupDelay();
        event.getWorld().spawnEntity(item);
    }

    private static void craftEnchantedChocolate(PlayerInteractEvent.LeftClickBlock event) {
        Vec3d vector = event.getHitVec();
        EntityItem item = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5D, vector.z, new ItemStack(ItemList.foodEnchantedChocolate, 1));
        item.setDefaultPickupDelay();
        event.getWorld().spawnEntity(item);
    }

    private static void craftExpChocolate(PlayerInteractEvent.LeftClickBlock event) {
        Vec3d vector = event.getHitVec();
        EntityItem item = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5D, vector.z, new ItemStack(ItemList.expChocolate, 1));
        item.setDefaultPickupDelay();
        event.getWorld().spawnEntity(item);
    }
    private static void craftExplosiveChocolate(PlayerInteractEvent.LeftClickBlock event) {
        Vec3d vector = event.getHitVec();
        EntityItem item = new EntityItem(event.getWorld(), vector.x, vector.y + 0.5D, vector.z, new ItemStack(ItemList.explosiveChocolate, 1));
        item.setDefaultPickupDelay();
        item.setGlowing(true);
        item.setNoGravity(true);
        event.getWorld().spawnEntity(item);
    }
    private static void craftTimeChocolate(PlayerInteractEvent.LeftClickBlock event) {
        BlockPos pos = event.getPos();
        List<EntityItem> Items = event.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(-1, -1, -1), pos.add(1, 1, 1)));
        for(EntityItem item:Items){
            if(item.getItem().getItem().equals(net.minecraft.init.Items.NETHER_STAR)){
                item.setDead();
                Vec3d vector = item.getPositionVector();
                EntityItem i = new EntityItem(event.getWorld(), vector.x, vector.y + 0.1D, vector.z, new ItemStack(ItemList.foodChocolateIcecream, 1));
                i.setDefaultPickupDelay();
                i.setGlowing(true);
                i.setNoDespawn();
                i.setNoGravity(true);
                event.getWorld().spawnEntity(i);
                event.getWorld().playEvent(2001, pos, Block.getStateId(Blocks.ANVIL.getDefaultState()));
                event.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                return;
            }
        }
        event.getWorld().playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}