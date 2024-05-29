package internship.java.java_internship.models;

import internship.java.java_internship.exceptions.FromStringException;

import java.util.Arrays;
import java.util.List;

public enum CatColor {
    GREY,
    BLACK,
    GINGER;

    public static CatColor fromString(String catColorString) throws FromStringException {
        return switch(catColorString.toLowerCase()) {
            case "grey" -> GREY;
            case "black" -> BLACK;
            case "ginger" -> GINGER;
            default -> throw new FromStringException();
        };
    }

    public static String allColors() {
        return Arrays.stream(CatColor.values())
                .map(c -> "`" + c.toString() + "` ")
                .reduce("", (str, c) -> {str += c; return str;});
    }

    @Override
    public String toString() {
        return switch (this) {
            case GREY -> "grey";
            case BLACK -> "black";
            case GINGER -> "ginger";
        };
    }
}
