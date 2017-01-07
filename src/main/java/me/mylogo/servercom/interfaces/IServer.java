package me.mylogo.servercom.interfaces;

import com.google.gson.JsonObject;

import java.util.UUID;

/**
 * Created by dennisheckmann on 05.01.17.
 */
public interface IServer extends ITransmitter {

    void sendPacket(IPacket packet, String clientName);

}
