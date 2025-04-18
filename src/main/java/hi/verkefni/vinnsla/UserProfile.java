package hi.verkefni.vinnsla;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private String username;
    private List<String> createdEvents = new ArrayList<>();
    private List<String> accessibleEvents = new ArrayList<>();

    public UserProfile() {}

    public UserProfile(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public List<String> getCreatedEvents() {
        return createdEvents;
    }
    public void setCreatedEvents(List<String> createdEvents) {
        this.createdEvents = createdEvents;
    }
    public List<String> getAccessibleEvents() {
        return accessibleEvents;
    }
    public void setAccessibleEvents(List<String> accessibleEvents) {
        this.accessibleEvents = accessibleEvents;
    }
}
