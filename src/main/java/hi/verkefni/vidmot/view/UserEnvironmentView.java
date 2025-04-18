package hi.verkefni.vidmot.view;

import hi.verkefni.vinnsla.UserProfile;
import hi.verkefni.vinnsla.data.EventData;
import hi.verkefni.vinnsla.helpers.AppEnvironment;
import hi.verkefni.vinnsla.helpers.SupportsDataInjection;
import hi.verkefni.vinnsla.helpers.View;
import hi.verkefni.vinnsla.helpers.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import hi.verkefni.vinnsla.EventStorage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserEnvironmentView implements SupportsDataInjection {

    public Circle fxProfileCircle;
    public Button fxLogoutBtn;
    public Button fxCreateEventBtn;
    @FXML
    private Label fxWelcomeLabel;

    @FXML
    private VBox fxCreatedList;

    @FXML
    private VBox fxAccessibleList;

    private UserProfile currentUser;

    // Map to store which user created each accessible event
    private final Map<String, String> accessibleEventCreators = new HashMap<>();

    @Override
    public void injectData(Object data) {
        if (data instanceof UserProfile user) {
            currentUser = user;
            AppEnvironment.getInstance().setCurrentUser(user);
            fxWelcomeLabel.setText("Velkomin(n), " + user.getUsername());
            populateEventLists();
        }
    }

    private void populateEventLists() {
        fxCreatedList.getChildren().clear();
        fxAccessibleList.getChildren().clear();
        accessibleEventCreators.clear();

        // Created Events
        for (String eventName : currentUser.getCreatedEvents()) {
            fxCreatedList.getChildren().add(createEventCard(eventName, true, currentUser.getUsername()));
        }

        // Accessible Events (from user profile)
        for (String eventName : currentUser.getAccessibleEvents()) {
            if (!currentUser.getCreatedEvents().contains(eventName)) {
                // We assume the user was granted access, but don’t know the creator, so we look for it
                File eventsDir = new File("events");
                if (eventsDir.exists()) {
                    File[] userFolders = eventsDir.listFiles(File::isDirectory);
                    if (userFolders != null) {
                        for (File folder : userFolders) {
                            File possible = new File(folder, eventName + ".json");
                            if (possible.exists()) {
                                accessibleEventCreators.put(eventName, folder.getName());
                                fxAccessibleList.getChildren().add(createEventCard(eventName, false, folder.getName()));
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Public Events (grantToAll = true)
        File eventsDir = new File("events");
        if (eventsDir.exists()) {
            File[] userFolders = eventsDir.listFiles(File::isDirectory);
            if (userFolders != null) {
                for (File userFolder : userFolders) {
                    String creator = userFolder.getName();
                    if (creator.equalsIgnoreCase(currentUser.getUsername())) continue;

                    File[] eventFiles = userFolder.listFiles((dir, name) -> name.endsWith(".json"));
                    if (eventFiles != null) {
                        for (File eventFile : eventFiles) {
                            try {
                                EventData event = EventStorage.load(eventFile);
                                if (event.grantToAll &&
                                        !currentUser.getCreatedEvents().contains(event.heiti) &&
                                        !currentUser.getAccessibleEvents().contains(event.heiti)) {
                                    accessibleEventCreators.put(event.heiti, creator);
                                    fxAccessibleList.getChildren().add(createEventCard(event.heiti, false, creator));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private HBox createEventCard(String eventName, boolean isCreator, String creatorUsername) {
        HBox card = new HBox(10);
        card.getStyleClass().add("event-card");

        Label nameLabel = new Label(eventName);
        nameLabel.getStyleClass().add("event-name");

        var bundle = AppEnvironment.getInstance().getLanguageManager().getBundle();

        // View button
        Button viewButton = new Button(bundle.getString("event.view"));
        viewButton.getStyleClass().add("event-button");
        viewButton.setOnAction(e -> {
            System.out.println("View: " + eventName);
            try {
                File file = new File("events/" + creatorUsername, eventName + ".json");
                if (file.exists()) {
                    EventData event = EventStorage.load(file);
                    ViewSwitcher.switchTo(View.EVENTVIEWER, event);
                } else {
                    System.err.println("⚠️ File not found: " + file.getAbsolutePath());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        card.getChildren().add(nameLabel);

        if (isCreator) {
            // Edit button
            Button editButton = new Button(bundle.getString("event.edit"));
            editButton.getStyleClass().add("event-button");
            editButton.setOnAction(e -> {
                System.out.println("Edit: " + eventName);
                try {
                    File file = new File("events/" + creatorUsername, eventName + ".json");
                    if (file.exists()) {
                        EventData event = EventStorage.load(file);
                        ViewSwitcher.switchTo(View.EVENTMANAGER, event);
                    } else {
                        System.err.println("⚠️ File not found: " + file.getAbsolutePath());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            card.getChildren().addAll(viewButton, editButton);
        } else {
            card.getChildren().add(viewButton);
        }

        return card;
    }

    @FXML
    private void onLogoutPressed() {
        AppEnvironment.getInstance().setCurrentUser(null);
        ViewSwitcher.switchTo(View.VELKOMINN);
    }

    @FXML
    private void onCreateEvent() {
        System.out.println("User clicked to create a new event.");
        ViewSwitcher.switchTo(View.EVENTMANAGER, new EventData());
    }
}
