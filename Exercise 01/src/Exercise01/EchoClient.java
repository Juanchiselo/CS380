package Exercise01;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public final class EchoClient
{

    public static void main(String[] args) throws Exception
    {


        try (Socket socket = new Socket("localhost", 22222))
        {
            //String message;
            //Scanner scanner = new Scanner(System.in);
            OutputStream outputStream = socket.getOutputStream();
            PrintStream out = new PrintStream(outputStream, false, "UTF-8");
            out.printf("Hello from ");

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            System.out.println(br.readLine());


            //System.out.println("Connected to: " + socket.getRemoteSocketAddress());



//            do
//            {
//                System.out.print("Client> ");
//                message = scanner.nextLine();
//
//
//
//
//
//
//            }while(!message.toUpperCase().equals("EXIT"));

            //socket.notify();
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }
}















