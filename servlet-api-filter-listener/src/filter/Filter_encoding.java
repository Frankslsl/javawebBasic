package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebFilter("*.do")

public class Filter_encoding implements Filter {
    private String encoding_de = "UTF-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encoding = filterConfig.getInitParameter("encoding");
        if (encoding != null && ! "".equals(encoding)){
            encoding_de = encoding;
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletRequest) servletRequest).setCharacterEncoding(encoding_de);
        filterChain.doFilter(servletRequest,servletResponse);
        ((HttpServletResponse) servletResponse).setCharacterEncoding(encoding_de);
    }
}
