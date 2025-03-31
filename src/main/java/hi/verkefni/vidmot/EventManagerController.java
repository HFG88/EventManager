package hi.verkefni.vidmot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import vinnsla.EventModel;


import java.util.Optional;

public class EventManagerController {
    // StackPane í miðju BorderPane í aðalviðmóti
    // Virkar eins og listi af viðmóts hlutum (EventView)
    @FXML
    private StackPane fxEventViews;

    private EventView currentView;   // Núverandi sýnilegur EventView
    private EventView vistadView;    // Vistuð útgáfa þegar notandi er að breyta viðburði
    private boolean editMode = false; // Satt ef viðburður er í breytingu


    // Listi af viðburðum (EventModel)
    private ObservableList<EventModel> list = FXCollections.observableArrayList();

    /**
     * Býr til tómt event view fyrir upphafsskjáinn og bætir því í viðmótið
     */
    public void initialize() {
        System.out.println("EventManagerController initialized!");
        currentView = new EventView();
        fxEventViews.getChildren().add(currentView);
    }

    /**
     * Vistar bæði EventView og tengdan EventModel ef nokkur skylirði eru uppfillt.
     * Ef viðburður með sama nafni er þegar til, býður notandanum að skrifa yfir eldri útgáfu.
     */
    public void vista() {
        EventModel model = currentView.getEventModel();
        // Passar að viðburðurinn hafi nafn
        if (model.hasName()) {
            // Athugar hvort viðburður með sama nafni er nú þegar í listanum
            for (EventModel eventModel : list) {
                if (eventModel.getHeiti().
                        equalsIgnoreCase(model.getHeiti()) && eventModel != model) {
                    // Býr til viðvörunarglugga og spyr hvort eigi að skrifa yfir
                    // ef viðburður er þegar til
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Viðburður er þegar til");
                    alert.setHeaderText("Viðburður með þessu nafni er þegar til.");
                    alert.setContentText("Viltu vista nýja í staðinn?");

                    ButtonType yfirskrifa = new ButtonType("Já");
                    ButtonType haetta = new ButtonType("Nei", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(yfirskrifa, haetta);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == yfirskrifa) {
                        // Fjarlægir viðburðinn sem var
                        // áður til og viðeigandi viðmót
                        list.remove(eventModel);
                        EventView oldView = finnaVidmot(eventModel);
                        if (oldView != null) {
                            fxEventViews.getChildren().remove(oldView);
                        }
                        break;
                    }
                    else {
                        return; // Notandi valdi að vista ekki, hættir keyrslu
                    }
                }
            }

            // "Vistar" currentView í viðmótið
            include(currentView);

            // Bætir nýjum viðburði við listann ef hann er ekki nú þegar til
            if (!list.contains(model)) {
                list.add(model);
                System.out.println("Event saved.");
            }

            // Merkir viðburð sem vistaðan og gerir hann óbreytanlegan
            currentView.markSaved();
            currentView.setReadOnly();
            editMode = false;

        }
        else {
            // Ef ekkert nafn var slegið inn, birtist viðvörun
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Gat ekki vistað");
            alert.setHeaderText("Ekkert nafn hefur verið slegið inn.");
            alert.setContentText("Vinsamlegast sláðu inn heiti á viðburð áður en þú vistar.");
            alert.showAndWait();
            return;
        }

        // Smá debug
        debug();
    }


    /**
     * Opnar glugga þar sem notandi getur slegið inn nafn á vistuðum viðburði.
     * Ef viðburður með það nafn finnst í listanum (list), birtist tengt EventView í viðmótinu.
     * Athugar fyrst hvort þörf sé á að vista núverandi viðmót eða henda því.
     */
    public void opna() {
        checkCurrentView();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Viðburður");
        dialog.setHeaderText("Veldu viðburð");
        dialog.setContentText("Heiti viðburðar:");

        Optional<String> input = dialog.showAndWait();

        if (input.isPresent()) {
            EventModel vidburdur = finnaVidburd(input.get());

            if (vidburdur != null) {
                EventView vidmot = finnaVidmot(vidburdur);
                switchView(vidmot);
            }
        }
        dialog.close();

        // Smá debug
        debug();
    }


    /**
     * Býr til nýjan tóman viðburð (EventView) og birtir hann í viðmótinu.
     * Ef slíkur viðburður er þegar til (með tómt heiti), opnast hann í staðinn.
     * Athugar fyrst hvort þörf sé á að vista núverandi viðmót eða henda því.
     */
    public void nyr() {
        checkCurrentView();

        // Leitar að tómum
        for (Node node : fxEventViews.getChildren()) {
            EventView eventView = (EventView) node;

            if (eventView.getEventModel().isEmpty()) {
                switchView(eventView);
                return;
            }
        }

        // Enginn tómur til — býr til nýjan
        currentView = new EventView();
        include(currentView);
        switchView(currentView);
    }


    /**
     * Setur forritið í breytingaham ef núverandi viðburður (currentView)
     * hefur verið vistaður.Vistað viðmót er geymt í vistadView svo
     * notandinn geti breytt gögnum án þess að þau séu glötuð ef hann hættir
     * við. Kannski ekki besta leiðin, en hér erum við.
     */
    public void breyta() {
        if (currentView.isSaved()) {
            editMode = true;
            this.vistadView = currentView;
            this.currentView.setEditable();
        }
    }

    /**
     * Lokar forritinu
     */
    public void haetta() {
        System.exit(0);
    }


    /**
     * Sýnir upplýsingaglugga ("Um forritið") með nöfnum allra vistaðra viðburða.
     */
    public void um() {
        StringBuilder message = new StringBuilder("Þetta forrit heldur utan um viðburði.\n\n");

        if (list.isEmpty()) {
            message.append("Engir viðburðir hafa verið vistaðir ennþá.");
        }
        else {
            message.append("Vistaðir viðburðir:\n");
            for (EventModel model : list) {
                message.append("• ").append(model.getHeiti()).append("\n");
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Um forritið");
        alert.setHeaderText("Viðburðaskráningarkerfi");
        alert.setContentText(message.toString());
        alert.showAndWait();
    }

    /**
     * Skiptir yfir í annað EventView í viðmótinu.
     * Geymir nýja stöðu í currentView og stoppar myndband ef það er í gangi.
     *
     * @param targetView Viðmótið sem á að sýna
     */
    private void switchView(EventView targetView) {
        for (Node node : fxEventViews.getChildren()) {
            node.setVisible(false);
        }

        // Stoppar myndband ef það er í gangi í núverandi EventView
        if (currentView != null && currentView.getKynningController() != null) {
            MediaPlayer mp = currentView.getKynningController().getMediaPlayer();
            if (mp != null && mp.getStatus() == MediaPlayer.Status.PLAYING) {
                mp.pause();
            }
        }

        // Sýnir nýja EventView
        targetView.setVisible(true);
        targetView.setFocusTraversable(true);
        currentView = targetView;

        // Endurstillir stærð glugga ef þörf krefur (Mögulega aldrei þörf lengur?)
        Stage stage = (Stage) fxEventViews.getScene().getWindow();
        stage.sizeToScene();

        System.out.println("Switch view: " + currentView);
    }


    /**
     * Athugar stöðu currentView vinnur út frá því
     * - Ef viðburðurinn hefur nafn en er ekki vistaður, býður forritið upp á að vista hann
     * - Ef nafnið er tómt og notandi er í breytingaham vistast "gamla" útgáfan aftur
     * - Ef viðburðurinn er ekki vistaður (og ekki í breytingaham), eyðist hann úr lista og viðmóti
     */
    private void checkCurrentView() {
        if (currentView.getEventModel().hasName() && !currentView.isSaved()) {
            askVista();
        }
        else if (!currentView.getEventModel().hasName() && editMode) {
            revertChanges();
        }
        else if (!currentView.isSaved()) {
            eyda(currentView);
        }
    }


    /**
     * Birtir glugga sem spyr notanda hvort eigi að vista óvistaðan viðburð.
     * Ef notandi velur að vista, er núverandi viðburður vistaður.
     * Ef notandi velur að vista ekki, er currentView eytt og vistuð útgáfa endurheimt ef til er.
     */
    private void askVista() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Óvistað efni");
        alert.setHeaderText("Viðburðurinn hefur ekki verið vistaður.");
        alert.setContentText("Viltu vista áður en þú býrð til nýjan?");

        ButtonType vista = new ButtonType("Vista");
        ButtonType ekkiVista = new ButtonType("Ekki vista");
        ButtonType haetta = new ButtonType("Hætta við", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(vista, ekkiVista, haetta);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == vista) {
                vista();
            }
            else if (result.get() == ekkiVista) {
                if (vistadView != null) {
                    include(vistadView);
                    vistadView = null;
                }
                eyda(currentView);
            }
        }
    }


    /**
     * Endurheimtir vistuðu útgáfuna af viðburðinum ef notandi var að breyta og hættir við.
     * Notað þegar breytingar á núverandi viðburði (currentView) eiga ekki að gilda.
     */
    private void revertChanges() {
        if (editMode) {
            // Bætir vistuðu útgáfunni aftur í viðmótið
            include(vistadView);

            // Eyðir viðburðinum sem verið var að breyta
            eyda(currentView);

            // Hættir í breytingaham
            editMode = false;
        }
    }


    /**
     * Eyðir gefnum EventView úr viðmóti og tilheyrandi EventModel úr lista.
     *
     * @param eventView Viðmótshlutur sem á að fjarlægja
     *                  (inniheldur EventModel sem á líka að fjarlægja)
     */
    private void eyda(EventView eventView) {
        list.remove(eventView.getEventModel());
        fxEventViews.getChildren().remove(eventView);
        // Smá debug
        debug();
    }

    /**
     * Bætir EventView við viðmótið ef hann er ekki þegar til staðar.
     *
     * @param view Viðburðarviðmót sem á að bæta við StackPane.
     */
    private void include(EventView view) {
        // Bætir við ef ekki nú þegar til staðar
        if (!fxEventViews.getChildren().contains(view)) {
            fxEventViews.getChildren().add(view);
        }
    }


    /**
     * Leitar að EventModel í listanum eftir nafni.
     *
     * @param eventName Heiti sem borið er saman við heiti allra viðburða í listanum.
     * @return EventModel með samsvarandi heiti ef hann finnst, annars null.
     */
    private EventModel finnaVidburd(String eventName) {
        for (EventModel vidburdur : list) {
            if (vidburdur.getHeiti().equalsIgnoreCase(eventName)) {
                return vidburdur;
            }
        }
        return null;
    }

    /**
     * Leitar að EventView sem inniheldur tiltekið EventModel.
     *
     * @param model EventModel sem á að tengja við viðmótshlut.
     * @return EventView sem tengist viðkomandi EventModel, eða null ef hann finnst ekki.
     */
    private EventView finnaVidmot(EventModel model) {
        for (Node node : fxEventViews.getChildren()) {
            EventView eventView = (EventView) node;
            if (eventView.getEventModel().equals(model)) {
                return eventView;
            }
        }
        return null;
    }


    /**
     * Var bara smá "on command" debug. Ekkert notað lengur
     */
    private void prentaVidmot() {
        int i = 1;
        for (Node node : fxEventViews.getChildren()) {
            EventView eventView = (EventView) node;
            System.out.println(i++ + ": " + eventView);
        }
    }

    /**
     * Bara smá debug dæmi
     */
    private void debug() {
        System.out.println("Current view: " + currentView);
        System.out.println("Saved views: " + fxEventViews.getChildren().size());
        System.out.println("Event list: " + list.size());
    }

}
