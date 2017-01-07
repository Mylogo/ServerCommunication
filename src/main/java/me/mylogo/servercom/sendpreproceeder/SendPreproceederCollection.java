package me.mylogo.servercom.sendpreproceeder;

import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.ISendPreproceeder;
import me.mylogo.servercom.interfaces.ITransmitter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public class SendPreproceederCollection {

    private List<ISendPreproceeder> preproceeders;

    public  SendPreproceederCollection() {
        this.preproceeders = new CopyOnWriteArrayList<>();
    }

    public void addPreproceeders(ISendPreproceeder... preproceeders) {
        this.addPreproceeders(Arrays.asList(preproceeders));
    }

    public void addPreproceeders(List<ISendPreproceeder> preproceeders) {
        this.preproceeders.addAll(preproceeders);
    }

    public void preproceed(IPacket packet) {
        for (ISendPreproceeder preproceeder : preproceeders) {
            if (preproceeder.preproceeds(packet)) {
                preproceeder.preproceed(packet);
            }
        }
    }

}
