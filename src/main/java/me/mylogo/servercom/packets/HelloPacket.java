package me.mylogo.servercom.packets;

import com.google.gson.JsonObject;
import me.mylogo.servercom.deserializers.InvalidPacketException;
import me.mylogo.servercom.deserializers.StandardPacketDeserializer;

/**
 * Created by dennisheckmann on 05.01.17.
 */
public class HelloPacket extends SimplePacket {

    public static final int UID = 0;
    private String name;

    public HelloPacket(String name) {
        super(UID);
        this.name = name;
    }

    @Override
    protected void putData() {
        add("name", this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static HelloPacket fromJson(JsonObject json) throws InvalidPacketException {
        String name = retrieveString(json, "name");
        return new HelloPacket(name);
    }

}
