package me.mylogo.servercom.deserializers;

import com.google.gson.JsonObject;
import me.mylogo.servercom.packets.SimplePacket;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class Retriever {

    private final JsonObject json;

    public Retriever(JsonObject json) {
        this.json = json;
    }

//    public <T> T retrieve(String key, Class<T> clazz) throws InvalidPacketException {
//        return SimplePacket.retrieve(this.json, key, clazz);
//    }

}
