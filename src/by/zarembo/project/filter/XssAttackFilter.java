package by.zarembo.project.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@WebFilter(filterName = "XssAttackFilter", urlPatterns = {"/*"})
public class XssAttackFilter implements Filter {
    static class FilteredRequest extends HttpServletRequestWrapper {
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
        filteredRequest.getParameterMap();
        chain.doFilter(filteredRequest, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
