package Project07;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.Socket;
import java.security.*;

public class FileTransfer extends Application
{
    private static Socket socket;
    public static Scene mainScene;
    public static Stage stage;
    public static Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        // Loads the FXML for the Login Scene and creates the Scene.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Layouts/MainScene.fxml"));
        mainScene = new Scene(loader.load(), 500, 500);
        controller = loader.getController();

        stage = primaryStage;
        stage.getIcons().add(new Image(FileTransfer.class.getResourceAsStream("Drawables/Icon.png")));
        stage.setTitle("Project 07 - Encrypted File Transfer - Jose Juan Sandoval");
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

    public static void makeKeys()
    {
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096); // you can use 2048 for faster key generation
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            FileHandler.getInstance().saveFileGUI("Private Key", privateKey);
            FileHandler.getInstance().saveFileGUI("Public Key", publicKey);
        }
        catch (NoSuchAlgorithmException exception)
        {
            exception.printStackTrace(System.err);
        }
    }
}
