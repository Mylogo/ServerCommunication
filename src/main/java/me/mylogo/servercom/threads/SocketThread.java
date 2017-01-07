package me.mylogo.servercom.threads;

import java.io.*;
import java.net.Socket;

/**
 * Created by dennisheckmann on 06.01.17.
 */
public abstract class SocketThread extends Thread {

    private Socket socket;
    private boolean running;
    private BufferedReader reader;
    private PrintWriter writer;

    public SocketThread(Socket socket) {
        this.socket = socket;
        running = true;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public Socket getSocket() {
        return socket;
    }

    public void run() {
        String line;
        while (isRunning() && !getSocket().isClosed()) {
//            System.out.println("running " + Thread.currentThread().getId());
            try {
                if ((line = reader.readLine()) != null) {
//                    System.out.println("Line:" + line);
                    onMessageReceive(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                //Somehow spamming is being avoided ^_^
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Closing socket connection.");
        try {
            closeMe();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeMe() throws IOException {
        reader.close();
        socket.close();
    }

    protected abstract void onMessageReceive(String line);

    public void sendString(String string) {
        writer.println(string);
    }
}
