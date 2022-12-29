package wolf.astell.choco.init.brew.potions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolf.astell.choco.init.register.EffectRegister;

public class PotionBeheading extends PotionMod {

	public PotionBeheading() {
		super("beheading", false, 0xC32222, 3);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}
	@Override
	public boolean isReady(int duration, int amplifier) {
		return false;
	}

	public static class PotionBeheadingHandler {

		@SubscribeEvent
		public void onEntityKilled(LivingDropsEvent e) {
			if (e.getEntityLiving().getEntityWorld().isRemote) return;
			PotionEffect pe = e.getEntityLiving().getActivePotionEffect(EffectRegister.beheading);
			if (pe!=null) {
				ItemStack stack = getHead(e.getEntityLiving());
				if (!stack.isEmpty()) {
					for (EntityItem ei:e.getDrops()) {
						if (Block.getBlockFromItem(ei.getItem().getItem()) instanceof BlockSkull) {
							return;
						}
					}
					EntityItem item = new EntityItem(e.getEntityLiving().getEntityWorld(), e.getEntityLiving().posX, e.getEntityLiving().posY, e.getEntityLiving().posZ, stack);
					item.setDefaultPickupDelay();
					item.lifespan = 60;
					e.getDrops().add(item);
					}

			}
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
				ItemStack head = new ItemStack(Items.SKULL, 1, 3);
				NBTTagCompound nametag = new NBTTagCompound();
				nametag.setString("SkullOwner", entity.getDisplayName().getFormattedText());
				head.setTagCompound(nametag);
				return head;
			}
			return ItemStack.EMPTY;
		}

	}

}
