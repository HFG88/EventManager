package hi.verkefni.vidmot.view;

import hi.verkefni.vinnsla.UserProfile;
import hi.verkefni.vinnsla.data.EventData;
import hi.verkefni.vinnsla.helpers.AppEnvironment;
import hi.verkefni.vinnsla.helpers.SupportsDataInjection;
import hi.verkefni.vinnsla.helpers.View;
import hi.verkefni.vinnsla.helpers.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;

public class EventViewerController implements SupportsDataInjection {

    @FXML
    private Label fxHeiti;

    @FXML
    private Label fxFlokkur;

    @FXML
    private Label fxDagsetning;

    @FXML
    private MediaView fxMediaView;

    @FXML
    private Button fxToProfileButton;

    @FXML
    private Button fxEditButton;

    private EventData currentEvent;

    @Override
    public void injectData(Object data) {
        if (data instanceof EventData event) {
            this.currentEvent = event;

            fxHeiti.setText(event.heiti);
            fxFlokkur.setText(event.flokkur != null ? event.flokkur.toString() : "");
            fxDagsetning.setText(event.dagsetning != null ? event.dagsetning.toString() : "");

            // Load video if exists
            if (event.kynningarmyndband != null) {
                File file = new File(event.kynningarmyndband);
                if (file.exists()) {
                    Media media = new Media(file.toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    fxMediaView.setMediaPlayer(mediaPlayer);
                    mediaPlayer.play(); // optional
                }
            }

            // Only show edit button if the user created the event
            UserProfile user = AppEnvironment.getInstance().getCurrentUser();
            fxEditButton.setVisible(user != null && user.getCreatedEvents().contains(event.heiti));
        }
    }

    @FXML
    private void onToProfilePressed() {
        UserProfile user = AppEnvironment.getInstance().getCurrentUser();
        if (user != null) {
            ViewSwitcher.switchTo(View.PROFILE, user);
        }
    }

    @FXML
    private void onEditPressed() {
        if (currentEvent != null) {
            ViewSwitcher.switchTo(View.EVENTMANAGER, currentEvent);
        }
    }
}
