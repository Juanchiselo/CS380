import java.io.IOException;
import java.net.ServerSocket;

public class WebServer
{
    public static void main(String[] args)
    {
        int portNumber = 8080;

        try (ServerSocket serverSocket = new ServerSocket(portNumber))
        {
            while (true)
                new WebServerThread(serverSocket.accept()).start();
        }
        catch (IOException e)
        {
            System.err.println("ERROR: Could not listen on port " + portNumber + ".");
            System.exit(-1);
        }
    }
}
