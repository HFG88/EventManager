package hi.verkefni.vinnsla.helpers;

import hi.verkefni.vidmot.view.EventManagerView;
import hi.verkefni.vidmot.components.KynningController;
import hi.verkefni.vidmot.components.MenuController;
import hi.verkefni.vinnsla.UserProfile;

public class AppEnvironment {
    private static AppEnvironment instance;
    private View currentView;
    private Object currentViewData;
    private Object currentController;
    private UserProfile currentUser;

    public UserProfile getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserProfile user) {
        this.currentUser = user;
    }


    public Object getCurrentController() {
        return currentController;
    }

    public void setCurrentController(Object currentController) {
        this.currentController = currentController;
    }


    private final EventManagerView eventManagerView;
    private final MenuController menuController;
    private final KynningController kynningController;
    private final LanguageManager languageManager;

    public AppEnvironment(EventManagerView emc, MenuController mc, KynningController kc, LanguageManager langMgr) {
        this.eventManagerView = emc;
        this.menuController = mc;
        this.kynningController = kc;
        this.languageManager = langMgr;
    }

    public static void setInstance(AppEnvironment env) {
        instance = env;
    }

    public static AppEnvironment getInstance() {
        return instance;
    }

    public EventManagerView getEventManagerController() {
        return eventManagerView;
    }

    public MenuController getMenuController() {
        return menuController;
    }

    public KynningController getKynningController() {
        return kynningController;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View currentView) {
        this.currentView = currentView;
    }

    public Object getCurrentViewData() {
        return currentViewData;
    }

    public void setCurrentViewData(Object currentViewData) {
        this.currentViewData = currentViewData;
    }

}
