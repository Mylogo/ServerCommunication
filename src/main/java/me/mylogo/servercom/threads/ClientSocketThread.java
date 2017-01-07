package me.mylogo.servercom.threads;

import me.mylogo.servercom.SimpleClient;

import java.net.Socket;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class ClientSocketThread extends SocketThread {

    private final SimpleClient CLIENT;

    public ClientSocketThread(Socket socket, SimpleClient client) {
        super(socket);
        this.CLIENT = client;
    }

    @Override
    protected void onMessageReceive(String line) {
        CLIENT.onReceive(line);
    }

}
