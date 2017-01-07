package me.mylogo.servercom.deserializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.IPacketDeserializer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class PacketDeserializerCollection {

    private final List<IPacketDeserializer> deserializers;

    public PacketDeserializerCollection() {
        deserializers = new CopyOnWriteArrayList<>();
    }

    public void addDeserializer(IPacketDeserializer deserializer) {
        deserializers.add(deserializer);
    }

    public IPacket deserializeByDeserializers(JsonObject json) {
        JsonElement element = json.get(IPacket.UID_KEY);
        if (element != null && element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                int uid = primitive.getAsInt();
                for (IPacketDeserializer deserializer : deserializers) {
//                    System.out.println("Check:" + deserializer.getClass().getName() +  " can: " + uid);
                    if (deserializer.deserializes(uid)) {
                        return deserializer.deserialize(json);
                    }
                }
            }
        }
        return null;
    }
}
