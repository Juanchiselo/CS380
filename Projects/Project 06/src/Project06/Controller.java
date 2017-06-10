package Project06;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.List;

public class Controller
{
    /*IMAGES*/
    private Image zero;
    private Image one;
    @FXML private ImageView upperLeftImageView;
    @FXML private ImageView upperCenterImageView;
    @FXML private ImageView upperRightImageView;
    @FXML private ImageView middleLeftImageView;
    @FXML private ImageView middleCenterImageView;
    @FXML private ImageView middleRightImageView;
    @FXML private ImageView lowerLeftImageView;
    @FXML private ImageView lowerCenterImageView;
    @FXML private ImageView lowerRightImageView;
    private List<ImageView> images;

    @FXML private Label messageLabel;

    public void initialize()
    {
        zero = new Image(TicTacToeClient.class.getResourceAsStream("Drawables/0.png"));
        one = new Image(TicTacToeClient.class.getResourceAsStream("Drawables/1.png"));

        images = Arrays.asList(upperLeftImageView, upperCenterImageView, upperRightImageView,
                middleLeftImageView, middleCenterImageView, middleRightImageView,
                lowerLeftImageView, lowerCenterImageView, lowerRightImageView);

        for(ImageView image : images)
            image.addEventHandler(MouseEvent.MOUSE_CLICKED, new ImageViewEventHandler());
    }

    private class ImageViewEventHandler implements EventHandler<Event>{
        @Override
        public void handle(Event event)
        {
            ImageView imageView = (ImageView) event.getSource();

            if(imageView.getImage() == null && TicTacToeThread.currentTurn.equals("USER"))
            {
                imageView.setImage(zero);
                TicTacToeThread.makeMove();
            }
        }
    }

    public void updateMessageLabel(String message)
    {
        messageLabel.setText(message);
    }

}
