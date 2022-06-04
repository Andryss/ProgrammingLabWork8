package client.localization;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizedData {
    private static final LocalizedData instance = new LocalizedData();
    public static LocalizedData getInstance() {
        return instance;
    }

    private final ObjectProperty<AvailableLocale> availableLocale = new SimpleObjectProperty<>();

    public ObjectProperty<AvailableLocale> availableLocaleProperty() {
        return availableLocale;
    }
    public void setAvailableLocale(AvailableLocale availableLocale) {
        this.availableLocale.set(availableLocale);
    }
    public AvailableLocale getAvailableLocale() {
        return availableLocale.get();
    }
    public Locale getLocale() {
        return getAvailableLocale().getLocale();
    }

    private final ObjectProperty<ResourceBundle> resourceBundle = new SimpleObjectProperty<>();
    {
        availableLocale.addListener((obs, o, n) -> setResourceBundle());
        availableLocale.set(AvailableLocale.DEFAULT);
    }

    public ObjectProperty<ResourceBundle> resourceBundleProperty() {
        return resourceBundle;
    }
    private void setResourceBundle() {
        this.resourceBundle.set(ResourceBundle.getBundle("client.localization.Data", getLocale()));
    }
    public ResourceBundle getResourceBundle() {
        return resourceBundle.get();
    }

    public enum AvailableLocale {
        DEFAULT("English", Locale.ROOT),
        RUSSIAN("Русский", new Locale("ru")),
        GERMAN("Deutsch", Locale.GERMAN),
        CATALAN("Català", new Locale("ca")),
        SPANISH_PANAMA("Español (panamá)", new Locale("es"));

        private final String name;
        private final Locale locale;

        AvailableLocale(String name, Locale locale) {
            this.name = name;
            this.locale = locale;
        }

        public String getName() {
            return name;
        }
        public Locale getLocale() {
            return locale;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
