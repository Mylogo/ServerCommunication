package me.mylogo.servercom.handler;

import me.mylogo.servercom.packets.EchoPacket;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class EchoHandler extends SpecificPacketHandler<EchoPacket> {

    public EchoHandler() {
        super(EchoPacket.class);
    }

    @Override
    protected void handlePacket(EchoPacket packet) {
        System.out.println(packet.getEcho());
    }

}
