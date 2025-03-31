package hi.verkefni.vidmot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EventManagerApplication extends Application {
    private static EventManagerController eventManagerController;

    /**
     * Setter fyrir eventManagerController
     *
     * @param controller Nýja gildið á eventManagerController
     */
    public static void setController(EventManagerController controller) {
        eventManagerController = controller;
    }

    /**
     * Getter fyrir eventManagerController
     *
     * @return Núverandi gildi á eventManagerController
     */
    public static EventManagerController getController() {
        return eventManagerController;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(EventManagerApplication.class.getResource("eventManager-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        setController(fxmlLoader.getController());

        stage.setTitle("Viðburðarstjóri");
        stage.setScene(scene);
        stage.show();

        stage.sizeToScene();

    }

    /**
     * @param args Ónotað
     */
    public static void main(String[] args) {
        launch();
    }
}
