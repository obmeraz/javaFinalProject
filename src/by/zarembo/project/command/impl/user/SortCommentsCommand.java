package by.zarembo.project.command.impl.user;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.command.SortType;
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

public class SortCommentsCommand implements Command {
    private static final String ATTRIBUTE_COMMENTS = "comments";
    private static final String SORT_TYPE = "sort_type";
    private static final String ATTRIBUTE_LIFEHACK = "lifehack";
    private static final String COMMENTS_LIKE_MAP = "commentsLikesMap";
    private CommentService commentService = new CommentService();
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        User user = (User) request.getSession().getAttribute(AttributeConstant.USER);
        String id = request.getParameter(AttributeConstant.LIFEHACK_ID);
        String type = request.getParameter(SORT_TYPE);
        if (!LifeHackValidator.validateId(id) || !commentService.checkSortTypeEnum(type)) {
            request.getSession().setAttribute(AttributeConstant.MESSAGE, "Invalid data");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
            return router;
        }
        SortType sortType = SortType.valueOf(type.toUpperCase());
        try {
            long lifeHackId = Long.parseLong(id);
            Optional<LifeHack> lifeHackOptional = lifeHackService.takeLifeHackById(lifeHackId);
            if (lifeHackOptional.isPresent()) {
                LifeHack lifeHack = lifeHackOptional.get();
                List<Comment> comments = commentService.takeSortComments(sortType, lifeHackId);
                request.setAttribute(ATTRIBUTE_COMMENTS, comments);
                request.setAttribute(ATTRIBUTE_LIFEHACK, lifeHack);
                Map<Comment, Boolean> commentsLikesMap =
                        commentService.markIsLikedComments(comments, user.getUserId());
                request.setAttribute(COMMENTS_LIKE_MAP, commentsLikesMap);
                router.setPagePath(PagePath.PATH_PAGE_LIFEHACK);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
