package hi.verkefni.vinnsla.helpers;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public enum View {
    VELKOMINN("/hi/verkefni/vidmot/fxml/velkominn-view.fxml"),
    PROFILE("/hi/verkefni/vidmot/fxml/user-environment-view.fxml"),
    EVENTMANAGER("/hi/verkefni/vidmot/fxml/eventmanager-view.fxml"),
    QUICKSTART("/hi/verkefni/vidmot/fxml/quickstart-view.fxml"),
    EVENTVIEWER("/hi/verkefni/vidmot/fxml/event-viewer.fxml"),
    ;

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
