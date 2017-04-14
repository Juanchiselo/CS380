package Project01;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller
{
    @FXML
    private TextField messageTextField;
    @FXML
    private ListView<String> chatWindowListView;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField usernameTextField;

    private ObservableList<String> messages = FXCollections.observableArrayList();

    public void login()
    {
        ChatClient.sendMessage(usernameTextField.getText());
        ChatClient.stage.setScene(ChatClient.chatWindowScene);
        ChatClient.stage.setResizable(true);
        ChatClient.stage.centerOnScreen();
    }

    public void sendMessage()
    {
        String message = messageTextField.getText();
        if(!message.trim().isEmpty())
        {
            ChatClient.sendMessage(message);
            messageTextField.clear();
        }
    }

    public void setMessages(String message)
    {
        messages.add(message);
        chatWindowListView.setItems(messages);
    }

    public void setStatus(String status)
    {
        statusLabel.setText("Status: " + status + ".");
    }
}
