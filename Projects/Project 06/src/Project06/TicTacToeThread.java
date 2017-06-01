package Project06;

import javafx.application.Platform;

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

//            System.out.println(socket.getInputStream().read());
//            message = (Message) objectInputStream.readObject();
//            boardMessage = (BoardMessage) message;
//            System.out.println(boardMessage.getStatus());
//            objectOutputStream.writeObject(new MoveMessage((byte)1,(byte)2));

            while(!endThread)
            {
                message = (Message) objectInputStream.readObject();

                switch (message.getType())
                {
                    case BOARD:
                        boardMessage = (BoardMessage) message;
                        byte[][] board = boardMessage.getBoard();
                        System.out.println(board.toString());
                        String messageString = null;

                        switch (boardMessage.getStatus())
                        {
                            case PLAYER1_VICTORY:
                                messageString = "Player 1 Won!";
                                break;
                            case PLAYER2_VICTORY:
                                messageString = "Player 2 Won!";
                                break;
                            case PLAYER1_SURRENDER:
                                messageString = "Player 1 Surrendered!";
                                break;
                            case PLAYER2_SURRENDER:
                                messageString = "Player 2 Surrendered!";
                                break;
                            case IN_PROGRESS:
                                break;
                            case STALEMATE:
                                messageString = "Draw!";
                                break;
                            case ERROR:
                                messageString = "ERROR: ";
                                break;
                        }

                        Platform.runLater(() ->
                                TicTacToeClient.controller
                                        .updateMessageLabel("Player 1 Won!"));
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
            endThread = true;
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
