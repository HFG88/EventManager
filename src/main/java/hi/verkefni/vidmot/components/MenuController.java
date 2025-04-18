package hi.verkefni.vidmot.components;

import hi.verkefni.vidmot.view.EventManagerView;
import hi.verkefni.vinnsla.helpers.AppEnvironment;
import hi.verkefni.vinnsla.helpers.LanguageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import java.util.Locale;
import java.util.ResourceBundle;

public class MenuController {
    @FXML
    private MenuItem fxSkiptaUmTungu;
    @FXML
    private MenuItem fxNyr;
    @FXML
    private MenuItem fxOpna;
    @FXML
    private MenuItem fxVista;
    @FXML
    private MenuItem fxVistaSem;
    @FXML
    private MenuItem fxHaetta;
    @FXML
    private MenuItem fxUm;

    public void updateTexts() {
        ResourceBundle bundle = LanguageManager.getBundle();
        fxNyr.setText(bundle.getString("menu.nyr"));
        fxOpna.setText(bundle.getString("menu.opna"));
        fxVista.setText(bundle.getString("menu.vista"));
        fxVistaSem.setText(bundle.getString("menu.vistaSem"));
        fxHaetta.setText(bundle.getString("menu.haetta"));
        fxUm.setText(bundle.getString("menu.um"));
        fxSkiptaUmTungu.setText(bundle.getString("menu.language"));
    }

    public void onSkiptaUmTungu() {
        Locale next = LanguageManager.getLocale().equals(Locale.ENGLISH)
                ? new Locale("is")
                : Locale.ENGLISH;

        var controller = AppEnvironment.getInstance().getCurrentController();
        if (controller instanceof EventManagerView emv) {
            emv.changeLanguage(next);
        }
    }

    public void onNyr(ActionEvent actionEvent) {
        var controller = AppEnvironment.getInstance().getCurrentController();
        if (controller instanceof EventManagerView emv) {
            emv.nyr();
        }
    }

    public void onOpna(ActionEvent actionEvent) {
        var controller = AppEnvironment.getInstance().getCurrentController();
        if (controller instanceof EventManagerView emv) {
            emv.opna();
        }
    }

    public void onVista(ActionEvent actionEvent) {
        var controller = AppEnvironment.getInstance().getCurrentController();
        if (controller instanceof EventManagerView emv) {
            emv.vista();
        }
    }

    public void onVistaSem(ActionEvent actionEvent) {
        var controller = AppEnvironment.getInstance().getCurrentController();
        if (controller instanceof EventManagerView emv) {
            emv.vistaSem();
        }
    }

    public void onHaetta(ActionEvent actionEvent) {
        var controller = AppEnvironment.getInstance().getCurrentController();
        if (controller instanceof EventManagerView emv) {
            emv.haetta();
        }
    }

    public void onUm(ActionEvent actionEvent) {
        var controller = AppEnvironment.getInstance().getCurrentController();
        if (controller instanceof EventManagerView emv) {
            emv.um();
        }
    }

    public void onBreyta(ActionEvent actionEvent) {
        var controller = AppEnvironment.getInstance().getCurrentController();
        if (controller instanceof EventManagerView emv) {
            emv.onBreyta();
        }
    }
}
