package by.zarembo.project.filter;

import by.zarembo.project.command.CommandConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The type Locale filter.
 */
@WebFilter(urlPatterns = {"/*"})
public class LocaleFilter implements Filter {
    private static final String LANGUAGE_EN = "en";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(true);
        if (session.getAttribute(CommandConstant.LANGUAGE) == null) {
            session.setAttribute(CommandConstant.LANGUAGE, LANGUAGE_EN);
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
