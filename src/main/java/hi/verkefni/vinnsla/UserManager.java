// 1. Update UserManager.java to support per-user files

package hi.verkefni.vinnsla;

import hi.verkefni.vinnsla.helpers.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManager {
    private static final File USER_INDEX_FILE = new File("users.json");
    private static final File PROFILE_FOLDER = new File("profiles");

    // list of usernames only
    private static List<String> usernames = loadUserIndex();

    static {
        if (!PROFILE_FOLDER.exists()) {
            PROFILE_FOLDER.mkdirs();
        }
    }

    private static List<String> loadUserIndex() {
        try {
            if (USER_INDEX_FILE.exists()) {
                return new ArrayList<>(List.of(JsonUtil.readFromJson(USER_INDEX_FILE, String[].class)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    private static void saveUserIndex() {
        try {
            JsonUtil.writeToJson(USER_INDEX_FILE, usernames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean usernameExists(String username) {
        return usernames.contains(username.toLowerCase());
    }

    public static boolean addUser(String username) {
        String cleanUsername = username.toLowerCase();
        if (usernameExists(cleanUsername)) return false;

        usernames.add(cleanUsername);
        saveUserIndex();

        UserProfile profile = new UserProfile(cleanUsername);
        saveUserProfile(profile);
        return true;
    }

    public static void saveUserProfile(UserProfile profile) {
        File userFile = new File(PROFILE_FOLDER, profile.getUsername() + ".json");
        try {
            JsonUtil.writeToJson(userFile, profile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserProfile findUser(String username) {
        File userFile = new File(PROFILE_FOLDER, username.toLowerCase() + ".json");
        try {
            if (userFile.exists()) {
                return JsonUtil.readFromJson(userFile, UserProfile.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<UserProfile> getAllUsers() {
        return usernames.stream()
                .map(UserManager::findUser)
                .filter(u -> u != null)
                .collect(Collectors.toList());
    }
}
