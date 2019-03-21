package by.zarembo.project.command.impl.admin;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.LifeHack;
import by.zarembo.project.entity.User;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;
import by.zarembo.project.util.LifeHackValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Create lifehack command.
 */
public class CreateLifeHackCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    private static final String PARAM_LIFEHACK_NAME = "lifehack_name";
    private static final String PARAM_LIFEHACK_CONTENT = "lifehack_content";
    private static final String PARAM_EXCERPT = "excerpt";
    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_IMAGE = "image";
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String category = request.getParameter(PARAM_CATEGORY);
        String name = request.getParameter(PARAM_LIFEHACK_NAME);
        User user = (User) request.getSession().getAttribute(CommandConstant.USER);
        String content = request.getParameter(PARAM_LIFEHACK_CONTENT);
        String excerpt = request.getParameter(PARAM_EXCERPT);
        String path = String.valueOf(request.getSession().getAttribute(CommandConstant.CURRENT_PAGE));
        Part filePart;
        try {
            filePart = request.getPart(PARAM_IMAGE);
            List<String> errorMessages = new ArrayList<>();
            if (LifeHackValidator.validate(category, name, content, excerpt, filePart, errorMessages)) {
                LifeHack lifeHack = lifeHackService.buildLifeHack(user, category,
                        name, content, excerpt, filePart);
                lifeHackService.addNewLifeHack(lifeHack);
                router.setPagePath(PagePath.PATH_PAGE_ADD_LIFEHACK);
                router.setRedirectRoute();
            } else {
                request.getSession().setAttribute("errorMessages", errorMessages);
                router.setPagePath(path);
                router.setRedirectRoute();
            }
        } catch (IOException | ServletException e) {
            request.getSession().setAttribute(CommandConstant.MESSAGE, "Getting part from request error");
            logger.error("Getting part from request error", e);
            router.setPagePath(PagePath.PATH_PAGE_ERROR);
            router.setRedirectRoute();
            return router;
        } catch (ServiceException e) {
            throw new CommandException("LifeHack adding error", e);
        }
        return router;
    }
}
