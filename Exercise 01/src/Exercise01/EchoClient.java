package Exercise01;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public final class EchoClient {

    public static void main(String[] args) throws Exception
    {
        try (Socket socket = new Socket("localhost", 22222))
        {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            System.out.println(br.readLine());


            String message;
            Scanner scanner = new Scanner(System.in);

            do
            {
                System.out.print("Client> ");
                message = scanner.nextLine();

            }while(!message.toUpperCase().equals("EXIT"));

            socket.notify();
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }
}















