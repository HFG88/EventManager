module hi.verkefni.eventmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;

    exports hi.verkefni.vidmot;
    exports hi.verkefni.vinnsla to com.fasterxml.jackson.databind, javafx.fxml;

    opens hi.verkefni.vidmot to javafx.fxml, javafx.media;
    opens hi.verkefni.vinnsla to javafx.fxml; // 👈 Required for FXML reflection
    opens hi.verkefni.vinnsla.helpers to javafx.fxml, javafx.media;

    exports hi.verkefni.vinnsla.helpers;
    exports hi.verkefni.vidmot.view;
    opens hi.verkefni.vidmot.view to javafx.fxml, javafx.media;
    exports hi.verkefni.vidmot.components;
    opens hi.verkefni.vidmot.components to javafx.fxml, javafx.media;
    exports hi.verkefni.vinnsla.data to com.fasterxml.jackson.databind, javafx.fxml;
    opens hi.verkefni.vinnsla.data to javafx.fxml;
}
