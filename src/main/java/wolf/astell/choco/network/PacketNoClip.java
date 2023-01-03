package wolf.astell.choco.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wolf.astell.choco.api.AvaritiaUtils;

public class PacketNoClip implements IMessage, IMessageHandler<PacketNoClip, IMessage> {

    @Override
    public void fromBytes(ByteBuf buf) { }

    @Override
    public void toBytes(ByteBuf buf) { }

    @Override
    public IMessage onMessage(PacketNoClip message, MessageContext context) {
        EntityPlayerMP playerMP = context.getServerHandler().player;
        AvaritiaUtils.toggleNoClip(playerMP);
        return null;
    }

}