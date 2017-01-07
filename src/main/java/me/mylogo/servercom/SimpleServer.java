package me.mylogo.servercom;

import com.google.gson.JsonObject;
import com.sun.istack.internal.NotNull;
import me.mylogo.servercom.deserializers.PacketDeserializerCollection;
import me.mylogo.servercom.deserializers.StandardPacketDeserializer;
import me.mylogo.servercom.handler.EchoHandler;
import me.mylogo.servercom.handler.PacketHandlerCollection;
import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.IPacketDeserializer;
import me.mylogo.servercom.interfaces.IPacketHandler;
import me.mylogo.servercom.interfaces.IServer;
import me.mylogo.servercom.sendpreproceeder.SendPreproceederCollection;
import me.mylogo.servercom.threads.AcceptedSocketThread;
import me.mylogo.servercom.threads.PrematureSocketThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by dennisheckmann on 05.01.17.
 */
public class SimpleServer implements IServer, AcceptedSocketThread.AcceptedSocketThreadCallback {

    public static final String SERVER_NAME = "$S";

    private final ServerSocket socket;
    private final List<PrematureSocketThread> prematureSockets;
    private final List<AcceptedSocketThread> acceptedThreads;
    private final PacketDeserializerCollection deserializer;
    private final PacketHandlerCollection handler;
    private final SendPreproceederCollection sendPreproceeder;
    private boolean running;

    public SimpleServer(int port) throws IOException {
        deserializer = new PacketDeserializerCollection();
        addDeserializer(StandardPacketDeserializer.STANDARD_PACKET_DESERIALIZER);
        handler = new PacketHandlerCollection(this);
        this.sendPreproceeder = new SendPreproceederCollection();
        prematureSockets = new ArrayList<>();
        acceptedThreads = new ArrayList<>();
        this.socket = new ServerSocket(port);
        running = true;
        new Thread(() -> {
            while (running) {
                try {
                    System.out.println("Accepting socket");
                    Socket s = socket.accept();
                    addPrematureSocket(s);
                    System.out.println(prematureSockets.size());
                } catch (IOException e) {
                    System.err.println("Failed to accept Socket.");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private IPacket deserializeByDeserializers(JsonObject json) {
//        System.out.println("Deserialize by deserializers " + json.toString());
        return deserializer.deserializeByDeserializers(json);
    }

    public void addDeserializer(IPacketDeserializer deserializer) {
        this.deserializer.addDeserializer(deserializer);
    }

    public void addAcceptedSocket(Socket socket, String name) {
//        System.out.println("Adding accepted");
        AcceptedSocketThread thread = new AcceptedSocketThread(socket, this, name);
        synchronized (acceptedThreads) {
            acceptedThreads.add(thread);
            thread.start();
        }
    }

    public AcceptedSocketThread getAcceptedSocket(String name) {
        synchronized (acceptedThreads) {
            AcceptedSocketThread th = acceptedThreads.stream().filter(thread -> thread.getName().equals(name)).findFirst().orElse(null);
            System.out.println(th);
            return th;
        }
    }

    private void addPrematureSocket(Socket s) {
        PrematureSocketThread thread = new PrematureSocketThread(s, this);
        synchronized (prematureSockets) {
            prematureSockets.add(thread);
        }
        thread.start();
    }

    public void removePrematureSocket(PrematureSocketThread thread) {
        synchronized (prematureSockets) {
            Iterator<PrematureSocketThread> iterator = prematureSockets.iterator();
            while (iterator.hasNext()) {
                PrematureSocketThread th = iterator.next();
                if (th == thread) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    private void removeAcceptedSocket(AcceptedSocketThread thread) {
        synchronized (acceptedThreads) {
            Iterator<AcceptedSocketThread> iterator = acceptedThreads.iterator();
            while (iterator.hasNext()) {
                AcceptedSocketThread th = iterator.next();
                if (th == thread) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public void sendPacket(@NotNull IPacket packet, @NotNull String clientName) {
        Objects.requireNonNull(packet);
        Objects.requireNonNull(clientName);
        try {
            sendPreproceeder.preproceed(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendString(packet.toString(), clientName);
    }

    public void sendString(@NotNull String string, @NotNull String clientName) {
        AcceptedSocketThread thread = getAcceptedSocket(clientName);
        if (thread != null) {
            thread.sendString(string);
        }
    }

    @Override
    public void onReceivePacket(IPacket pack) {
        handler.handle(pack);
    }

    @Override
    public IPacket deserialize(JsonObject json) {
        return deserializeByDeserializers(json);
    }

    @Override
    public PacketDeserializerCollection getDeserializer() {
        return deserializer;
    }

    @Override
    public SendPreproceederCollection getSendPreproceeder() {
        return sendPreproceeder;
    }

    @Override
    public void onDeserializeFailure(String str) {
        System.out.println("Failure: " + str);
    }

    @Override
    public void onAcceptedSocketMessageReceive(AcceptedSocketThread thread, String message) {
//        System.out.println("Received: " + message);
        if (message.isEmpty()) {
            return;
        }
        try {
//            System.out.println("Trying now");
            JsonObject json = gson.fromJson(message, JsonObject.class);
//            System.out.println("JsonObject:" + json);
            IPacket packet = deserialize(json);
//            System.out.println("Packet:" + packet);
            if (packet == null) {
                onDeserializeFailure("packet==null - " + message);
            } else {
//                System.out.println("onReceivePacket");
                onReceivePacket(packet);
            }
        } catch (Exception e) {
            onDeserializeFailure("Exception while retrieving JsonObject" + message);
            e.printStackTrace();
        }
    }

}
