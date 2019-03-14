package by.zarembo.project.command;

import by.zarembo.project.command.impl.EmptyCommand;
import by.zarembo.project.command.impl.admin.*;
import by.zarembo.project.command.impl.common.*;
import by.zarembo.project.command.impl.guest.ActivateAccountCommand;
import by.zarembo.project.command.impl.guest.LogInCommand;
import by.zarembo.project.command.impl.guest.SignUpCommand;
import by.zarembo.project.command.impl.user.*;

public enum CommandType {
    LOG_IN(new LogInCommand()),
    LOG_OUT(new LogOutCommand()),
    SIGN_UP(new SignUpCommand()),
    ACTIVATE_ACCOUNT(new ActivateAccountCommand()),
    CREATE_LIFEHACK(new CreateLifeHackCommand()),
    LEAVE_COMMENT(new LeaveCommentCommand()),
    LIKE_LIFEHACK(new LikeLifeHackCommand()),
    LIKE_COMMENT(new LikeCommentCommand()),
    SORT_COMMENTS(new SortCommentsCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    CHANGE_EMAIL(new ChangeEmailCommand()),
    EDIT_LIFEHACK(new EditLifeHackCommand()),
    EDIT_LIFEHACK_NAME(new EditLifeHackNameCommand()),
    EDIT_LIFEHACK_CONTENT(new EditLifeHackContentCommand()),
    EDIT_LIFEHACK_EXCERPT(new EditLifeHackExcerptCommand()),
    EDIT_LIFEHACK_CATEGORY(new EditLifeHackCategory()),
    MAKE_USER_ADMIN(new MakeAdminCommand()),
    REMOVE_USER_ADMIN(new RemoveAdminCommand()),
    DELETE_COMMENT(new DeleteCommentCommand()),
    DISPLAY_USERS_LIST(new DisplayUsersListCommand()),
    DISPLAY_LIFEHACK_POST(new DisplayLifeHackPostCommand()),
    DISPLAY_LIFEHACKS_LIST(new DisplayLifeHacksListCommand()),
    DISPLAY_LIFEHACKS_BY_TYPE(new DisplayLifeHacksByTypeCommand()),
    DELETE_LIFEHACK(new DeleteLifeHackCommand()),
    DELETE_USER(new DeleteUserCommand()),
    CHOOSE_LIFEHACK_CATEGORY(new ChooseLifeHackCategoryCommand()),
    EMPTY_COMMAND(new EmptyCommand()),
    LOCALE(new LocaleCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
