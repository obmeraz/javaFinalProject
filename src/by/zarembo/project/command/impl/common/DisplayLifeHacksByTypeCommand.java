package by.zarembo.project.command.impl.common;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.Command;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.CommentService;
import by.zarembo.project.service.LifeHackService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class DisplayLifeHacksByTypeCommand implements Command {
    private static final String TYPE = "type";
    private static final String CATEGORY = "category";
    private static final String NUMBER_OF_PAGES = "numberOfPages";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String TITLE = "title";
    private static final String COMMENT_MAP = "commentMap";

    private LifeHackService lifeHackService = new LifeHackService();
    private CommentService commentService = new CommentService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        List<LifeHack> lifeHacks;
        User user = (User) request.getSession().getAttribute(AttributeConstant.USER);
        Router router = new Router();
        String type = request.getParameter(TYPE);
        if (type == null || type.isEmpty()) {
            request.getSession().setAttribute(AttributeConstant.MESSAGE, "Invalid type");
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
            return router;
        }
        String category = request.getParameter(CATEGORY);
        int currentPage = Integer.parseInt(request.getParameter(AttributeConstant.CURRENT_PAGE));
        try {
            lifeHacks = lifeHackService.takeLifeHacksByType(type, category, currentPage, user);
            if (lifeHacks != null) {
                Map<LifeHack, Integer> commentCountMap =
                        commentService.countLifeHackComments(lifeHacks);
                request.setAttribute(AttributeConstant.LIFEHACKS, lifeHacks);
                request.setAttribute(TITLE, type);
                request.setAttribute(CATEGORY, category);
                request.setAttribute(COMMENT_MAP, commentCountMap);
                router.setPagePath(PagePath.PATH_PAGE_DISPLAY_LIFEHACKS);
            } else {
                request.getSession().setAttribute(AttributeConstant.MESSAGE, "Invalid data");
                router.setPagePath(PagePath.PATH_PAGE_ERROR);
                router.setRedirectRoute();
                return router;
            }
            int numberOfPages = lifeHackService.getNumberOfPages(lifeHacks);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(CURRENT_PAGE, currentPage);
            request.setAttribute(TYPE, type);
            request.setAttribute(AttributeConstant.MESSAGE, "No any lifehacks");
            router.setPagePath(PagePath.PATH_PAGE_DISPLAY_LIFEHACKS);

        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
