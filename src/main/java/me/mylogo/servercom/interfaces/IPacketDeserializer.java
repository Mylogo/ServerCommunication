package me.mylogo.servercom.interfaces;

import com.google.gson.JsonObject;
import me.mylogo.servercom.interfaces.IPacket;

/**
 * Created by dennisheckmann on 05.01.17.
 */
public interface IPacketDeserializer {

    /**
     *
     * @param json
     * @return
     */
    IPacket deserialize(JsonObject json);
    boolean deserializes(int uid);

}
