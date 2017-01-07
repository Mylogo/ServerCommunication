package me.mylogo.servercom.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.ITransmitter;
import me.mylogo.servercom.packets.MultiPacket;

import java.util.List;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public class MultiPacketHandler extends SpecificPacketHandler<MultiPacket> {

    private final ITransmitter TRANSMITTER;

    public MultiPacketHandler(ITransmitter transmitter) {
        super(MultiPacket.class);
        this.TRANSMITTER = transmitter;
    }

    @Override
    protected void handlePacket(MultiPacket packet) {
        JsonArray data = packet.getPacketsData();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            JsonElement elem = data.get(i);
            if (elem.isJsonObject()) {
                JsonObject json = (JsonObject) elem;
                try {
                    IPacket deserialized = TRANSMITTER.deserialize(json);
                    if (deserialized != null) {
                        TRANSMITTER.onReceivePacket(deserialized);
                    }
                } catch (Exception e) {
                    System.out.println("Something went wrong while deserializing Packet number:" + i);
                    e.printStackTrace();
                }
            }
        }
    }

}
