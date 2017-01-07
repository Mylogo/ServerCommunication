package me.mylogo.servercom;

import com.google.gson.JsonObject;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import me.mylogo.servercom.deserializers.PacketDeserializerCollection;
import me.mylogo.servercom.handler.PacketHandlerCollection;
import me.mylogo.servercom.interfaces.IClient;
import me.mylogo.servercom.interfaces.IPacket;
import me.mylogo.servercom.interfaces.IPacketDeserializer;
import me.mylogo.servercom.interfaces.IPacketHandler;
import me.mylogo.servercom.packets.HelloPacket;
import me.mylogo.servercom.sendpreproceeder.SendPreproceederCollection;
import me.mylogo.servercom.threads.ClientSocketThread;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dennisheckmann on 05.01.17.
 */
public class SimpleClient implements IClient {

    private String name;
    private final Socket socket;
    private final OutputStream out;
    private final PrintWriter printOut;
    private final PacketDeserializerCollection deserializer;
    private final PacketHandlerCollection handler;
    private final SendPreproceederCollection sendPreproceeder;
    private final ClientSocketThread thread;

    public SimpleClient(String host, int port, @Nullable String name) throws IOException {
        deserializer = new PacketDeserializerCollection();
        handler = new PacketHandlerCollection(this);
        this.sendPreproceeder = new SendPreproceederCollection();
        this.socket = new Socket(host, port);
        this.out = this.socket.getOutputStream();
        this.printOut = new PrintWriter(this.out, true);
        this.name = name == null ? UUID.randomUUID().toString() : name;
        sendPacket(new HelloPacket(this.name));
        this.thread = new ClientSocketThread(socket, this);
        thread.start();
    }

    public void addDeserializer(IPacketDeserializer deserializer) {
        this.deserializer.addDeserializer(deserializer);
    }

    public void addPacketHandler(IPacketHandler handler) {
        this.handler.addPacketHandler(handler);
    }

    private IPacket deserializeByDeserializers(JsonObject json) {
        return deserializer.deserializeByDeserializers(json);
    }

    public OutputStream getOutput() {
        return out;
    }

    public PrintWriter getPrintOutput() {
        return printOut;
    }

    public void sendPacket(@NotNull IPacket pack) {
        try {
            sendPreproceeder.preproceed(pack);
        } catch (Exception e) {
            System.out.println("Something went wrong while preproceeding the packet.");
            e.printStackTrace();
        }
        if (pack != null) {
            String jsonString = pack.getFinalJson().toString();
            sendString(jsonString);
        }
    }

    public void sendString(String s) {
        //no synchronization needed as println is synchronized 'yey'
        printOut.println(s);
    }

    public String getName() {
        return this.name;
    }

    public Socket getSocket() {
        return socket;
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
        System.out.println(str);
    }
}
