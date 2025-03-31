package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vinnsla.EventModel;
import vinnsla.Flokkur;

import java.io.File;
import java.io.IOException;

public class EventView extends VBox {
    private final EventModel eventModel; // Inniheldur upplýsingar um viðburðinn
    private boolean saved = false;  // Svo hægt sé að fylgjast með því
    // hvort viðburður sé vistaður í listann

    @FXML
    private TextField fxHeiti; // Nafn viðburðar er skrifað hér
    @FXML
    private ComboBox<Flokkur> fxFlokkur; // Tegund viðburðar er valin hér
    @FXML
    private DatePicker fxDagsetning; // Dagsetning viðburðar er valin hér
    @FXML
    private Spinner<Integer> fxHours; // Hér er valið hvenær viðburðurinn byrjar
    @FXML
    private Spinner<Integer> fxMinutes; // Hér er valið hvenær viðburðurinn byrjar
    @FXML
    private Button fxBtnVeljaMyndband; // Hér fær notandi að velja myndband fyrir viðburðinn

    private KynningController kynningController; // Aðgangur að controller fyrir MediaView

    /**
     * No args constructor
     */
    public EventView() {
        this(new EventModel());
    }

    /**
     * Constructor sem tekur inn tilbúið model
     *
     * @param model EventModel sem á að tengjast þessu viðmóti
     */
    public EventView(EventModel model) {
        this.eventModel = model;
        loadFXML("event-view.fxml");
        setupMediaPane();
        setEditable();
    }

    /**
     * Frumstillir viðmótið og tengir viðmótshluti við EventModel.
     * Setur einnig ýmsa listener-a
     */
    public void initialize() {
        System.out.println("EventView initialized");

        // Set up components
        fxHours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
        fxMinutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 30));

        fxFlokkur.getItems().setAll(Flokkur.values());

        // Bind properties
        fxHeiti.textProperty().bindBidirectional(eventModel.heitiProperty());
        fxFlokkur.valueProperty().bindBidirectional(eventModel.flokkurProperty());
        fxDagsetning.valueProperty().bindBidirectional(eventModel.dagsetningProperty());

        // Change tracking
        fxHours.valueProperty().addListener((obs, oldVal, newVal) -> markUnsaved());
        fxMinutes.valueProperty().addListener((obs, oldVal, newVal) -> markUnsaved());

        fxFlokkur.valueProperty().addListener((obs, oldVal, newVal) -> markUnsaved());
        fxDagsetning.valueProperty().addListener((obs, oldVal, newVal) -> markUnsaved());
        fxHeiti.textProperty().addListener((obs, oldVal, newVal) -> markUnsaved());

        // Media button
        fxBtnVeljaMyndband.setOnAction(e -> onSelectMedia());
    }

    private void loadFXML(String fxmlName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        }
        catch (IOException exception) {
            throw new RuntimeException("Failed to load FXML", exception);
        }
    }

    private void setupMediaPane() {
        try {
            FXMLLoader mediaLoader = new FXMLLoader(getClass().getResource("media-view.fxml"));
            VBox mediaBox = mediaLoader.load();
            kynningController = mediaLoader.getController();

            eventModel.kynningarmyndbandProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && kynningController != null) {
                    kynningController.setMedia(newVal);
                }
            });

            this.getChildren().add(mediaBox);
        }
        catch (IOException e) {
            System.out.println("Eitthvað error?");
        }
    }

    /**
     * Getter fyrir eventModel
     *
     * @return eventModel
     */
    public EventModel getEventModel() {
        return this.eventModel;
    }

    /**
     * Gefur notanda leyfi til að breyta viðmótshlutunum
     */
    public void setEditable() {
        fxHeiti.setEditable(true);
        fxFlokkur.setDisable(false);
        fxDagsetning.setDisable(false);
        fxHours.setDisable(false);
        fxMinutes.setDisable(false);

    }

    /**
     * Læsir viðmótshlutunum
     */
    public void setReadOnly() {
        fxHeiti.setEditable(false);
        fxFlokkur.setDisable(true);
        fxDagsetning.setDisable(true);
        fxHours.setDisable(true);
        fxMinutes.setDisable(true);

    }

    /**
     * Kannar hvort Viðburðurinn sé vistaður (Getter fyrir saved)
     *
     * @return saved
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Stillir viðburðinn sem vistaðann
     */
    public void markSaved() {
        this.saved = true;
    }

    /**
     * Stillir viðburðinn sem ekki vistaðann
     */
    public void markUnsaved() {
        this.saved = false;
    }


    /**
     * Opnar FileChooser og leyfir notanda að velja myndband.
     * Skráin er vistuð í EventModel og MediaPlayer uppfærður.
     */
    private void onSelectMedia() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veldu myndband");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.m4v", "*.mov"));

        File file = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (file != null) {
            Media media = new Media(file.toURI().toString());
            eventModel.setKynningarmyndband(media);
            markUnsaved();

            Stage stage = (Stage) this.getScene().getWindow();
            stage.sizeToScene();
        }
    }

    /**
     * Getter fyrir kynningController
     *
     * @return kynningController
     */
    public KynningController getKynningController() {
        return kynningController;
    }

    /**
     * Prent aðferð fyrir EventView
     *
     * @return Staða EventView á String formi
     */
    @Override
    public String toString() {
        return "EventView{" +
                "eventModel=" +
                eventModel + ", " +
                "fxHeiti=" +
                fxHeiti.getText() +
                ", isSaved=" +
                isSaved() + '}';
    }


}
