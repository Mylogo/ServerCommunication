package me.mylogo.servercom.packets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.mylogo.servercom.deserializers.InvalidPacketException;
import me.mylogo.servercom.interfaces.IPacket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public class MultiPacket extends SimplePacket {

    public static final int UID = 4;
    private JsonArray packets;

    private MultiPacket(JsonArray data) {
        super(UID);
        this.packets = data;
    }

    private MultiPacket() {
        super(UID);
        packets = new JsonArray();
    }

    public MultiPacket(List<IPacket> packets) {
        this();
        addPackets(packets);
    }

    public MultiPacket(IPacket... packets) {
        this();
        addPackets(packets);
    }

    public MultiPacket addPackets(IPacket... packets) {
        return addPackets(Arrays.asList(packets));
    }

    public MultiPacket addPackets(List<IPacket> packets) {
        for (IPacket p : packets) {
            addPacketData(p.getFinalJson());
        }
        return this;
    }

    public void addPacketData(JsonObject finalJson) {
        packets.add(finalJson);
    }

    public JsonArray getPacketsData() {
        return packets;
    }

    @Override
    protected void putData() {
        getJson().add("packets", packets);
    }

    public static MultiPacket fromJson(JsonObject json) throws InvalidPacketException {
        JsonArray array = retrieveJsonArray(json, "packets");
        return new MultiPacket(array);
    }

}
