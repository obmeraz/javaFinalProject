package by.zarembo.project.entity;

import java.util.Arrays;
import java.util.Optional;

/**
 * The enum Category type.
 */
public enum CategoryType {
    /**
     * Sport category type.
     */
    SPORT,
    /**
     * Education category type.
     */
    EDUCATION,
    /**
     * Life category type.
     */
    LIFE,
    /**
     * Cinema category type.
     */
    CINEMA,
    /**
     * Technologies category type.
     */
    TECHNOLOGIES,
    /**
     * Motivation category type.
     */
    MOTIVATION,
    /**
     * Food category type.
     */
    FOOD,
    /**
     * News category type.
     */
    NEWS;

    /**
     * Value of make from int enum type optional.
     *
     * @param categoryId the category id
     * @return the optional
     */
    public static Optional<CategoryType> valueOf(int categoryId) {
        return Arrays.stream(values())
                .filter(categoryType -> categoryType.ordinal() == categoryId)
                .findFirst();
    }
}
