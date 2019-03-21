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

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The type Like comment command.
 */
public class LikeCommentCommand implements Command {
    private static final String COMMENT_ID = "comment_id";
    private CommentService commentService = new CommentService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Comment comment;
        Router router = new Router();
        User user = (User) request.getSession().getAttribute(CommandConstant.USER);
        String path = String.valueOf(request.getSession().getAttribute(CommandConstant.CURRENT_PAGE));
        String id = request.getParameter(COMMENT_ID);
        if (id == null || id.isEmpty()) {
            request.getSession().setAttribute(CommandConstant.MESSAGE, "Invalid id");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
            return router;
        }
        long commentId = Long.parseLong(id);

        try {
            if (!commentService.isCommentLikedAlready(commentId, user.getUserId())) {
                commentService.likeComment(commentId);
                commentService.insertUserLikeComment(commentId, user.getUserId());
            } else {
                commentService.removeLikeComment(commentId);
                commentService.deleteUserLikeComment(commentId, user.getUserId());
            }
            Optional<Comment> commentOptional = commentService.takeCommentById(commentId);
            if (commentOptional.isPresent()) {
                comment = commentOptional.get();
                String lifeHackLikes = String.valueOf(comment.getLikesAmount());
                router.setAjaxRoute(lifeHackLikes);
            } else {
                router.setPagePath(path);
                router.setRedirectRoute();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
