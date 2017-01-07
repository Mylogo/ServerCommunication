package me.mylogo.servercom.packets.question;

import com.google.gson.JsonObject;
import me.mylogo.servercom.deserializers.InvalidPacketException;
import me.mylogo.servercom.packets.SimplePacket;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public class QuestionPacket extends SimplePacket {

    public static final String ID_KEY = "pid";
    public static final int UID = 2;

    private final InformationRequest request;

    public QuestionPacket(InformationRequest request) {
        super(UID);
        this.request = request;
    }

    @Override
    protected void putData() {
        getJson().add("req", request.getJson());
    }

    public static QuestionPacket fromJson(JsonObject json) throws InvalidPacketException {
        JsonObject requestData = retrieveJsonObject(json, "req");
        InformationRequest request = new InformationRequest(requestData);
        return new QuestionPacket(request);
    }

}
