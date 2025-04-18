package hi.verkefni.vinnsla.helpers;

import java.util.Locale;
import java.util.ResourceBundle;


public class LanguageManager {
    private static Locale currentLocale = new Locale("is");
    private static final String BUNDLE_BASE = "hi.verkefni.vidmot.lang";

    public static void setLocale(Locale locale) {
        currentLocale = locale;
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    public static ResourceBundle getBundle() {
        return ResourceBundle.getBundle(BUNDLE_BASE, currentLocale);
    }
}
