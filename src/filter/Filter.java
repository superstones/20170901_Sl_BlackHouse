package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * Created by Administrator on 2017/9/3.
 */
@WebFilter(filterName = "filter",urlPatterns = "/*")
public class Filter implements javax.servlet.Filter {
    private Properties mProperties = new Properties();

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        String blackIp = mProperties.getProperty("ip");
        String remoteIp = req.getRemoteAddr();
        if (blackIp.equals(remoteIp)) {
            req.getRequestDispatcher("/blackhouse.jsp").forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        String path = config.getServletContext().getRealPath("/WEB-INF/classes/ip.properties");
        try {
            InputStream inputStream = new FileInputStream(new File(path));
            mProperties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
