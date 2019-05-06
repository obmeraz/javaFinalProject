package by.zarembo.project.command;

import by.zarembo.project.command.impl.EmptyCommand;
import by.zarembo.project.command.impl.admin.*;
import by.zarembo.project.command.impl.common.*;
import by.zarembo.project.command.impl.guest.ActivateAccountCommand;
import by.zarembo.project.command.impl.guest.LogInCommand;
import by.zarembo.project.command.impl.guest.SignUpCommand;
import by.zarembo.project.command.impl.user.*;

/**
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The Log in.
     */
    LOG_IN(new LogInCommand()),
    /**
     * The Log out.
     */
    LOG_OUT(new LogOutCommand()),
    /**
     * The Sign up.
     */
    SIGN_UP(new SignUpCommand()),
    /**
     * The Activate account.
     */
    ACTIVATE_ACCOUNT(new ActivateAccountCommand()),
    /**
     * The Create lifehack.
     */
    CREATE_LIFEHACK(new CreateLifeHackCommand()),
    /**
     * The Leave comment.
     */
    LEAVE_COMMENT(new LeaveCommentCommand()),
    /**
     * The Like lifehack.
     */
    LIKE_LIFEHACK(new LikeLifeHackCommand()),
    /**
     * The Like comment.
     */
    LIKE_COMMENT(new LikeCommentCommand()),
    /**
     * The Sort comments.
     */
    SORT_COMMENTS(new SortCommentsCommand()),
    /**
     * The Change password.
     */
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    /**
     * The Change email.
     */
    CHANGE_EMAIL(new ChangeEmailCommand()),
    /**
     * The Edit lifehack.
     */
    EDIT_LIFEHACK(new EditLifeHackCommand()),
    /**
     * The Edit lifehack name.
     */
    EDIT_LIFEHACK_NAME(new EditLifeHackNameCommand()),
    /**
     * The Edit lifehack content.
     */
    EDIT_LIFEHACK_CONTENT(new EditLifeHackContentCommand()),
    /**
     * The Edit lifehack excerpt.
     */
    EDIT_LIFEHACK_EXCERPT(new EditLifeHackExcerptCommand()),
    /**
     * The Edit lifehack category.
     */
    EDIT_LIFEHACK_CATEGORY(new EditLifeHackCategory()),
    /**
     * The Make user admin.
     */
    MAKE_USER_ADMIN(new MakeAdminCommand()),
    /**
     * The Remove user admin.
     */
    REMOVE_USER_ADMIN(new RemoveAdminCommand()),
    /**
     * The Delete comment.
     */
    DELETE_COMMENT(new DeleteCommentCommand()),
    /**
     * The Display users list.
     */
    DISPLAY_USERS_LIST(new DisplayUsersListCommand()),
    /**
     * The Display lifehack post.
     */
    DISPLAY_LIFEHACK_POST(new DisplayLifeHackPostCommand()),
    /**
     * The Display lifehacks list.
     */
    DISPLAY_LIFEHACKS_LIST(new DisplayLifeHacksListCommand()),
    /**
     * The Display lifehacks by type.
     */
    DISPLAY_LIFEHACKS_BY_TYPE(new DisplayLifeHacksByTypeCommand()),
    /**
     * The Delete lifehack.
     */
    DELETE_LIFEHACK(new DeleteLifeHackCommand()),
    /**
     * The Delete user.
     */
    DELETE_USER(new DeleteUserCommand()),
    /**
     * The Choose lifehack category.
     */
    CHOOSE_LIFEHACK_CATEGORY(new ChooseLifeHackCategoryCommand()),
    /**
     * The Empty command.
     */
    EMPTY_COMMAND(new EmptyCommand()),

    SEARCH_LIFEHACK(new FindLifeHacksCommand()),
    EXPORT_TO_JSON(new LifeHackJsonExportCommand()),
    IMPORT_FROM_JSON(new LifeJackImportFromJsonCommand()),
    /**
     * The Locale.
     */
    LOCALE(new LocaleCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }
}
