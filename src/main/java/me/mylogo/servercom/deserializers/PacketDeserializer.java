package me.mylogo.servercom.deserializers;

import com.google.gson.JsonObject;
import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.IPacketDeserializer;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public abstract class PacketDeserializer implements IPacketDeserializer {

    @Override
    public IPacket deserialize(JsonObject json) {
        int uid = json.get(IPacket.UID_KEY).getAsInt();
        return deserialize(uid, json);
    }

    protected abstract IPacket deserialize(int uid, JsonObject json);

}
