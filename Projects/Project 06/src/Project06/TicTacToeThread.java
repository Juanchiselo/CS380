package Project06;

import java.io.*;
import java.net.Socket;

public class TicTacToeThread extends Thread
{
    public volatile static boolean endThread = false;
    public volatile static String currentTurn;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ConnectMessage connectMessage;
    private CommandMessage commandMessage;
    private BoardMessage boardMessage;
    private ErrorMessage errorMessage;
    private Message message;

    public TicTacToeThread(Socket socket, String username)
    {
        super("Tic Tac Toe Thread");
        this.socket = socket;
        connectMessage = new ConnectMessage(username);
    }

    /**
     * The overridden run() function belonging to the Thread class.
     * This is what handles the communication between the server and the client.
     */
    public void run()
    {
        try
        {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(connectMessage);
            commandMessage = new CommandMessage(CommandMessage.Command.NEW_GAME);
            objectOutputStream.writeObject(commandMessage);
            currentTurn = "USER";

            while((message = (Message) objectInputStream.readObject()) != null)
            {
                System.out.println(message.getType());

                switch (message.getType())
                {
                    case BOARD:
                        boardMessage = (BoardMessage) message;

                        byte[][] board = boardMessage.getBoard();
                        System.out.println(board.toString());

                        switch (boardMessage.getStatus())
                        {
                            case PLAYER1_VICTORY:
                                break;
                            case PLAYER2_VICTORY:
                                break;
                            case PLAYER1_SURRENDER:
                                break;
                            case PLAYER2_SURRENDER:
                                break;
                            case IN_PROGRESS:
                                break;
                            case STALEMATE:
                                break;
                            case ERROR:
                                break;
                        }
                        break;
                    case ERROR:
                        errorMessage = (ErrorMessage) message;
                        System.out.println(errorMessage.getError());
                        break;
                }
            }

            //objectInputStream.close();
            //objectOutputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: " + e.getMessage() + ".");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void makeMove()
    {

    }
}
