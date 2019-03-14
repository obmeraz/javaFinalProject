package by.zarembo.project.filter;

import by.zarembo.project.command.AttributeConstant;
import by.zarembo.project.command.CommandType;
import by.zarembo.project.command.PagePath;
import by.zarembo.project.entity.RoleType;
import by.zarembo.project.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"})
public class ControllerFilter implements Filter {
    private static final String COMMAND_PARAMETER = "command";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String command = request.getParameter(COMMAND_PARAMETER);
        RoleConfigurator roleConfigurator = new RoleConfigurator();
        if (command == null || command.isEmpty() || !roleConfigurator.checkCommandName(command)) {
            response.sendRedirect(request.getContextPath() + PagePath.PATH_PAGE_MAIN);
        } else {
            CommandType type = CommandType.valueOf(command.toUpperCase());
            User user = (User) request.getSession().getAttribute(AttributeConstant.USER);
            RoleType roleType;
            if (user == null) {
                roleType = RoleType.GUEST;
            } else {
                roleType = user.getRole();
            }
            if (!roleConfigurator.checkRolePrivileges(roleType, type)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                chain.doFilter(req, resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
