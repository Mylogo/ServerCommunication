package me.mylogo.servercom.packets;

import com.google.gson.JsonObject;
import me.mylogo.servercom.deserializers.InvalidPacketException;
import me.mylogo.servercom.interfaces.IPacket;

/**
 * Created by dennisheckmann on 06.01.17.
 *
 * This packet may be used to test a connection.
 * The corresponding handler from {@link }
 */
public class EchoPacket extends SimplePacket {

    public static final int UID = 1;
    private final String TO_ECHO;

    public EchoPacket(String toEcho) {
        super(UID);
        this.TO_ECHO = toEcho;
    }

    public String getEcho() {
        return this.TO_ECHO;
    }

    @Override
    protected void putData() {
        getJson().addProperty("echo", this.TO_ECHO);
    }

    public static EchoPacket fromJson(JsonObject json) throws InvalidPacketException {
        String toEcho = retrieveString(json, "echo");
        return new EchoPacket(toEcho);
    }

}
