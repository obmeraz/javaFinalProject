package by.zarembo.project.command.impl.user;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;
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

public class LeaveCommentCommand implements Command {
    private static final String COMMENT_CONTENT = "comment_content";
    private static final String ERROR_MESSAGES = "errorMessages";
    private CommentService commentService = new CommentService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String commentContent = request.getParameter(COMMENT_CONTENT);
        Router router = new Router();
        User user = (User) request.getSession().getAttribute(AttributeConstant.USER);
        String id = request.getParameter(AttributeConstant.LIFEHACK_ID);
        if (!LifeHackValidator.validateId(id)) {
            request.getSession().setAttribute(AttributeConstant.MESSAGE, "Doesn't exist");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
        }
        long lifeHackId = Long.parseLong(id);
        String path = String.valueOf(request.getSession().getAttribute(AttributeConstant.CURRENT_PAGE));
        Comment comment = commentService.buildComment(commentContent, user, lifeHackId);
        List<String> errorMessages = new ArrayList<>();
        if (CommentValidator.validate(comment, errorMessages)) {
            try {
                commentService.addNewComment(comment);
                router.setPagePath(path);
                router.setRedirectRoute();
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            request.setAttribute(ERROR_MESSAGES, errorMessages);
            router.setPagePath(path);
        }
        return router;
    }
}
