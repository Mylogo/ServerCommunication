package me.mylogo.servercom.interfaces;

/**
 * Created by dennisheckmann on 07.01.17.
 */
public interface ISendPreproceeder {

    boolean preproceeds(IPacket packet);
    void preproceed(IPacket packet);

}
