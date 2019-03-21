package by.zarembo.project.util;

import by.zarembo.project.entity.CategoryType;

import javax.servlet.http.Part;
import java.util.Arrays;
import java.util.List;

/**
 * The type Life hack validator.
 */
public class LifeHackValidator {

    private static final String REGEX_LIFEHACK_NAME = "^[а-яА-ЯёЁa-zA-Z\\d\\s\\W]{5,100}$";
    private static final String REGEX_LIFEHACK_EXCERPT = "^[а-яА-ЯёЁa-zA-Z\\d\\s\\W]{5,60}$";
    private static final String REGEX_LIFEHACK_CONTENT = "^[а-яА-ЯёЁa-zA-Z\\d\\s\\W\\t]{5,16777215}$";
    private static final String REGEX_LIFEHACK_ID = "^[0-9]+$";


    /**
     * Validate boolean.
     *
     * @param category      the category
     * @param name          the name
     * @param content       the content
     * @param excerpt       the excerpt
     * @param filepart      the filepart
     * @param errorMessages the error messages
     * @return the boolean
     */
    public static boolean validate(String category, String name, String content,
                                   String excerpt, Part filepart, List<String> errorMessages) {

        if (name == null || name.isEmpty() || content == null
                || content.isEmpty() || excerpt == null || excerpt.isEmpty()
                || category == null || category.isEmpty()
        ) {
            errorMessages.add("Fill the empty elements");
            return false;
        }
        boolean isCorrect = true;

        if (filepart.getSubmittedFileName().isEmpty()) {
            errorMessages.add("Load image");
            isCorrect = false;
        }

        if (!validateName(name)) {
            errorMessages.add("Invalid lifehack name");
            isCorrect = false;
        }
        if (!validateContent(content)) {
            errorMessages.add("Invalid lifehack content");
            isCorrect = false;
        }
        if (!validateExcerpt(excerpt)) {
            errorMessages.add("Invalid lifehack excerpt");
            isCorrect = false;
        }

        if (!validateCategory(category)) {
            errorMessages.add("Invalid lifehack category");
            isCorrect = false;
        }
        return isCorrect;
    }

    /**
     * Validate name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean validateName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.matches(REGEX_LIFEHACK_NAME);
    }

    /**
     * Validate excerpt boolean.
     *
     * @param excerpt the excerpt
     * @return the boolean
     */
    public static boolean validateExcerpt(String excerpt) {
        if (excerpt == null || excerpt.isEmpty()) {
            return false;
        }
        return excerpt.matches(REGEX_LIFEHACK_EXCERPT);
    }

    /**
     * Validate category boolean.
     *
     * @param category the category
     * @return the boolean
     */
    public static boolean validateCategory(String category) {
        if (category == null || category.isEmpty()) {
            return false;
        }
        return Arrays.stream(CategoryType.values())
                .anyMatch(categoryType -> categoryType.toString().equals(category.toUpperCase()));
    }

    /**
     * Validate content boolean.
     *
     * @param content the content
     * @return the boolean
     */
    public static boolean validateContent(String content) {
        if (content == null || content.isEmpty()) {
            return false;
        }
        return content.matches(REGEX_LIFEHACK_CONTENT);
    }

    /**
     * Validate id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    public static boolean validateId(String id) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        return id.matches(REGEX_LIFEHACK_ID);
    }
}
