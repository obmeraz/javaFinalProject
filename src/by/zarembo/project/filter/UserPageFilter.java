package by.zarembo.project.filter;

import by.zarembo.project.command.CommandConstant;
import by.zarembo.project.entity.RoleType;
import by.zarembo.project.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The type User page filter.
 */
@WebFilter(urlPatterns = {"/jsp/user/*"})
public class UserPageFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        User user = (User) request.getSession().getAttribute(CommandConstant.USER);
        if (user == null || user.getRole().equals(RoleType.GUEST)) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, resp);
        }
        chain.doFilter(request, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
