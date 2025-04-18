package hi.verkefni.vidmot.components;

import hi.verkefni.vinnsla.helpers.*;
import hi.verkefni.vidmot.view.VelkominnView;
import hi.verkefni.vinnsla.data.StartViewData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.util.Locale;

public class LanguageButton extends Button {

    private final ImageView flagIcon;

    public LanguageButton() {
        // ⬇️ Simplified FXML loading
        FXMLUtil.load("/hi/verkefni/vidmot/fxml/language-button.fxml", this);

        // 🏁 Set up the initial flag icon
        flagIcon = new ImageView(getFlagForLocale(LanguageManager.getLocale()));
        flagIcon.setFitWidth(32);
        flagIcon.setFitHeight(32);

        setPrefSize(32, 32);
        setMinSize(32, 32);
        setMaxSize(32, 32);
        setGraphic(flagIcon);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setStyle("-fx-alignment: center; -fx-padding: 0;");
        setClip(new Circle(16, 16, 16));
        setOnAction(e -> changeLanguage());
    }

    @FXML
    private void changeLanguage() {
        Locale current = LanguageManager.getLocale();
        Locale next = current.equals(Locale.ENGLISH) ? new Locale("is") : Locale.ENGLISH;
        LanguageManager.setLocale(next);

        flagIcon.setImage(getFlagForLocale(next));

        AppEnvironment env = AppEnvironment.getInstance();

        if (env.getCurrentView() == View.VELKOMINN && env.getCurrentController() instanceof VelkominnView vv) {
            StartViewData data = new StartViewData();
            data.username = vv.getUsername(); // You'll implement this getter in VelkominnView
            env.setCurrentViewData(data);
        }

        ViewSwitcher.switchTo(env.getCurrentView(), env.getCurrentViewData());
    }


    private Image getFlagForLocale(Locale locale) {
        String path = locale.equals(Locale.ENGLISH) ? "/images/en.png" : "/images/is.png";
        return new Image(getClass().getResourceAsStream(path));



    }
}
