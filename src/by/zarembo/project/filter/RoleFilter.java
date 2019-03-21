package by.zarembo.project.filter;

import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.entity.RoleType;
import by.zarembo.project.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The type Role filter.
 */
@WebFilter(urlPatterns = {"/*"})
public class RoleFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(true);
        if (session.getAttribute(CommandConstant.USER) == null) {
            User user = new User();
            user.setRoleEnum(RoleType.GUEST);
            session.setAttribute(CommandConstant.ROLE, RoleType.GUEST);
            session.setAttribute(CommandConstant.USER, user);
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
