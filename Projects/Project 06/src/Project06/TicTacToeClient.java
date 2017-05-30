package Project06;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TicTacToeClient extends Application
{
    private static Socket socket;
    public static Scene loginScene;
    public static Scene ticTacToeScene;
    public static Stage stage;
    public Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Loads the FXML for the Login Scene and creates the Scene.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/Login.fxml"));
        loginScene = new Scene(loader.load(), 500, 500);

        loader = new FXMLLoader(getClass().getResource("Layouts/TicTacToe.fxml"));
        ticTacToeScene = new Scene(loader.load(), 1280, 720);
        controller = loader.getController();

        stage = primaryStage;
        stage.getIcons().add(new Image(TicTacToeClient.class.getResourceAsStream("Drawables/Icon.png")));
        stage.setTitle("Project 06 - Tic Tac Toe - Jose Juan Sandoval");
        stage.setResizable(false);
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
        if(socket != null)
            disconnectFromServer();
        TicTacToeThread.endThread = true;
    }

    /**
     * Connects the client to the server and
     * creates a TicTacToeThread where the logic of the
     * game occurs.
     */
    public static void connectToServer(String username)
    {
        String hostName = "codebank.xyz";
        int portNumber = 38006;

        try
        {
            socket = new Socket(hostName, portNumber);
            TicTacToeThread.endThread = false;
            new TicTacToeThread(socket, username).start();
        }
        catch (UnknownHostException e) {
            System.err.println("ERROR: Unknown host " + hostName + ".");
        } catch (Exception e) {
            System.err.println("ERROR: Could not connect to " + hostName + ".");
        }
    }

    /**
     * Disconnects the client from the server.
     */
    public static void disconnectFromServer()
    {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage() + ".");
        }
    }
}
