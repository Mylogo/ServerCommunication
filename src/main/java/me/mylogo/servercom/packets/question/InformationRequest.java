package me.mylogo.servercom.packets.question;

import com.google.gson.JsonObject;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public class InformationRequest {

    public static final String JSON_KEY = "reqid";

    private JsonObject json;
    private final int REQUEST_ID;

    public InformationRequest(JsonObject json) {
        this.REQUEST_ID = json.get(JSON_KEY).getAsInt();
        this.json = json;
    }

    public InformationRequest(int requestId) {
        json = new JsonObject();
        this.REQUEST_ID = requestId;
        json.addProperty(JSON_KEY, REQUEST_ID);
    }

    public JsonObject getJson() {
        return json;
    }

    public void addParameter(String key, String value) {
        getJson().addProperty(key, value);
    }

    public void addParameter(String key, int value) {
        getJson().addProperty(key, value);
    }

}
