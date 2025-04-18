package hi.verkefni.vidmot.components;

import hi.verkefni.vinnsla.data.EventData;
import hi.verkefni.vinnsla.Flokkur;
import hi.verkefni.vinnsla.helpers.FXMLUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class EventEditor extends VBox {

    @FXML
    private TextField fxHeiti;
    @FXML
    private ComboBox<Flokkur> fxFlokkur;
    @FXML
    private DatePicker fxDagsetning;
    @FXML
    private Spinner<Integer> fxHours;
    @FXML
    private Spinner<Integer> fxMinutes;
    @FXML
    private Button fxBtnVeljaMyndband;

    private String chosenMediaPath; // set when media is selected

    public EventEditor() {
        FXMLUtil.load("/hi/verkefni/vidmot/fxml/event-view.fxml", this);
    }

    @FXML
    public void initialize() {
        fxHours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
        fxMinutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 30));
        fxFlokkur.getItems().setAll(Flokkur.values());
    }

    public void fillWith(EventData data) {
        fxHeiti.setText(data.heiti);
        fxFlokkur.setValue(data.flokkur);
        fxDagsetning.setValue(data.dagsetning);
        // Media path handling, if needed
        this.chosenMediaPath = data.kynningarmyndband;
    }

    public EventData extractEventData() {
        EventData data = new EventData();
        data.heiti = fxHeiti.getText();
        data.flokkur = fxFlokkur.getValue();
        data.dagsetning = fxDagsetning.getValue();
        data.kynningarmyndband = chosenMediaPath;
        return data;
    }

    public void setKynningarmyndband(String path) {
        this.chosenMediaPath = path;
    }

    public boolean isEmpty() {
        return (fxHeiti.getText() == null || fxHeiti.getText().isBlank()) &&
                fxFlokkur.getValue() == null &&
                fxDagsetning.getValue() == null;
    }
}
