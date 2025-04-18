package hi.verkefni.vidmot.view;

import hi.verkefni.vinnsla.helpers.*;
import hi.verkefni.vinnsla.data.StartViewData;
import hi.verkefni.vinnsla.UserManager;
import hi.verkefni.vinnsla.UserProfile;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class VelkominnView implements SupportsDataInjection {

    @FXML
    private TextField fxUsernameField;

    @Override
    public void injectData(Object data) {
        if (data instanceof StartViewData svd) {
            fxUsernameField.setText(svd.username);
        }
    }

    @FXML
    private void onLoginPressed() {
        String username = fxUsernameField.getText();
        if (username == null || username.isBlank()) {
            System.out.println("Notandanafn vantar!");
            return;
        }

        UserProfile user = UserManager.findUser(username);
        if (user == null) {
            System.out.println("Notandi fannst ekki.");
            return;
        }

        // ✅ Set the current user globally
        AppEnvironment.getInstance().setCurrentUser(user);

        // 🚀 Switch to the user's profile or main dashboard view
        ViewSwitcher.switchTo(View.PROFILE, user);
    }

    @FXML
    private void onSignupPressed() {
        String username = fxUsernameField.getText();
        if (username == null || username.isBlank()) {
            System.out.println("Sláðu inn notandanafn!");
            return;
        }

        boolean success = UserManager.addUser(username);
        if (success) {
            System.out.println("Nýr notandi búinn til: " + username);

            // ✅ Auto-login: retrieve and set the newly created user
            UserProfile user = UserManager.findUser(username);
            AppEnvironment.getInstance().setCurrentUser(user);

            // 🚀 Show quick start view with welcome
            ViewSwitcher.switchTo(View.QUICKSTART, user);
        } else {
            System.out.println("Notandanafn er þegar í notkun.");
        }
    }


    public String getUsername() {
        return fxUsernameField.getText();
    }

}
