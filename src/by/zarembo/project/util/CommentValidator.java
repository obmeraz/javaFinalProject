package by.zarembo.project.util;

import by.zarembo.project.entity.Comment;

import java.util.List;

public class CommentValidator {
    private static final String REGEX_COMMENT_CONTENT = "^[а-яА-ЯёЁa-zA-Z\\d\\W]{1,255}$";

    public static boolean validate(Comment comment, List<String> errorMessages) {
        boolean isCorrect = true;
        String content = comment.getContent();
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
