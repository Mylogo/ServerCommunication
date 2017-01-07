package me.mylogo.servercom.interfaces;

import com.google.gson.JsonObject;

/**
 * Created by dennisheckmann on 05.01.17.
 */
public interface IPacket {

    String UID_KEY = "puid";

    int getUid();
    JsonObject getJson();
    JsonObject getFinalJson();

}
