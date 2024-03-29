package client;

import general.Request;
import general.ConnectorHelper;
import general.Response;
import general.commands.ExecuteScriptCommand;
import general.commands.InsertCommand;
import general.element.UserProfile;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Properties;

/**
 * <p>ClientConnector implements (3) step in ClientManager</p>
 * <p>There are some methods to send and receive datagrams</p>
 */
public class ClientConnectorImpl implements ClientConnectorModule {
    private static final ClientConnectorImpl instance = new ClientConnectorImpl();
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;
    private int socketSoTimeout;
    private final ByteBuffer dataBuffer = ByteBuffer.allocate(15_000);

    private ClientConnectorImpl() {}

    public static ClientConnectorImpl getInstance() {
        return instance;
    }

    @Override
    public void initialize() throws IOException, ClassNotFoundException {
        setConnection();
        checkConnection();
        ClientModuleHolder.getInstance().getClientControllerModule().addBaseText(
                "Connecting to server \"" + serverAddress + "\"",
                "Connection to server was successful"
        );
    }

    private void setConnection() throws SocketException {
        socket = new DatagramSocket();
        socket.setSoTimeout(socketSoTimeout);
    }

    private void checkConnection() throws IOException, ClassNotFoundException {
        try {
            socket.setSoTimeout(socket.getSoTimeout() - 2_000);
            Response response = sendToServer(
                    RequestBuilder.createNewRequest().setRequestType(Request.RequestType.CHECK_CONNECTION).build()
            );
            socket.setSoTimeout(socket.getSoTimeout() + 2_000);
            if (response.getResponseType() != Response.ResponseType.CONNECTION_SUCCESSFUL) {
                throw new IOException("Server has wrong logic: expected \"" +
                        Response.ResponseType.CONNECTION_SUCCESSFUL +
                        "\", but not \"" +
                        response.getResponseType() +
                        "\"");
            }
        } catch (SocketTimeoutException e) {
            throw new SocketTimeoutException("Server is not responding, try later or choose another server :(");
        }
    }

    @Override
    public void setProperties(Properties properties) throws UnknownHostException, NumberFormatException {
        serverAddress = InetAddress.getByName(properties.getProperty("serverAddress", "localhost"));
        try {
            serverPort = Integer.parseInt(properties.getProperty("serverPort", "52927"));
            if (serverPort < 0 || serverPort > 65535) {
                throw new NumberFormatException("property \"serverPort\" must be in range 1-65536");
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Can't parse property \"serverPort\": " + e.getMessage());
        }
        try {
            socketSoTimeout = Integer.parseInt(properties.getProperty("socketSoTimeout", "5000"));
            if (socketSoTimeout < 3_000 || socketSoTimeout > 60_000) {
                throw new NumberFormatException("property \"socketSoTimeout\" must be in range 3k-60k");
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Can't parse property \"socketSoTimeout\": " + e.getMessage());
        }
    }

    @Override
    public synchronized Response sendToServer(Request request) throws IOException, ClassNotFoundException {
        try {
            sendRequest(request);
        } catch (IOException e) {
            throw new IOException("Can't send request to server: " + e.getMessage(), e);
        }
        try {
            return acceptResponse();
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (IOException e) {
            throw new IOException("Can't receive response from server: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Can't find Response class in response from server: " + e.getMessage(), e);
        }
    }

    @Override
    public synchronized void sendRequest(Request request) throws IOException {
        sendPacket(ConnectorHelper.objectToBuffer(request));
    }

    private void sendPacket(byte[] buf) throws IOException {
        DatagramPacket packetWithData = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
        socket.send(packetWithData);
    }

    private Response acceptResponse() throws IOException, ClassNotFoundException {
        receivePacket(dataBuffer.array());
        Response response = ConnectorHelper.objectFromBuffer(dataBuffer.array());
        dataBuffer.clear();
        return response;
    }

    private void receivePacket(byte[] buf) throws IOException {
        DatagramPacket packetWithData = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packetWithData);
        } catch (SocketTimeoutException e) {
            throw new SocketTimeoutException("Server is not responding, try later or choose another server :(");
        }
    }
}
