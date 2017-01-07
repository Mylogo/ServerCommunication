package me.mylogo.servercom.packets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.mylogo.servercom.deserializers.InvalidPacketException;
import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.ITransmitter;

/**
 * Created by dennisheckmann on 05.01.17.
 */
public class SimplePacket implements IPacket {

    private JsonObject json;
    private int uid;

    public SimplePacket(int uid) {
        this.json = new JsonObject();
        this.uid = uid;
        json.addProperty(UID_KEY, getUid());
    }

    public final int getUid() {
        return this.uid;
    }

    public JsonObject getJson() {
        return json;
    }

    public JsonObject getFinalJson() {
        putData();
        return getJson();
    }

    protected void putData() {}

    protected void add(String key, String str) {
        getJson().addProperty(key, str);
    }

    protected void add(String key, Number num) {
        getJson().addProperty(key, num);
    }

    public static String retrieveString(JsonObject json, String key) throws InvalidPacketException {
        try {
            return json.get(key).getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidPacketException(json);
        }
    }

    public static int retrieveInt(JsonObject json, String key) throws InvalidPacketException {
        try {
            return json.get(key).getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidPacketException(json);
        }
    }

    public static float retrieveFloat(JsonObject json, String key) throws InvalidPacketException {
        try {
            return json.get(key).getAsFloat();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidPacketException(json);
        }
    }

    public static double retrieveDouble(JsonObject json, String key) throws InvalidPacketException {
        try {
            return json.get(key).getAsDouble();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidPacketException(json);
        }
    }

    public static long retrieveLong(JsonObject json, String key) throws InvalidPacketException {
        try {
            return json.get(key).getAsLong();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidPacketException(json);
        }
    }

    public static JsonObject retrieveJsonObject(JsonObject json, String key) throws InvalidPacketException {
        try {
            return json.get(key).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidPacketException(json);
        }
    }

    public static JsonArray retrieveJsonArray(JsonObject json, String key) throws InvalidPacketException {
        try {
            return json.get(key).getAsJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidPacketException(json);
        }
    }

//    public static <T> T retrieve(JsonObject json, String key, Class<T> clazz) throws InvalidPacketException {
//        try {
//            return clazz.cast(json.get(key));
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new InvalidPacketException(json);
//        }
//    }

}
