package Project01;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Application
{
    private static Socket socket;
    public static Controller controller;
    public static Scene chatWindowScene;
    public static Scene chatLoginScene;
    public static Stage stage;

    /**
     * The overridden start() method belonging to the
     * Application class.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Loads the FXML for the Login Scene and creates the Scene.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/ChatLoginLayout.fxml"));
        chatLoginScene = new Scene(loader.load(), 500, 500);

        // Loads the FXML for the Chat Scene and creates the Scene.
        loader = new FXMLLoader(getClass().getResource("Layouts/ChatWindowLayout.fxml"));
        chatWindowScene = new Scene(loader.load(), 1280, 720);

        // Saves a reference of the Controller object so
        // the Listener thread can access it.
        controller = loader.getController();

        // Saves a reference of the Stage object so
        // the Controller class can access it.
        // It also sets the stage.
        stage = primaryStage;
        stage.getIcons().add(new Image(ChatClient.class.getResourceAsStream("Drawable/Icon.png")));
        stage.setTitle("Client Chat (Project 01) - Jose Juan Sandoval");
        stage.setResizable(false);
        stage.setScene(chatLoginScene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
        ListenerThread.endThread = true;
    }

    /**
     * Sends a message to the server.
     * @param message - The message to be sent.
     */
    public static void sendMessage(String message)
    {
        try
        {
            OutputStream outputStream = socket.getOutputStream();
            PrintStream out = new PrintStream(outputStream, false, "UTF-8");
            out.println(message);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());

            // Updates the statusLabel GUI object with the
            // error message.
            Platform.runLater(() ->
                    controller.setStatus(e.getMessage()));
        }
    }

    /**
     * Connects the client to the server and
     * creates a Listener thread.
     */
    public static void connectToServer()
    {
        String hostName = "codebank.xyz";
        int portNumber = 38001;

        try
        {
            socket = new Socket(hostName, portNumber);
            ListenerThread.endThread = false;

            // Creates and starts a new thread to listen for messages.
            new ListenerThread(socket).start();
        }
        catch (UnknownHostException e)
        {
            System.err.println("ERROR: Unknown host " + hostName + ".");
        }
        catch (Exception e)
        {
            System.err.println("ERROR: Could not connect to " + hostName + ".");
        }
    }

    /**
     * Disconnects the client from the server.
     */
    public static void disconnectFromServer()
    {
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
}
