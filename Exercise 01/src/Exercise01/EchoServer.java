package Exercise01;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer {

    public static void main(String[] args) throws Exception
    {
        try (ServerSocket serverSocket = new ServerSocket(22222))
        {
            while (true)
            {
                try (Socket socket = serverSocket.accept())
                {
                    System.out.println(serverSocket.getInetAddress().getHostAddress());
                    String address = socket.getInetAddress().getHostAddress();
                    System.out.printf("Client connected: %s%n", address);
                    OutputStream os = socket.getOutputStream();
                    PrintStream out = new PrintStream(os, true, "UTF-8");
                    out.printf("Hi %s, thanks for connecting!\nIf you would like to disconnect just type \"EXIT\"."
                            + "\nServer>", address);
                }
            }
        }
    }
}
