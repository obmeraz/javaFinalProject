package by.zarembo.project.command.impl.common;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.Comment;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.CommentService;
import by.zarembo.project.service.LifeHackService;
import by.zarembo.project.util.LifeHackValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DisplayLifeHackPostCommand implements Command {
    private static final String ATTRIBUTE_COMMENTS = "comments";
    private static final String ATTRIBUTE_LIFEHACK = "lifehack";
    private static final String ATTRIBUTE_IS_LIKED_ALREADY = "liked";
    private static final String COMMENTS_LIKE_MAP = "commentsLikesMap";
    private LifeHackService lifeHackService = new LifeHackService();
    private CommentService commentService = new CommentService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        LifeHack lifeHack;
        Router router = new Router();
        User user = (User) request.getSession().getAttribute(AttributeConstant.USER);
        String id = request.getParameter(AttributeConstant.LIFEHACK_ID);
        if (!LifeHackValidator.validateId(id)) {
            request.getSession().setAttribute(AttributeConstant.MESSAGE, "Invalid id");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
            return router;
        }
        try {
            long lifeHackId = Long.valueOf(id);
            Optional<LifeHack> lifeHackOptional = lifeHackService.takeLifeHackById(lifeHackId);
            if (lifeHackOptional.isPresent()) {
                lifeHack = lifeHackOptional.get();
                List<Comment> comments = commentService.takeCommentsToLifeHack(lifeHackId);
                request.setAttribute(ATTRIBUTE_COMMENTS, comments);
                request.setAttribute(ATTRIBUTE_LIFEHACK, lifeHack);
                if (user != null) {
                    if (lifeHackService.isLikedAlready(lifeHackId, user.getUserId())) {
                        request.setAttribute(ATTRIBUTE_IS_LIKED_ALREADY, true);
                    }
                    Map<Comment, Boolean> commentsLikesMap =
                            commentService.markIsLikedComments(comments, user.getUserId());
                    request.setAttribute(COMMENTS_LIKE_MAP, commentsLikesMap);
                }
                router.setPagePath(PagePath.PATH_PAGE_LIFEHACK);
            } else {
                request.getSession().setAttribute(AttributeConstant.MESSAGE, "Can't found lifehack");
                router.setPagePath(PagePath.PATH_PAGE_ERROR);
                router.setRedirectRoute();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
