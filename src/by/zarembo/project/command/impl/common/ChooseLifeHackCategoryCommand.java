package by.zarembo.project.command.impl.common;

import by.zarembo.project.command.Command;
import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.controller.Router;
import by.zarembo.project.entity.CategoryType;
import by.zarembo.project.exception.CommandException;
import by.zarembo.project.exception.ServiceException;
import by.zarembo.project.service.LifeHackService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The type Choose lifehack category command.
 */
public class ChooseLifeHackCategoryCommand implements Command {
    private static final String CATEGORY_HASH_MAP = "categoryMap";
    private LifeHackService lifeHackService = new LifeHackService();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Map<CategoryType, Integer> categoryMap;
        Router router = new Router();
        try {
            categoryMap = lifeHackService.takeCategories();
            if (!categoryMap.isEmpty()) {
                router.setPagePath(PagePath.PATH_PAGE_CHOOSE_CATEGORY);
                request.setAttribute(CATEGORY_HASH_MAP, categoryMap.entrySet());
            } else {
                request.setAttribute(CommandConstant.MESSAGE, "No any categories yet");
                router.setPagePath(PagePath.PATH_PAGE_CHOOSE_CATEGORY);
            }
        } catch (ServiceException e) {
            throw new CommandException("Choose category error", e);
        }
        return router;
    }
}
