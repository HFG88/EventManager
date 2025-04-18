package hi.verkefni.vidmot.view;

import hi.verkefni.vidmot.components.EventEditor;
import hi.verkefni.vinnsla.UserManager;
import hi.verkefni.vinnsla.UserProfile;
import hi.verkefni.vinnsla.data.EventData;
import hi.verkefni.vinnsla.data.StartViewData;
import hi.verkefni.vinnsla.helpers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EventManagerView implements SupportsDataInjection {

    @FXML
    private MenuBar fxMenu;


    @FXML
    private EventEditor fxEventView;


    @FXML
    private Label fxWelcomeLabel;

    private UserProfile currentUser;

    @FXML
    private CheckBox fxGrantToAll;

    @FXML
    private VBox fxAccessUserList;


    @Override
    public void injectData(Object data) {
        AppEnvironment.getInstance().setCurrentController(this); // ✅ This line is key

        if (data instanceof UserProfile user) {
            this.currentUser = user;
            AppEnvironment.getInstance().setCurrentUser(user);
            fxWelcomeLabel.setText(String.format("%s, %s!", getGreeting(), user.getUsername()));
        } else if (data instanceof EventData event) {
            fxEventView.fillWith(event);

            // ✅ Populate access checkboxes
            fxAccessUserList.getChildren().clear();
            fxGrantToAll.setSelected(event.grantToAll);

            for (UserProfile u : UserManager.getAllUsers()) {
                // Don't include the current user (creator)
                if (!u.getUsername().equalsIgnoreCase(AppEnvironment.getInstance().getCurrentUser().getUsername())) {
                    CheckBox cb = new CheckBox(u.getUsername());

                    // Pre-check if this user is already in the access list
                    if (event.accessList != null && event.accessList.contains(u.getUsername())) {
                        cb.setSelected(true);
                    }

                    fxAccessUserList.getChildren().add(cb);
                }
            }
        }
    }



    private String getGreeting() {
        return switch (Locale.getDefault().getLanguage()) {
            case "is" -> "Velkomin(n)";
            default -> "Welcome";
        };
    }

    @FXML
    private void onLogoutPressed(ActionEvent e) {
        AppEnvironment.getInstance().setCurrentUser(null);
        ViewSwitcher.switchTo(View.VELKOMINN, new StartViewData());
    }

    // Menu methods
    public void nyr() {}
    public void vista() {
        EventData data = fxEventView.extractEventData();

        UserProfile user = AppEnvironment.getInstance().getCurrentUser();
        if (user == null || data.heiti == null || data.heiti.isBlank()) return;

        // Save to file
        File userFolder = new File("events", user.getUsername());
        if (!userFolder.exists()) userFolder.mkdirs();

        String fileName = data.heiti.replaceAll("\\s+", "_") + ".json";
        File saveFile = new File(userFolder, fileName);

        try {
            // Save event data
            JsonUtil.writeToJson(saveFile, data);
            System.out.println("✅ Event saved to: " + saveFile.getAbsolutePath());

            // Add to creator’s profile if new
            if (!user.getCreatedEvents().contains(data.heiti)) {
                user.getCreatedEvents().add(data.heiti);
            }

            // Access control logic
            data.accessList.clear();
            if (!fxGrantToAll.isSelected()) {
                for (Node node : fxAccessUserList.getChildren()) {
                    if (node instanceof CheckBox cb && cb.isSelected()) {
                        data.accessList.add(cb.getText());
                    }
                }

                // Update each selected user's profile
                for (String username : data.accessList) {
                    UserProfile target = UserManager.findUser(username);
                    if (target != null && !target.getAccessibleEvents().contains(data.heiti)) {
                        target.getAccessibleEvents().add(data.heiti);
                        UserManager.saveUserProfile(target);
                    }
                }
            }

            data.grantToAll = fxGrantToAll.isSelected();
            UserManager.saveUserProfile(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void vistaSem() {}

    public void opna() {
        UserProfile user = AppEnvironment.getInstance().getCurrentUser();
        if (user == null) return;

        File userFolder = new File("events", user.getUsername());
        if (!userFolder.exists() || !userFolder.isDirectory()) {
            System.out.println("No saved events for this user.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Opna viðburð");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        fileChooser.setInitialDirectory(userFolder);

        File selectedFile = fileChooser.showOpenDialog(null); // null = current active window
        if (selectedFile != null) {
            try {
                EventData data = JsonUtil.readFromJson(selectedFile, EventData.class);

                // Switch back to the same view and inject the data
                ViewSwitcher.switchTo(View.EVENTMANAGER, data);

            } catch (IOException e) {
                e.printStackTrace(); // or show error popup
            }
        }
    }


    public void um() {}
    public void haetta() {
        System.exit(0);
    }
    @FXML
    public void onBreyta() {
        // Handle editing logic here
        System.out.println("Breyta clicked!");
    }


    public void changeLanguage(Locale next) {}
    @FXML
    private void onBackToProfile() {
        var user = AppEnvironment.getInstance().getCurrentUser();
        if (user != null) {
            ViewSwitcher.switchTo(View.PROFILE, user);
        }
    }

    @FXML
    private void onViewOnly() {
        EventData data = fxEventView.extractEventData();
        if (data != null) {
            ViewSwitcher.switchTo(View.EVENTVIEWER, data);
        }
    }
}
