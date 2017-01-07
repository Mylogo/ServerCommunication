package me.mylogo.servercom.sendpreproceeder;

import me.mylogo.servercom.handler.SpecificPacketHandler;
import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.packets.question.QuestionPacket;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public class QuestionSendPreproceeder extends SpecificSendPreproceeder<QuestionPacket> {

    public QuestionSendPreproceeder() {
        super(QuestionPacket.class);
    }

    @Override
    public void preproceedPacket(QuestionPacket packet) {

    }

}
