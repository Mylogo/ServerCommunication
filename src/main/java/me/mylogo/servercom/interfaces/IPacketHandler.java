package me.mylogo.servercom.interfaces;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public interface IPacketHandler {

    boolean handles(IPacket packet);
    void handle(IPacket packet);

}
