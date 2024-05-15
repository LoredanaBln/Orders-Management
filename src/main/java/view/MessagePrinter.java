package view;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Utility class for displaying messages with a fade-out effect.
 */
public class MessagePrinter {

    /**
     * Displays a message on a Label and fades it out after 3 seconds.
     * @param message The message to display.
     * @param popupLabel The Label where the message will be displayed.
     */
    public void showMessage(String message, Label popupLabel) {
        popupLabel.setText(message);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), popupLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        fadeTransition.setOnFinished(event -> popupLabel.setText(""));

       fadeTransition.play();
    }
}
