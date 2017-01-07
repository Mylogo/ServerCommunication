package me.mylogo.servercom.handler;

import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.IPacketHandler;
import me.mylogo.servercom.interfaces.ITransmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class PacketHandlerCollection {

    private final ITransmitter transmitter;
    private final List<IPacketHandler> handlers;

    /**
     * You should normally NOT use this constructor. This is
     * only adviced if you'd like to create your own
     * standard {@link IPacketHandler}s.
     * @param includeStandard
     */
    public PacketHandlerCollection(ITransmitter transmitter, boolean includeStandard) {
        this.transmitter = transmitter;
        handlers = new CopyOnWriteArrayList<>();
        if (includeStandard) {
            addPacketHandler(new EchoHandler());
            addPacketHandler(new MultiPacketHandler(this.transmitter));
        }
    }

    public PacketHandlerCollection(ITransmitter transmitter) {
        this(transmitter, true);
    }

    public void addPacketHandler(IPacketHandler handler) {
        handlers.add(handler);
    }

    public void handle(IPacket packet) {
        List<IPacketHandler> specificHandlers = getHandlers(packet);
        if (specificHandlers.size() == 0) {
            System.out.println("Received packet but did not handle it!");
            System.out.println("PacketType:" + packet.getClass().getName() + " Packet UID:" + packet.getUid());
            System.out.println("Packet Json:" + packet.getJson().toString());
        } else {
            specificHandlers.forEach(handler -> handler.handle(packet));
        }
    }

    public List<IPacketHandler> getHandlers(IPacket packet) {
        List<IPacketHandler> specificHandlers = new ArrayList<>();
//        handlers.stream().filter(handler -> handler.handles(packet)).forEach(specificHandlers::add);
        for (IPacketHandler handler : handlers) {
            if (handler.handles(packet))
                specificHandlers.add(handler);
        }
        return specificHandlers;
    }

}
