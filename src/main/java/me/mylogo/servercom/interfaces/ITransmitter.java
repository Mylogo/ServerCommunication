package me.mylogo.servercom.interfaces;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.mylogo.servercom.deserializers.PacketDeserializerCollection;
import me.mylogo.servercom.sendpreproceeder.SendPreproceederCollection;

import java.util.function.Consumer;

/**
 * Created by dennisheckmann on 05.01.17.
 */
public interface ITransmitter {

    Gson gson = new Gson();

    void onReceivePacket(IPacket pack);
    IPacket deserialize(JsonObject json);
    PacketDeserializerCollection getDeserializer();
    SendPreproceederCollection getSendPreproceeder();

    /**
     * This method is being fired every time a string
     * is received from a socket.
     * @param str the received string
     */
    default void onReceive(String str) {
        System.out.println("Received:" + str);
        try {
            JsonObject json = gson.fromJson(str, JsonObject.class);
            IPacket packet = deserialize(json);
            if (packet == null) {
                System.out.println("Packet could not be deserialized.");
                onDeserializeFailure(str);
            } else {
                onReceivePacket(packet);
            }
        } catch (Exception e) {
            onDeserializeFailure(str);
            System.out.println("Receiving failed. Maybe wanted, maybe not. Will print stacktrace.");
            e.printStackTrace();
        }
    }

    /**
     * This method is being fired when {@link #onReceive(String)} could not produce JsonObject or
     * {@link #deserialize(JsonObject)} did
     * return null. It's therefore a fallback method and
     * could be used for logging
     * @param str The
     */
    void onDeserializeFailure(String str);



}
