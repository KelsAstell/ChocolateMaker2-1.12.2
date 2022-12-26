
package wolf.astell.choco.network;

import baubles.api.BaublesApi;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.items.IItemHandler;
import wolf.astell.choco.itemtool.AirChocolate;

public class PacketJump implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	public static class Handler implements IMessageHandler<PacketJump, IMessage> {

		@Override
		public IMessage onMessage(PacketJump message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;
			player.mcServer.addScheduledTask(() -> {
				IItemHandler baublesInv = BaublesApi.getBaublesHandler(player);
				ItemStack bodyStack = baublesInv.getStackInSlot(5);

				if(!bodyStack.isEmpty() && bodyStack.getItem() instanceof AirChocolate) {
					player.addExhaustion(0.3F);
					player.fallDistance = 0;
				}
			});
			return null;
		}
	}

}
