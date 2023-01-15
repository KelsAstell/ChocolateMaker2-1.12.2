
package wolf.astell.choco.items.foodfunctional;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.init.ItemList;

import javax.annotation.Nonnull;
import java.util.List;

public class ExpChocolate extends Item {

	public ExpChocolate(String name)
	{
		this.setMaxStackSize(64);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 16;
	}

	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.EAT;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		player.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	@Nonnull
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && !entity.getEntityWorld().isRemote) {
			EntityPlayer player = (EntityPlayer) entity;

			if (player.isSneaking()){
				int count = stack.getCount();
				player.getFoodStats().addStats(Math.min(4 * count, 20),Math.min(count, 20));//well, you know, follow the rules
				player.getEntityWorld().spawnEntity(new EntityXPOrb(player.getEntityWorld(),player.posX,player.posY-1,player.posZ,256 * stack.getCount()));
				stack.shrink(stack.getCount());
			}else{
				player.getFoodStats().addStats(4 ,1);
				player.getEntityWorld().spawnEntity(new EntityXPOrb(player.getEntityWorld(),player.posX,player.posY-1,player.posZ,256));
				stack.shrink(1);
				if (player.getEntityWorld().rand.nextInt(7) > 5) // 1/7
				{
					getTips(player);
				}
			}
		}
		return stack;
	}

	private static void getTips(EntityPlayer player){
		String s = "tip.choco.";
		int r = player.getEntityWorld().rand.nextInt(6);
		if (!Loader.isModLoaded("avaritia") && r==3){
			player.sendMessage(new TextComponentTranslation("tip.choco.3"));
		}
		else if(r!=3){
			s = s + r;
			player.sendMessage(new TextComponentTranslation(s));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.exp_chocolate.desc.0"));
		tooltip.add(I18n.format("item.exp_chocolate.desc.1"));
	}
}
