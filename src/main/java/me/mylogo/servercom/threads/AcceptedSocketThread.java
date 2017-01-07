package me.mylogo.servercom.threads;

import java.net.Socket;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public class AcceptedSocketThread extends SocketThread {

    private final String NAME;
    private final AcceptedSocketThreadCallback CALLBACK;

    public AcceptedSocketThread(Socket socket, AcceptedSocketThreadCallback callback, String name) {
        super(socket);
        this.NAME = name;
        this.CALLBACK = callback;
    }

    public String getSocketName() {
        return NAME;
    }

    @Override
    protected void onMessageReceive(String line) {
        this.CALLBACK.onAcceptedSocketMessageReceive(this, line);
    }

    public interface AcceptedSocketThreadCallback {
        void onAcceptedSocketMessageReceive(AcceptedSocketThread thread, String message);
    }

}
