package Project01;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class ListenerThread extends Thread
{
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
            // It only executes when the server has sent a message.
            while((receivedMessage = in.readLine()) != null)
            {
                final String message = receivedMessage;

                // Update the Label on the JavaFx Application Thread
                Platform.runLater(() ->
                        ChatClient.controller.setMessages(message));
            }
            socket.close();
        }
        catch (IOException e)
        {
            System.err.println("ERROR: Connection lost with server.");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
