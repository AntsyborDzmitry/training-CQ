package by.test.services.filters;

import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

@SlingFilter(label = "Sample Filter",
                description = "Sample Description",
                metatype = true,
                scope = SlingFilterScope.REQUEST,
                order = -500)
public class TestFilter implements Filter {

    private String filterPathReg = "/content/test-project/bin/testResorceType(.*)";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String path = ((SlingHttpServletRequest)servletRequest).getRequestPathInfo().getResourcePath();

        boolean isFilteredPath = path.matches(filterPathReg);

        if (!(servletRequest instanceof SlingHttpServletRequest)
                || !(servletResponse instanceof SlingHttpServletResponse)
                || !isFilteredPath) {
            // Not a SlingHttpServletRequest/Response, so ignore.
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        filterChain.doFilter(new FilteredRequest(servletRequest), servletResponse);
    }


    @Override
    public void destroy() {

    }
}
