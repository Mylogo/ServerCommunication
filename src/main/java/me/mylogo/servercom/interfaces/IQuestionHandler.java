package me.mylogo.servercom.interfaces;

import me.mylogo.servercom.packets.question.QuestionPacket;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public interface IQuestionHandler {

    void handleQuestion(QuestionPacket packet);

}
