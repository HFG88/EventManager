package hi.verkefni.vidmot.view;

import hi.verkefni.vinnsla.helpers.AppEnvironment;
import hi.verkefni.vinnsla.helpers.LanguageManager;
import hi.verkefni.vinnsla.UserProfile;
import hi.verkefni.vinnsla.helpers.View;
import hi.verkefni.vinnsla.helpers.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class QuickStartController {

    @FXML
    private Button fxViewEventsButton;

    @FXML
    private Label fxWelcomeLabel;

    @FXML
    public void initialize() {
        AppEnvironment env = AppEnvironment.getInstance();
        UserProfile user = env.getCurrentUser();

        if (user != null) {
            ResourceBundle bundle = LanguageManager.getBundle();
            String template = bundle.getString("quickstart.welcome"); // "Velkomin, {0}!"
            fxWelcomeLabel.setText(MessageFormat.format(template, user.getUsername()));

            boolean hasAccessibleEvents = user.getAccessibleEvents() != null && !user.getAccessibleEvents().isEmpty();
            fxViewEventsButton.setDisable(!hasAccessibleEvents);
        }
    }

    @FXML
    private void onCreateEvent() {
        UserProfile user = AppEnvironment.getInstance().getCurrentUser();
        System.out.println("Creating event for: " + user.getUsername());
        ViewSwitcher.switchTo(View.EVENTMANAGER, user);
    }

    @FXML
    private void onViewEvents() {
        UserProfile user = AppEnvironment.getInstance().getCurrentUser();
        System.out.println("Viewing events for: " + user.getUsername());
        ViewSwitcher.switchTo(View.PROFILE, user);
    }
}
