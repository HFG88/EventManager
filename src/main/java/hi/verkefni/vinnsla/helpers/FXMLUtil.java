package hi.verkefni.vinnsla.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

public class FXMLUtil {

    public static <T> Parent load(String fxmlPath, T controller) {
        try {
            if (!fxmlPath.startsWith("/")) {
                fxmlPath = "/" + fxmlPath;
            }

            URL location = FXMLUtil.class.getResource(fxmlPath);
            if (location == null) {
                System.err.println("⚠️ FXMLUtil: FXML file not found at path: " + fxmlPath);
                return null;
            }

            FXMLLoader loader = new FXMLLoader(location);
            loader.setController(controller);
            loader.setRoot(controller);
            loader.setResources(LanguageManager.getBundle());
            return loader.load();
        } catch (IOException e) {
            System.err.println("⚠️ FXMLUtil: Failed to load FXML from " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }

    public static FXMLLoader createLoader(String fxmlPath) {
        if (!fxmlPath.startsWith("/")) {
            fxmlPath = "/" + fxmlPath;
        }

        URL location = FXMLUtil.class.getResource(fxmlPath);
        if (location == null) {
            throw new RuntimeException("FXML file not found: " + fxmlPath);
        }
        FXMLLoader loader = new FXMLLoader(location);
        loader.setResources(LanguageManager.getBundle());
        return loader;
    }

    public static Scene loadScene(View view, Object data) {
        try {
            FXMLLoader loader = createLoader(view.getFileName());
            Parent root = loader.load();

            Object controller = loader.getController();
            if (data != null && controller instanceof SupportsDataInjection sdi) {
                sdi.injectData(data);
            }

            Scene scene = new Scene(root);
            ViewSwitcher.setScene(scene); // Track the scene globally

            // Update current view in AppEnvironment
            AppEnvironment env = AppEnvironment.getInstance();
            if (env != null) {
                env.setCurrentView(view);
                env.setCurrentViewData(data);
            }

            return scene;
        } catch (IOException e) {
            System.err.println("FXMLUtil: Failed to load scene from FXML: " + view.getFileName());
            e.printStackTrace();
            return null;
        }
    }
}
