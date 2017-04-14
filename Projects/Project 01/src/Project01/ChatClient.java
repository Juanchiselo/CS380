package Project01;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Application
{
    // FXML Controller.
    public static Controller controller;
    private static Socket socket;
    public static Scene chatWindowScene;
    public static Scene chatLoginScene;
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/ChatLoginLayout.fxml"));
        Parent loginRoot = loader.load();
        loader = new FXMLLoader(getClass().getResource("Layouts/ChatWindowLayout.fxml"));
        Parent chatRoot = loader.load();
        controller = loader.getController();
        stage = primaryStage;
        primaryStage.setTitle("Project 1 - Jose Juan Sandoval");
        chatLoginScene = new Scene(loginRoot, 500, 500);
        chatWindowScene = new Scene(chatRoot, 1280, 720);
        primaryStage.setResizable(false);
        primaryStage.setScene(chatLoginScene);
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        String hostName = "codebank.xyz";
        int portNumber = 38001;

        try
        {
            socket = new Socket(hostName, portNumber);

            // Creates and starts a new thread to listen for messages.
            new ListenerThread(socket).start();

            // Launches the GUI.
            launch(args);
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

    public static void sendMessage(String message)
    {
        try
        {
            // Objects needed for sending messages to the server.
            OutputStream outputStream = socket.getOutputStream();
            PrintStream out = new PrintStream(outputStream, false, "UTF-8");

            out.println(message);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());

            // Updates the statusLabel GUI object.
            Platform.runLater(() ->
                    controller.setStatus(e.getMessage()));
        }
    }
}
