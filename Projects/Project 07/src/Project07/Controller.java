package Project07;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller
{
    @FXML private Button makeKeysButton;

    /**Server Mode Components*/
    @FXML private TextField serverPortTextField;
    @FXML private Button serverBrowseButton;
    @FXML private Button setAsServerButton;

    /**Client Mode Components*/
    @FXML private TextField clientHostTextField;
    @FXML private TextField clientPortTextField;
    @FXML private Button clientBrowseButton;
    @FXML private Button setAsClientButton;

    public void onMakeKeys()
    {
        FileTransfer.makeKeys();
    }

    public void onSetAsServer()
    {

    }

    public void onSetAsClient()
    {

    }



    public void disableClientMode(boolean disable)
    {
        setAsClientButton.setDisable(disable);
    }

    public void disableServerMode(boolean disable)
    {
        setAsServerButton.setDisable(disable);
    }
}
