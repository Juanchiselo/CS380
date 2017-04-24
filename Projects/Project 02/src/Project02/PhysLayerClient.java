package Project02;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PhysLayerClient
{
    private static Socket socket;

    public static void main(String[] args)
    {
        connectToServer();
    }

    /**
     * Connects the client to the server and
     * creates a Listener thread.
     */
    public static void connectToServer()
    {
        String hostName = "codebank.xyz";
        int portNumber = 38002;

        try
        {
            socket = new Socket(hostName, portNumber);
            new ListenerThread(socket).start();
            System.out.println("Connected to server.");
        }
        catch (UnknownHostException e)
        {
            System.err.println("ERROR: Unknown host " + hostName + ".");
        }
        catch (Exception e)
        {
            System.err.println("ERROR: Could not connect to " + hostName + ".");
        }
    }

    /**
     * Disconnects the client from the server.
     */
    public static void disconnectFromServer()
    {
        try
        {
            socket.close();
            System.out.println("Disconnected from server.");
        }
        catch (IOException e)
        {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}