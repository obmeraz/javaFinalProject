package by.zarembo.project.util;

import java.util.List;

/**
 * The type Comment validator.
 */
public class CommentValidator {
    private static final String REGEX_COMMENT_CONTENT = "^[а-яА-ЯёЁa-zA-Z\\d\\W]{1,255}$";

    /**
     * Validate boolean.
     *
     * @param content       the content
     * @param errorMessages the error messages
     * @return the boolean
     */
    public static boolean validate(String content, List<String> errorMessages) {
        boolean isCorrect = true;
        if (content == null || content.isEmpty()) {
            errorMessages.add("Fill the empty element");
            return false;
        }
        if (!content.matches(REGEX_COMMENT_CONTENT)) {
            errorMessages.add("Incorrect content");
            isCorrect = false;
        }
        return isCorrect;
    }
}
