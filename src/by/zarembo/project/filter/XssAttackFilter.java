package by.zarembo.project.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * The type Xss attack filter.
 */
@WebFilter(filterName = "XssAttackFilter", urlPatterns = {"/*"})
public class XssAttackFilter implements Filter {
    /**
     * The type Filtered request.
     */
    static class FilteredRequest extends HttpServletRequestWrapper {
        /**
         * Instantiates a new Filtered request.
         *
         * @param request the request
         */
        FilteredRequest(ServletRequest request) {
            super((HttpServletRequest) request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            return value == null ? "" : value.replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;");
        }
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        FilteredRequest filteredRequest = new FilteredRequest(req);
        chain.doFilter(filteredRequest, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
