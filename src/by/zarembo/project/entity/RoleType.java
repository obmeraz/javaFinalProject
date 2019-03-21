package by.zarembo.project.entity;

import java.util.Arrays;
import java.util.Optional;

/**
 * The enum Role type.
 */
public enum RoleType {
    /**
     * Guest role type.
     */
    GUEST,
    /**
     * User role type.
     */
    USER,
    /**
     * Admin role type.
     */
    ADMIN;

    /**
     * Value of make from string enum type optional.
     *
     * @param roleId the role id
     * @return the optional
     */
    public static Optional<RoleType> valueOf(int roleId) {
        return Arrays.stream(values())
                .filter(roleType -> roleType.ordinal() == roleId)
                .findFirst();
    }

}
