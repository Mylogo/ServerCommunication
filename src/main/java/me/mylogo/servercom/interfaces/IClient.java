package me.mylogo.servercom.interfaces;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Created by dennisheckmann on 05.01.17.
 */
public interface IClient extends ITransmitter {

    void sendPacket(IPacket pack);
    OutputStream getOutput();
    PrintWriter getPrintOutput();
    String getName();

}
