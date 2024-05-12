package view;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MessagePrinter {
    public void showMessage(String message, Label popupLabel) {
        popupLabel.setText(message);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), popupLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> popupLabel.setText(""));
        fadeTransition.play();
    }
}
