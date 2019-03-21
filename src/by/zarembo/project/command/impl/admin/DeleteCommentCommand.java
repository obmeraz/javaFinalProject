package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.CommentService;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Delete comment command.
 */
public class DeleteCommentCommand implements Command {
    private static final String PARAM_COMMENT_ID = "comment_id";
    private CommentService commentService = new CommentService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String path = String.valueOf(request.getSession().getAttribute(CommandConstant.CURRENT_PAGE));
        String id = request.getParameter(PARAM_COMMENT_ID);
        if (id == null || id.isEmpty()) {
            request.getSession().setAttribute(CommandConstant.MESSAGE, "Can't find");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
            return router;
        }
        try {
            long commentId = Long.parseLong(id);
            commentService.deleteComment(commentId);
            router.setPagePath(path);
            router.setRedirectRoute();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
