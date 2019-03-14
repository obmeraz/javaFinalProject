package by.zarembo.project.entity;

import java.util.Arrays;
import java.util.Optional;

public enum CategoryType {
    SPORT,
    EDUCATION,
    LIFE,
    CINEMA,
    TECHNOLOGIES,
    MOTIVATION,
    FOOD,
    NEWS;

    public static Optional<CategoryType> valueOf(int categoryId) {
        return Arrays.stream(values())
                .filter(categoryType -> categoryType.ordinal() == categoryId)
                .findFirst();
    }
}
