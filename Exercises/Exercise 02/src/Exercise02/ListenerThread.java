package Exercise02;

import javafx.application.Platform;
import java.io.*;
import java.net.Socket;

public class ListenerThread extends Thread
{
    public volatile static boolean endThread = false;
    private Socket socket = null;

    public ListenerThread(Socket socket)
    {
        super("Chat Listener Thread");
        this.socket = socket;
    }

    /**
     * The overridden run() function belonging to the Thread class.
     * This is what handles the communication between the server and the client.
     */
    public void run()
    {
        try
        {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[100];

            // Objects needed for receiving and reading the server's messages.
            int firstFourBits;
            int nextFourBits;
            int constructedByte;

            InputStream inputStream = socket.getInputStream();
            //InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            //BufferedReader in = new BufferedReader(inputStreamReader);

            int counter = 1;

            // The main loop of execution.
            // This executes when the servers sends a message
            // and the thread has not received a flag to terminate.
            while((firstFourBits = inputStream.read()) != -1
                    && (nextFourBits = inputStream.read()) != -1)
            {
                constructedByte = ((firstFourBits << 4) | (nextFourBits & 0xFF));

                //buffer.write(data, 0, receivedData);
                System.out.println(Integer.toHexString(firstFourBits) + "  " + Integer.toHexString(nextFourBits));
                System.out.println("#" + counter++ + " Message received: " + Integer.toHexString(constructedByte));
            }
            buffer.flush();
            Ex2Client.disconnectFromServer();
        }
        catch (IOException e)
        {
            System.err.println("ERROR: Connection lost with server.");
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}
