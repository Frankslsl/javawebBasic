package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 *
 */
@WebFilter("*.do")
public class DemoFilter01 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("this is filter 01");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("this is filter 01");
    }
}
