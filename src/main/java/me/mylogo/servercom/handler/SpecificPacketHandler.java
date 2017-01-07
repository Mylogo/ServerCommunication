package me.mylogo.servercom.handler;

import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.IPacketHandler;
import me.mylogo.servercom.packets.HelloPacket;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public abstract class SpecificPacketHandler<PacketClass extends IPacket> implements IPacketHandler {

    private final Class<PacketClass> toHandle;

    public SpecificPacketHandler(Class<PacketClass> toHandle) {
        this.toHandle = toHandle;
    }

    protected abstract void handlePacket (PacketClass packet);

    @Override
    public final boolean handles(IPacket packet) {
        return toHandle.isInstance(packet);
    }

    @Override
    public void handle(IPacket packet) {
        handlePacket(toHandle.cast(packet));
    }

    private static class Handler extends SpecificPacketHandler<HelloPacket> {

        public Handler() {
            super(HelloPacket.class);
        }

        @Override
        protected void handlePacket(HelloPacket packet) {

        }
    }

}
