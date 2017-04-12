package Exercise01;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public final class EchoServer
{
    public static void main(String[] args) throws Exception
    {
        /**
         * ServerSocket serverSocket
         *
         * The ServerSocket class implements server sockets. A server socket waits for requests to come in over the network.
         * It performs some operation based on that request, and then possibly returns a result to the requester.
         *
         * new ServerSocket(22222)
         *
         * Creates a server socket, bound to the specified port, in the case above it is port 22222.
         * A port number of 0 means that the port number is automatically allocated, typically from
         * an ephemeral port range. This port number can then be retrieved by calling getLocalPort.
         */
        try (ServerSocket serverSocket = new ServerSocket(22222))
        {
            // This block of code only executes once, when the ServerSocket is created.
            // It does not execute every time a client connects to the server.

            // TODO: Figure out why was true used to trigger the while loop.
            while (true)
            {
                // This block of code executes at least once and executes again
                // after a client is done connecting to the server.
                // NOTE: A client does not need to connect to the server in order
                // for this block of code to execute the first time.
                //System.out.println("Executed inside the while loop!");

                /**
                 * serverSocket.accept()
                 *
                 * Listens for a connection to be made to this socket and accepts it.
                 * The method blocks until a connection is made.
                 *
                 * Socket socket
                 *
                 * The Socket class implements client sockets (also called just "sockets").
                 * A socket is an endpoint for communication between two machines.
                 */
                try (Socket socket = serverSocket.accept())
                {
                    // This block of code executes every time a client connects to the server.

                    // This gives you the IP address of the client that connected to the server.
                    String address = socket.getInetAddress().getHostAddress();

                    /**
                     * The System.out.printf() and System.out.format() methods allow you to format
                     * your printouts.
                     * The syntax for both methods is the same.
                     * System.out.printf(String format, Object...args)
                     * where format is a string that specifies the formatting to be used
                     * and args is a list of the variables to be printed using that formatting.
                     *
                     * System.out.printf("Client connected: %s%n", address);
                     *
                     * In the line above, the String "Client connected: %s%n" is the format.
                     * The variable address is the variable to be printed using the format.
                     *
                     * %s and %n are known as format specifiers, which are special characters
                     * that format the arguments of Object... args.
                     * Format specifiers begin with a percent sign (%) and end with a converter.
                     * The converter is a character indicating the type of argument to be formatted.
                     * In between the percent sign (%) and the converter you can have optional flags and specifiers.
                     * In the line of code above, there are not flags and specifiers,
                     * there is only the percent sign and a converter.
                     *
                     * %s
                     * The converter 's' or 'S' is part of the General Argument Category.
                     * If the argument arg is null, then the result is "null".
                     * If arg implements Formattable, then arg.formatTo is invoked.
                     * Otherwise, the result is obtained by invoking arg.toString().
                     *
                     * %n
                     * The %n is a platform-independent newline character.
                     * The converter 'n' is part of the Line Separator Argument Category.
                     * The result is the platform-specific line separator.
                     */
                    System.out.printf("Client connected: %s%n", address);

                    /**
                     * socket.getOutputStream()
                     *
                     * Returns an output stream for this socket.
                     * If this socket has an associated channel then the resulting output stream
                     * delegates all of its operations to the channel. If the channel is in non-blocking mode
                     * then the output stream's write operations will throw an IllegalBlockingModeException.
                     * Closing the returned OutputStream will close the associated socket.
                     * Returns: an output stream for writing bytes to this socket.
                     * Throws: IOException - if an I/O error occurs when creating the output stream
                     *          or if the socket is not connected.
                     *
                     * OutputStream outputStream
                     *
                     * The OutputStream abstract class is the superclass of all classes
                     * representing an output stream of bytes.
                     * An output stream accepts output bytes and sends them to some sink.
                     */
                    OutputStream outputStream = socket.getOutputStream();

                    /**
                     * PrintStream out
                     *
                     * The PrintStream class adds functionality to another output stream,
                     * namely the ability to print representations of various data values conveniently.
                     * Unlike other output streams, a PrintStream never throws an IOException;
                     * instead, exceptional situations merely set an internal flag
                     * that can be tested via the checkError method.
                     * Optionally, a PrintStream can be created so as to flush automatically;
                     * this means that the flush method is automatically invoked after a byte array is written,
                     * one of the println methods is invoked, or a newline character or byte ('\n') is written.
                     * All characters printed by a PrintStream are converted into bytes using
                     * the platform's default character encoding. The PrintWriter class should be used
                     * in situations that require writing characters rather than bytes.
                     *
                     * new PrintStream(outputStream, true, "UTF-8")
                     *
                     * Creates a new print stream.
                     * Parameters:
                     *      out - The output stream to which values and objects will be printed.
                     *      autoFlush - A boolean; if true, the output buffer will be flushed
                     *              whenever a byte array is written, one of the println methods is invoked,
                     *              or a newline character or byte ('\n') is written.
                     *      encoding - The name of a supported character encoding.
                     * Throws: UnsupportedEncodingException - If the named encoding is not supported.
                     *
                     */
                    PrintStream out = new PrintStream(outputStream, true, "UTF-8");


                    out.printf("Hi %s, thanks for connecting! If you would like to disconnect just type \"EXIT\"."
                            , address);

                    InputStream inputStream = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader br = new BufferedReader(isr);

                    String inputLine;
                    while((inputLine = br.readLine()) != null)
                    {
                        out.println("Server>" + inputLine);
                        if(inputLine.toUpperCase().equals("EXIT"))
                            break;
                    }
                }
            }
        }
    }
}
