package me.mylogo.servercom.packets.question;

import com.google.gson.JsonObject;
import me.mylogo.servercom.packets.SimplePacket;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public class AnswerPacket extends SimplePacket {

    public static final int UID = 3;

    private JsonObject answer;

    public AnswerPacket() {
        super(UID);
        this.answer = new JsonObject();
    }

    public  JsonObject getAnswer() {
        return answer;
    }

    public void setAnswer(JsonObject json) {
        this.answer = json;
    }

}
