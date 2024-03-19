package no.ntnu.game.Button;
import com.badlogic.gdx.graphics.Color;
/**
 * Abstract factory for Buttons in the application
 *
 * @author Jeff
 */
public class ButtonFactory {
    // the theme of the game is this color so button would be this
    public static Color Starknight = new Color(61 / 255f, 63 / 255f, 65 / 255f, 255 / 255f);

    public static Button createLeftArrowButton(float x, float y) {
        Button leftArrowButton = new CircleButton(x, y);
        leftArrowButton.setColor(Starknight); // Set color for left arrow button
        leftArrowButton.setText("<"); // Set text for left arrow button
        return leftArrowButton;
    }

    public static Button createRightArrowButton(float x, float y) {
        Button rightArrowButton = new CircleButton(x, y);
        rightArrowButton.setColor(Starknight);
        rightArrowButton.setText(">");
        return rightArrowButton;
    }
    public static Button createSettingButton(float x, float y) {
        Button SettingButton = new CircleButton(x, y);
        SettingButton.setColor(Starknight);
        SettingButton.setText("Settings");
        return SettingButton;
    }

    // this is for the menu screen where u need multiple buttons to navigate
    public static Button createMenuButton(float x, float y, String text) {
        Button SettingButton = new RectangleButton(x, y);
        SettingButton.setColor(Starknight);
        SettingButton.setText(text);
        return SettingButton;
    }
}
