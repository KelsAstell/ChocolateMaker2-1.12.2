
package wolf.astell.choco.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolf.astell.choco.Main;
import wolf.astell.choco.api.Vector3;
import wolf.astell.choco.init.ItemList;
import wolf.astell.choco.init.ModConfig;
import wolf.astell.choco.network.PacketHandler;
import wolf.astell.choco.network.PacketJump;

import java.util.List;

public class AirChocolate extends CloudChoco implements IBauble {
	private static int timesJumped;
	private static boolean jumpDown;
	public AirChocolate(String name) {
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(Main.ProjectChocolate);
		this.setContainerItem(this);

		ItemList.ITEM_LIST.add(this);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.BODY;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void clientWornTick(ItemStack stack, EntityLivingBase player) {
		if(player instanceof EntityPlayerSP && player == Minecraft.getMinecraft().player) {
			EntityPlayerSP playerSp = (EntityPlayerSP) player;
			Vector3 look = new Vector3(playerSp.getLookVec()).multiply(1, 0, 1).normalize();
			boolean doGlide = player.isSneaking() && !player.onGround && player.fallDistance >= 2F;
			if(doGlide) {
				player.motionY = Math.max(ModConfig.TRINKET_CONF.DESCEND_RATE, player.motionY);
				float mul = 0.7F;
				player.motionX = look.x * mul;
				player.motionZ = look.z * mul;
				player.fallDistance = 2F;
			}
			if(playerSp.onGround)
				timesJumped = 0;
			else {
				if(playerSp.movementInput.jump) {
					if(!jumpDown && timesJumped < getMaxAllowedJumps()) {
						playerSp.jump();
						PacketHandler.sendToServer(new PacketJump());
						timesJumped++;
					}
					jumpDown = true;
				} else jumpDown = false;
			}
		}
	}
	public int getMaxAllowedJumps() {
		return Math.max(ModConfig.TRINKET_CONF.MAX_ALLOWED_JUMPS, 1);//In case of negative value
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(I18n.format("item.air_chocolate.desc"));
	}
}
