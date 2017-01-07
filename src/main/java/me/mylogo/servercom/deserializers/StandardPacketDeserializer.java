package me.mylogo.servercom.deserializers;

import com.google.gson.JsonObject;
import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.IPacketDeserializer;
import me.mylogo.servercom.packets.EchoPacket;
import me.mylogo.servercom.packets.HelloPacket;
import me.mylogo.servercom.packets.MultiPacket;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class StandardPacketDeserializer extends PacketDeserializer {

    public static final StandardPacketDeserializer STANDARD_PACKET_DESERIALIZER = new StandardPacketDeserializer();

    @Override
    protected IPacket deserialize(int uid, JsonObject json) {
        try {
            switch (uid) {
                case HelloPacket.UID:
                    return HelloPacket.fromJson(json);
                case EchoPacket.UID:
                    return EchoPacket.fromJson(json);
                case MultiPacket.UID:
                    return MultiPacket.fromJson(json);
                default:
                    return null;
            }
        } catch (InvalidPacketException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deserializes(int uid) {
        return uid == HelloPacket.UID || uid == EchoPacket.UID || uid == MultiPacket.UID;
    }
}
