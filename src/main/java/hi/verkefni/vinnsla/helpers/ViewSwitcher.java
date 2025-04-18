package hi.verkefni.vinnsla.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ViewSwitcher {

    private static Scene scene;

    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }

    public static void switchTo(View view) {
        switchTo(view, null);
    }

    public static void switchTo(View view, Object data) {
        if (scene == null) {
            System.out.println("No scene was set");
            return;
        }

        try {
            System.out.println("Loading from FXML: " + view.getFileName());

            FXMLLoader loader = new FXMLLoader(ViewSwitcher.class.getResource(view.getFileName()));
            loader.setResources(LanguageManager.getBundle());

            Parent root = loader.load();

            Object controller = loader.getController();

            AppEnvironment env = AppEnvironment.getInstance();
            if (env != null) {
                env.setCurrentController(controller);
                env.setCurrentView(view);
                env.setCurrentViewData(data);
            }

            if (data != null && controller instanceof SupportsDataInjection sdi) {
                sdi.injectData(data);
            }

            // Set new root
            scene.setRoot(root);

            // Resize the stage to fit the new content
            scene.getWindow().sizeToScene();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void reload() {
        AppEnvironment env = AppEnvironment.getInstance();
        if (env != null && env.getCurrentView() != null) {
            switchTo(env.getCurrentView(), env.getCurrentViewData());
        } else {
            System.out.println("No previous view to reload.");
        }
    }
}
