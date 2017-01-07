package me.mylogo.servercom.threads;

import com.google.gson.JsonObject;
import com.sun.javafx.geom.AreaOp;
import me.mylogo.servercom.SimpleServer;
import me.mylogo.servercom.deserializers.StandardPacketDeserializer;
import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.ITransmitter;
import me.mylogo.servercom.packets.HelloPacket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class PrematureSocketThread extends SocketThread {

    private final long CONNECTED_TIME;
    private final SimpleServer SERVER;

    public PrematureSocketThread(Socket socket, SimpleServer server) {
        super(socket);
        this.CONNECTED_TIME = System.currentTimeMillis();
        this.SERVER = server;
    }

    @Override
    protected void onMessageReceive(String line) {
        try {
            System.out.println("Received");
            JsonObject json = ITransmitter.gson.fromJson(line, JsonObject.class);
            IPacket packet = StandardPacketDeserializer.STANDARD_PACKET_DESERIALIZER.deserialize(json);
            System.out.println("deserialized");
            if (packet instanceof HelloPacket) {
                System.out.println("It was instance");
                HelloPacket hello = (HelloPacket) packet;
                String name = hello.getName();
                AcceptedSocketThread accepted = SERVER.getAcceptedSocket(name);
                if (accepted == null && !name.equals(SimpleServer.SERVER_NAME)) {
                    SERVER.addAcceptedSocket(getSocket(), name);
                    removeMe();
                } else {
                    try {
                        new PrintWriter(getSocket().getOutputStream(), true).print("Name already in use. Will disconnect you.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        closeMe();
                        removeMe();
                    }
                }
            } else {
                System.out.println("it was not instance but " + packet);
                closeMe();
                removeMe();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                closeMe();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            removeMe();
        }
    }

    private void removeMe() {
        this.SERVER.removePrematureSocket(this);
    }

}