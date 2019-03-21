package by.zarembo.project.command.impl.user;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.Comment;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.CommentService;
import by.zarembo.project.util.CommentValidator;
import by.zarembo.project.util.LifeHackValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Leave comment command.
 */
public class LeaveCommentCommand implements Command {
    private static final String COMMENT_CONTENT = "comment_content";
    private static final String ERROR_MESSAGES = "errorMessages";
    private CommentService commentService = new CommentService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String commentContent = request.getParameter(COMMENT_CONTENT);
        Router router = new Router();
        User user = (User) request.getSession().getAttribute(CommandConstant.USER);
        String id = request.getParameter(CommandConstant.LIFEHACK_ID);
        if (!LifeHackValidator.validateId(id)) {
            request.getSession().setAttribute(CommandConstant.MESSAGE, "Doesn't exist");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
        }
        long lifeHackId = Long.parseLong(id);
        String path = String.valueOf(request.getSession().getAttribute(CommandConstant.CURRENT_PAGE));
        List<String> errorMessages = new ArrayList<>();
        if (CommentValidator.validate(commentContent, errorMessages)) {
            try {
                Comment comment = commentService.buildComment(commentContent, user, lifeHackId);
                commentService.addNewComment(comment);
                router.setPagePath(path);
                router.setRedirectRoute();
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            request.getSession().setAttribute(ERROR_MESSAGES, errorMessages);
            router.setRedirectRoute();
            router.setPagePath(path);
        }
        return router;
    }
}
