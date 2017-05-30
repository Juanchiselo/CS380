package Project06;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController
{
    @FXML
    private TextField usernameTextField;

    public void onLogin()
    {
        TicTacToeClient.connectToServer(usernameTextField.getText());
        TicTacToeClient.stage.setScene(TicTacToeClient.ticTacToeScene);
        TicTacToeClient.stage.setResizable(false);
    }
}
