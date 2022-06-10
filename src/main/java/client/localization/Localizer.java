package client.localization;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localizer {
    private static final Localizer instance = new Localizer();
    public static Localizer getInstance() {
        return instance;
    }

    private final ObjectProperty<AvailableLocale> availableLocale = new SimpleObjectProperty<>();

    public ObjectProperty<AvailableLocale> availableLocaleProperty() {
        return availableLocale;
    }
    public AvailableLocale getAvailableLocale() {
        return availableLocale.get();
    }
    public Locale getLocale() {
        return getAvailableLocale().getLocale();
    }

    private final ObjectProperty<ResourceBundle> resourceBundle = new SimpleObjectProperty<>();
    public ObjectProperty<ResourceBundle> resourceBundleProperty() {
        return resourceBundle;
    }
    private void setResourceBundle() {
        this.resourceBundle.set(ResourceBundle.getBundle("client.localization.Data", getLocale()));
    }
    public ResourceBundle getResourceBundle() {
        return resourceBundle.get();
    }

    private final ObjectProperty<NumberFormat> numberFormat = new SimpleObjectProperty<>();
    public ObjectProperty<NumberFormat> numberFormatProperty() {
        return numberFormat;
    }
    private void setNumberFormat() {
        this.numberFormat.set(NumberFormat.getNumberInstance(getLocale()));
    }
    public NumberFormat getNumberFormat() {
        return numberFormat.get();
    }

    private final ObjectProperty<DateFormat> shortDateFormat = new SimpleObjectProperty<>();
    public ObjectProperty<DateFormat> shortDateFormatProperty() {
        return shortDateFormat;
    }
    private void setShortDateFormat() {
        this.shortDateFormat.set(DateFormat.getDateInstance(DateFormat.SHORT, getLocale()));
    }
    public DateFormat getShortDateFormat() {
        return shortDateFormat.get();
    }

    private final ObjectProperty<DateFormat> longDateFormat = new SimpleObjectProperty<>();
    public ObjectProperty<DateFormat> longDateFormatProperty() {
        return longDateFormat;
    }
    private void setLongDateFormat() {
        this.longDateFormat.set(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", getLocale()));
    }
    public DateFormat getLongDateFormat() {
        return longDateFormat.get();
    }

    private final ObjectProperty<DateFormat> shortDateTimeFormat = new SimpleObjectProperty<>();
    public ObjectProperty<DateFormat> shortDateTimeFormatProperty() {
        return shortDateTimeFormat;
    }
    private void setShortDateTimeFormat() {
        this.shortDateTimeFormat.set(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, getLocale()));
    }
    public DateFormat getShortDateTimeFormat() {
        return shortDateTimeFormat.get();
    }

    {
        availableLocale.addListener((obs, o, n) -> {
            setResourceBundle();
            setNumberFormat();
            setShortDateFormat();
            setLongDateFormat();
            setShortDateTimeFormat();
        });
        availableLocale.set(AvailableLocale.DEFAULT);
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
