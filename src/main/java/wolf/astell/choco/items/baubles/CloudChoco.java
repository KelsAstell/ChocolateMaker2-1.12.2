
package wolf.astell.choco.items.baubles;

import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class CloudChoco extends Item implements IBauble {

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		IBauble.super.onWornTick(stack, player);

		clientWornTick(stack, player);
	}

	public void clientWornTick(ItemStack stack, EntityLivingBase player) {
	}

	public int getMaxAllowedJumps() {
		return 4;
	}

}
