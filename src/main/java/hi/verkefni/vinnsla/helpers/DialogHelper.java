package hi.verkefni.vinnsla.helpers;

import javafx.scene.control.*;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogHelper {

    public static void showWarning(String titleKey, String headerKey, String contentKey) {
        ResourceBundle bundle = LanguageManager.getBundle();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(bundle.getString(titleKey));
        alert.setHeaderText(bundle.getString(headerKey));
        alert.setContentText(bundle.getString(contentKey));
        alert.showAndWait();
    }

    public static void showInfo(String titleKey, String headerKey, String contentText) {
        ResourceBundle bundle = LanguageManager.getBundle();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString(titleKey));
        alert.setHeaderText(bundle.getString(headerKey));
        alert.setContentText(contentText); // assumes contentText is already formatted
        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmation(String titleKey, String headerKey, String contentKey,
                                                        String yesKey, String noKey) {
        ResourceBundle bundle = LanguageManager.getBundle();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString(titleKey));
        alert.setHeaderText(bundle.getString(headerKey));
        alert.setContentText(bundle.getString(contentKey));

        ButtonType yes = new ButtonType(bundle.getString(yesKey));
        ButtonType no = new ButtonType(bundle.getString(noKey), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no);

        return alert.showAndWait();
    }

    public static Optional<String> showTextInput(String titleKey, String headerKey, String contentKey) {
        ResourceBundle bundle = LanguageManager.getBundle();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString(titleKey));
        dialog.setHeaderText(bundle.getString(headerKey));
        dialog.setContentText(bundle.getString(contentKey));
        return dialog.showAndWait();
    }

    public static Optional<ButtonType> showCustomConfirmation(String titleKey, String headerKey, String contentKey,
                                                              String yesKey, String noKey, String cancelKey) {
        ResourceBundle bundle = LanguageManager.getBundle();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString(titleKey));
        alert.setHeaderText(bundle.getString(headerKey));
        alert.setContentText(bundle.getString(contentKey));

        ButtonType yes = new ButtonType(bundle.getString(yesKey));
        ButtonType no = new ButtonType(bundle.getString(noKey));
        ButtonType cancel = new ButtonType(bundle.getString(cancelKey), ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yes, no, cancel);
        return alert.showAndWait();
    }

    public enum SaveChoice {
        SAVE,
        DONT_SAVE,
        CANCEL
    }

    public static SaveChoice showSaveConfirmation() {
        ResourceBundle bundle = LanguageManager.getBundle();

        ButtonType save = new ButtonType(bundle.getString("button.save"));
        ButtonType dontSave = new ButtonType(bundle.getString("button.dontSave"));
        ButtonType cancel = new ButtonType(bundle.getString("button.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("alert.unsaved.title"));
        alert.setHeaderText(bundle.getString("alert.unsaved.header"));
        alert.setContentText(bundle.getString("alert.unsaved.content"));
        alert.getButtonTypes().setAll(save, dontSave, cancel);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty()) {
            return SaveChoice.CANCEL;
        } else if (result.get() == save) {
            return SaveChoice.SAVE;
        } else if (result.get() == dontSave) {
            return SaveChoice.DONT_SAVE;
        } else {
            return SaveChoice.CANCEL;
        }
    }

}
