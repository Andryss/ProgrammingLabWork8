package general.element;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Part of the Movie class
 * @see Movie
 */
public class Person implements Serializable, Cloneable {
    /**
     * Name of screenwriter (can't be null, String can't be empty)
     */
    private String name;
    /**
     * Birthday of screenwriter (can be null, must have "DD.MM.YYYY" format)
     */
    private Date birthday;
    /**
     * Hair color of screenwriter (can be null)
     * @see Color
     */
    private Color hairColor;

    public Person() {}

    /**
     * Parse name from string and set
     * @throws FieldException if string is incorrect
     */
    @FieldSetter(fieldName = "name", example = "for example \"James Francis Cameron\"", index = 7)
    public void setName(String name) throws FieldException {
        if (name == null || name.equals("null") || name.length() == 0) {
            throw new FieldException("field can't be null, String can't be empty");
        }
        if (name.length() > 20) {
            throw new FieldException("name must have less than 20 characters");
        }
        this.name = name;
    }

    private static final DateTimeFormatter birthdayFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Parse birthday (in format "DD.MM.YYYY") from string and set
     * @throws FieldException if string is incorrect
     */
    @SuppressWarnings({"MagicConstant", "deprecation"})
    @FieldSetter(fieldName = "birthday", example = "for example \"16.08.1954\"", index = 8)
    public void setBirthday(String birthday) throws FieldException {
        if (birthday == null || birthday.equals("null") || birthday.length() == 0) {
            this.birthday = null;
            return;
        }
        try {
            LocalDate date = LocalDate.parse(birthday, birthdayFormatter);
            if (date.getYear() < 1900) {
                throw new FieldException("year must be at least 1900 (vampire-screenwriters is not supported)");
            }
            this.birthday = new Date(date.getYear() - 1900, date.getMonthValue() -1, date.getDayOfMonth());
        } catch (DateTimeParseException e) {
            throw new FieldException("field must have \"DD.MM.YYYY\" format");
        }
    }

    /**
     * Parse hair color from string and set
     * @throws FieldException if string is incorrect
     * @see Color
     */
    @FieldSetter(fieldName = "hairColor", example = "it must be one of: [RED, BLACK, BLUE, WHITE, BROWN]", index = 9)
    public void setHairColor(String hairColor) throws FieldException {
        if (hairColor == null || hairColor.equals("null") || hairColor.length() == 0) {
            this.hairColor = null;
            return;
        }
        try {
            this.hairColor = Color.valueOf(hairColor.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new FieldException("value must be one of: " + Arrays.toString(Color.values()));
        }
    }

    public String getName() {
        return name;
    }

    /**
     * @return Date object of birthday
     * @see Date
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @return date in "DD.MM.YYYY" format using Date object
     * @see Date
     */
    @SuppressWarnings("deprecation")
    public String getBirthdayString() {
        if (birthday == null) {
            return "";
        }
        int day = birthday.getDate();
        int month = birthday.getMonth() + 1;
        int year = birthday.getYear() + 1900;
        return "" + day / 10 + day % 10 + "." +
                month / 10 + month % 10 + "." +
                year / 1000 + year / 100 % 10 + year / 10 % 10 + year % 10;
    }

    public Color getHairColor() {
        return hairColor;
    }

    /**
     * Enum with possible hair colors
     */
    public enum Color {
        RED,
        BLACK,
        BLUE,
        WHITE,
        BROWN
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + getBirthdayString() +
                ", hairColor=" + hairColor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) &&
                getBirthdayString().equals(person.getBirthdayString()) &&
                hairColor == person.hairColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, hairColor);
    }

    @Override
    protected Person clone() {
        Person clone = new Person();
        clone.name = name;
        clone.birthday = birthday;
        clone.hairColor = hairColor;
        return clone;
    }
}
