package me.mylogo.servercom.sendpreproceeder;

import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.ISendPreproceeder;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public abstract class SpecificSendPreproceeder<PacketClass extends IPacket> implements ISendPreproceeder {

    private Class<PacketClass> toPreproceed;

    public SpecificSendPreproceeder(Class<PacketClass> toPreproceed) {
        this.toPreproceed = toPreproceed;
    }

    @Override
    public boolean preproceeds(IPacket packet) {
        return toPreproceed.isInstance(packet);
    }

    @Override
    public void preproceed(IPacket packet) {
        preproceedPacket(toPreproceed.cast(packet));
    }

    public abstract void preproceedPacket(PacketClass packet);

}
