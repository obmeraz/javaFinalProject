package by.zarembo.project.filter;

import by.zarembo.project.command.CommandType;
import by.zarembo.project.entity.RoleType;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Role configurator.
 */
class RoleConfigurator {
    private EnumMap<RoleType, Set<CommandType>> rolePrivilegeMap = new EnumMap<>(RoleType.class);

    /**
     * Instantiates a new Role configurator.
     */
    RoleConfigurator() {
        initGuestPrivileges();
        initUserPrivileges();
        initAdminPrivileges();
    }

    private void initGuestPrivileges() {
        Set<CommandType> guestCommands = new HashSet<>();
        guestCommands.add(CommandType.LOG_IN);
        guestCommands.add(CommandType.LOG_OUT);
        guestCommands.add(CommandType.SIGN_UP);
        guestCommands.add(CommandType.LOCALE);
        guestCommands.add(CommandType.CHOOSE_LIFEHACK_CATEGORY);
        guestCommands.add(CommandType.DISPLAY_LIFEHACKS_BY_TYPE);
        guestCommands.add(CommandType.DISPLAY_LIFEHACK_POST);
        guestCommands.add(CommandType.ACTIVATE_ACCOUNT);
        guestCommands.add(CommandType.EMPTY_COMMAND);
        rolePrivilegeMap.put(RoleType.GUEST, guestCommands);
    }

    private void initUserPrivileges() {
        Set<CommandType> userCommands = new HashSet<>();
        userCommands.add(CommandType.LOG_OUT);
        userCommands.add(CommandType.LEAVE_COMMENT);
        userCommands.add(CommandType.LIKE_LIFEHACK);
        userCommands.add(CommandType.LIKE_COMMENT);
        userCommands.add(CommandType.SORT_COMMENTS);
        userCommands.add(CommandType.CHANGE_PASSWORD);
        userCommands.add(CommandType.CHANGE_EMAIL);
        userCommands.add(CommandType.DISPLAY_LIFEHACK_POST);
        userCommands.add(CommandType.DISPLAY_LIFEHACKS_BY_TYPE);
        userCommands.add(CommandType.CHOOSE_LIFEHACK_CATEGORY);
        userCommands.add(CommandType.EMPTY_COMMAND);
        userCommands.add(CommandType.LOCALE);
        rolePrivilegeMap.put(RoleType.USER, userCommands);
    }

    private void initAdminPrivileges() {
        Set<CommandType> adminCommands = new HashSet<>();
        adminCommands.add(CommandType.LOG_OUT);
        adminCommands.add(CommandType.CREATE_LIFEHACK);
        adminCommands.add(CommandType.LEAVE_COMMENT);
        adminCommands.add(CommandType.LIKE_LIFEHACK);
        adminCommands.add(CommandType.LIKE_COMMENT);
        adminCommands.add(CommandType.SORT_COMMENTS);
        adminCommands.add(CommandType.EDIT_LIFEHACK_NAME);
        adminCommands.add(CommandType.EDIT_LIFEHACK_CONTENT);
        adminCommands.add(CommandType.EDIT_LIFEHACK_EXCERPT);
        adminCommands.add(CommandType.EDIT_LIFEHACK_CATEGORY);
        adminCommands.add(CommandType.CHANGE_PASSWORD);
        adminCommands.add(CommandType.CHANGE_EMAIL);
        adminCommands.add(CommandType.MAKE_USER_ADMIN);
        adminCommands.add(CommandType.REMOVE_USER_ADMIN);
        adminCommands.add(CommandType.DELETE_COMMENT);
        adminCommands.add(CommandType.DISPLAY_USERS_LIST);
        adminCommands.add(CommandType.DISPLAY_LIFEHACKS_LIST);
        adminCommands.add(CommandType.DISPLAY_LIFEHACK_POST);
        adminCommands.add(CommandType.DISPLAY_LIFEHACKS_BY_TYPE);
        adminCommands.add(CommandType.DELETE_LIFEHACK);
        adminCommands.add(CommandType.DELETE_USER);
        adminCommands.add(CommandType.CHOOSE_LIFEHACK_CATEGORY);
        adminCommands.add(CommandType.LOCALE);
        adminCommands.add(CommandType.EMPTY_COMMAND);
        adminCommands.add(CommandType.EDIT_LIFEHACK);
        rolePrivilegeMap.put(RoleType.ADMIN, adminCommands);
    }

    /**
     * Check role privileges boolean.
     *
     * @param userRole    the user role
     * @param commandType the command type
     * @return the boolean
     */
    boolean checkRolePrivileges(RoleType userRole, CommandType commandType) {
        Set<CommandType> commandTypeList = rolePrivilegeMap.get(userRole);
        return commandTypeList.stream().anyMatch(commandType::equals);
    }

    /**
     * Check command name boolean.
     *
     * @param commandName the command name
     * @return the boolean
     */
    boolean checkCommandName(String commandName) {
        return Arrays.stream(CommandType.values())
                .anyMatch(commandType -> commandType.toString().equals(commandName.toUpperCase()));
    }
}
