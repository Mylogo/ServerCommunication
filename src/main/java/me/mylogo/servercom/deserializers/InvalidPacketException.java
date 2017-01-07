package me.mylogo.servercom.deserializers;

import com.google.gson.JsonObject;
import com.sun.jdi.InvocationException;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class InvalidPacketException extends Exception {

    private JsonObject json;

    public InvalidPacketException(JsonObject json) {
        this.json = json;
    }

    @Override
    public void printStackTrace() {
        System.err.println("An error occured while parsing a Packet from an JsonObject:");
        System.out.println(json.toString());
        super.printStackTrace();
    }
}
