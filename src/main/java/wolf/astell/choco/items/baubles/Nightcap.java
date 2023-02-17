package wolf.astell.choco.items.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.NBTHelper;
import wolf.astell.choco.init.ItemList;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@EventBusSubscriber
public class Nightcap extends Item implements IBauble
{
	public Nightcap(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
		ItemList.VARIED_ITEM_LIST.add(this);
	}

	public static final String IS_NEW = "isNew";
	private static final ResourceLocation lootTable = new ResourceLocation("minecraft", "chests/end_city_treasure");

	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for(int i = 0; i < baubles.getSlots(); ++i) {
				baubles.getStackInSlot(i);
				if (baubles.getStackInSlot(i).isEmpty() && baubles.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
					baubles.setStackInSlot(i, player.getHeldItem(hand).copy());
					if (!player.capabilities.isCreativeMode) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
					}
					this.onEquipped(player.getHeldItem(hand), player);
					break;
				}
			}
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (this.isInCreativeTab(tab)) {
			for (int i=0;i<2;i++){
				list.add(new ItemStack(this, 1, i));
			}
		}
	}
	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.HEAD;
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void feelLucky(PlayerWakeUpEvent e)
	{
		if(e.getEntityLiving() instanceof EntityPlayer && !e.getEntityLiving().getEntityWorld().isRemote)
		{
			IBaublesItemHandler h = BaublesApi.getBaublesHandler((EntityPlayer) e.getEntityLiving());
			for(int i : BaubleType.HEAD.getValidSlots())
			{
				ItemStack stack = h.getStackInSlot(i);
				if(!stack.isEmpty() && stack.getItem() instanceof Nightcap)
				{

					World world = e.getEntityLiving().getEntityWorld();
					Random rand = world.rand;
					ItemStack bonus;
					List<ItemStack> stacks = world.getLootTableManager().getLootTableFromLocation(lootTable).generateLootForPools(rand, new LootContext.Builder((WorldServer) world).build());
					if (stacks.isEmpty())
						return;
					else {
						Collections.shuffle(stacks);
						bonus = stacks.get(0);
					}
					if (bonus.isEmpty()){
						bonus = new ItemStack(ItemList.foodChocolateIcecream);
					}
					EntityItem item = new EntityItem(world, e.getEntityLiving().posX, e.getEntityLiving().posY + 1.2D, e.getEntityLiving().posZ, bonus);
					item.setDefaultPickupDelay();
					item.setGlowing(true);
					item.setAlwaysRenderNameTag(true);
					e.getEntityLiving().getEntityWorld().spawnEntity(item);
					stack.shrink(1);
				}
			}
		}
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity, int slot, boolean selected) {
		if (!entity.world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if (NBTHelper.getBoolean(itemstack, IS_NEW,true)){
				NBTHelper.setBoolean(itemstack, IS_NEW,false);
				if (player.getEntityWorld().rand.nextInt(10) == 9){
					itemstack.setItemDamage(1);
				}
			}
		}
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.nightcap.desc.0"));
	}
}