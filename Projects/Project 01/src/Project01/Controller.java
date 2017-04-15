package Project01;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller
{
    @FXML private TextField messageTextField;
    @FXML private ListView<String> chatWindowListView;
    @FXML private Label statusLabel;
    @FXML private TextField usernameTextField;
    private ObservableList<String> messages = FXCollections.observableArrayList();

    /**
     * Switches between the Login and Chat windows.
     */
    public void switchWindows()
    {
        if(ChatClient.stage.getScene().equals(ChatClient.chatLoginScene))
        {
            ChatClient.stage.setScene(ChatClient.chatWindowScene);
            ChatClient.stage.setResizable(true);
        }
        else
        {
            ChatClient.stage.setScene(ChatClient.chatLoginScene);
            ChatClient.stage.setResizable(false);
        }
        ChatClient.stage.centerOnScreen();
    }

    /**
     * Logs in the user by connecting him/her to the server
     * and sends the username to the server.
     */
    public void login()
    {
        ChatClient.connectToServer();
        ChatClient.sendMessage(usernameTextField.getText());
        switchWindows();
    }

    /**
     * Logs out the user by disconnecting from the server
     * and returns him/her to the login screen.
     * It also notifies the Listener thread to terminate.
     */
    public void logout()
    {
        switchWindows();
        messages.clear();
        ListenerThread.endThread = true;
        ChatClient.disconnectFromServer();
    }

    /**
     * Displays error messages in the form of alerts.
     * @param title - The title of the alert.
     * @param errorMessage - The error message.
     */
    public void displayError(String title, String errorMessage)
    {
        logout();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    /**
     * Sends a message to the server when the user
     * either clicks on the Send button or presses
     * the Enter key while the Message TextField is focused.
     */
    public void sendMessage()
    {
        String message = messageTextField.getText();

        // Trimming the message and checking if the
        // String is not empty after trimming it
        // prevents blank messages from being sent.
        if(!message.trim().isEmpty())
        {
            ChatClient.sendMessage(message);
            messageTextField.clear();
        }
    }

    /**
     * Displays the messages received from the server.
     * @param message - The last received message.
     */
    public void setMessages(String message)
    {
        messages.add(message);
        chatWindowListView.setItems(messages);
    }

    /**
     * Displays the status messages located in the status bar.
     * @param status - The status message.
     */
    public void setStatus(String status)
    {
        statusLabel.setText("Status: " + status + ".");
    }
}