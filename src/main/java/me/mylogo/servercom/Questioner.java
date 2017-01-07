package me.mylogo.servercom;

import me.mylogo.servercom.interfaces.IPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class Questioner {

    private List<Question> questions;
    private final Object LOCK = new Object();

    public Questioner() {
        questions = new ArrayList<>();
    }

    /**
     *
     * @param packet
     * @return id for {@link me.mylogo.servercom.packets.question.QuestionPacket#ID_KEY}
     */
    public int askQuestion(IPacket packet, Consumer<IPacket> answer) {
        int randomNum;
        do {
            randomNum = (int) (Math.random() * Double.MAX_VALUE);
        } while (getQuestion(randomNum) != null);
        Question q = new Question(randomNum, answer);
        addQuestion(q);
        return randomNum;
    }

    private void addQuestion(Question question) {
        synchronized (LOCK) {
            questions.add(question);
        }
    }

    private void removeQuestion(int id) {
        synchronized (LOCK) {
            int size = questions.size();
            for (int i = 0; i < size; i++) {
                if (questions.get(i).getQuestionId() == id) {
                    questions.remove(i);
                    break;
                }
            }
        }
    }

    private Question getQuestion(int id) {
        synchronized (LOCK) {
            for (Question q : questions) {
                if (q.getQuestionId() == id)
                    return q;
            }
        }
        return null;
    }

    private class Question {
        private final int questionId;
        private final long TIME_SENT;
        private final Consumer<IPacket> CALLBACK;

        private Question(int id, Consumer<IPacket> callback) {
            this.questionId = id;
            TIME_SENT = System.currentTimeMillis();
            this.CALLBACK = callback;
        }
        private int getQuestionId() {
            return questionId;
        }
    }

}
