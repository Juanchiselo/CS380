package Project01;

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
            // Objects needed for receiving and reading the server's messages.
            String receivedMessage;
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader in = new BufferedReader(inputStreamReader);

            // The main loop of execution.
            // This executes when the servers sends a message
            // and the thread has not received a flag to terminate.
            while((receivedMessage = in.readLine()) != null
                    && !endThread)
            {
                // NOTE: The variable sent to the
                // Application GUI thread has to be final.
                final String message = receivedMessage;

                // Displays the received messages.
                Platform.runLater(() ->
                        ChatClient.controller.setMessages(message));

                // Catches the Unavailable Username error thrown by the server.
                if(message.equals("Name in use."))
                {
                    Platform.runLater(() ->
                            ChatClient.controller.displayError("Unavailable Username",
                                    "The username you entered is already being used."));
                    endThread = true;
                }

                // Catches the Inactivity error thrown by the server.
                if(message.equals("Connection idle for 1 minute, closing connection."))
                {
                    Thread.sleep(5000);
                    Platform.runLater(() ->
                            ChatClient.controller.displayError("Connection Timed Out",
                                    "You were idle for 1 minute "
                                            + "so the server closed your connection."));
                    endThread = true;
                }
            }
            ChatClient.disconnectFromServer();
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
