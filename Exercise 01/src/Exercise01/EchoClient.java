package Exercise01;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public final class EchoClient
{
    public static void main(String[] args) throws IOException
    {
        String hostName = "localhost";
        int portNumber = 22222;

        try (Socket socket = new Socket(hostName, portNumber))
        {
            // Objects needed for receiving and reading the server's messages.
            String message;
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader in = new BufferedReader(inputStreamReader);

            // Objects needed for sending messages to the server.
            Scanner scanner = new Scanner(System.in);
            OutputStream outputStream = socket.getOutputStream();
            PrintStream out = new PrintStream(outputStream, false, "UTF-8");

            // The main loop of execution.
            // It only executes when the server has sent a message.
            while((message = in.readLine()) != null)
            {
                System.out.println("Server> " + message);
                System.out.print("Client> ");
                message = scanner.nextLine();

                // Sends the message to the server.
                out.println(message);

                if(message.toUpperCase().equals("EXIT"))
                    break;
            }
            socket.close();
        }
        catch (UnknownHostException e)
        {
            System.err.println("ERROR: Unknown host " + hostName + ".");
        }
        catch (Exception e)
        {
            System.err.println("ERROR: Could not connect to " + hostName + ".");
        }
        finally
        {
            System.exit(1);
        }
    }
}















