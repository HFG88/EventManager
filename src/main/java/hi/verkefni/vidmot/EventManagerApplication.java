package hi.verkefni.vidmot;

import hi.verkefni.vidmot.view.EventManagerView;
import hi.verkefni.vinnsla.helpers.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EventManagerApplication extends Application {

    @Override
    public void start(Stage stage) {
        // ✅ Initialize AppEnvironment early
        AppEnvironment.setInstance(new AppEnvironment(null, null, null, new LanguageManager()));

        Scene scene = FXMLUtil.loadScene(View.VELKOMINN, null);
        stage.setScene(scene);
        stage.setTitle("Viðburðastjóri");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
