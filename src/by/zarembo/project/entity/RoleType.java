package by.zarembo.project.entity;

import java.util.Arrays;
import java.util.Optional;

public enum RoleType {
    GUEST,
    USER,
    ADMIN;

    public static Optional<RoleType> valueOf(int roleId) {
        return Arrays.stream(values())
                .filter(roleType -> roleType.ordinal() == roleId)
                .findFirst();
    }

}
