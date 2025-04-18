package hi.verkefni.vidmot.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;


public class KynningController {
    // viðmótið
    @FXML
    public MediaView fxMediaView; // eingöngu birtingin (rendering) á myndbandinu
    @FXML
    public Slider fxSlVolume; // Volume slider
    @FXML
    private VBox rootVBox;

    // vinnslan
    private MediaPlayer mediaPlayer; // öll virknin fer fram hér


    /**
     * Getter fyrir mediaPlayer
     *
     * @return mediaPlayer
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }


    /**
     * Setter fyrir media
     *
     * @param media
     */
    public void setMedia(Media media) {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
        mediaPlayer = new MediaPlayer(media);
        fxMediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.volumeProperty().bind(fxSlVolume.valueProperty().divide(100));
        mediaPlayer.setAutoPlay(true);

    }

    /**
     * Býr til nýjan MediaPlayer út frá slóð á media skrá
     *
     * @param mediaURL Slóð á myndband
     */
    private void newMediaPlayer(String mediaURL) {
        mediaPlayer = new MediaPlayer(new Media(mediaURL));
        mediaPlayer.setAutoPlay(true);
        fxMediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.volumeProperty().bind(fxSlVolume.valueProperty().divide(100));
    }

    /**
     * Stýring fyrir play/pause takkann. Fer úr stöðunni pause í play og úr play í pause.
     *
     * @param actionEvent atburðurinn notaður til að ná í
     *                    play/pause hnappinn - athugið er ekki tilviksbreyta
     */
    public void playPauseAction(ActionEvent actionEvent) {
        Button playButton = (Button) actionEvent.getSource();
        System.out.println(playButton.getText());

        if (playButton.getText().equals("▶")) {
            mediaPlayer.play();
            System.out.println("play " + mediaPlayer.getStatus());
            playButton.setText("| |");
        } else {
            mediaPlayer.pause();
            System.out.println("pause " + mediaPlayer.getStatus());
            playButton.setText("▶");
        }
    }

    /**
     * Handler til að rewind-a í byrjun
     *
     * @param ignored ónotað
     */
    public void rewindAction(ActionEvent ignored) {
        mediaPlayer.seek(Duration.ZERO);
    }


}
