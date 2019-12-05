package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "Filter 20",urlPatterns = {"/*"})
public class Filter20_LoginSession implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filter 20 - LoginSessionFilter begin");
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp =(HttpServletResponse)response;
        HttpSession session = req.getSession(false);
        String path = req.getRequestURI();
        if (path.contains("/login.ctl")||path.contains("logout.ctl")){
            chain.doFilter(request,response);
            System.out.println("Filter 20 - LoginSessionFilter ending!");
        }else if (session!= null&& session.getAttribute("currentUser")!= null){
            chain.doFilter(request,response);
            System.out.println("Filter 20 - LoginSessionFilter ending!");
        }else {
            resp.getWriter().println("您没有登录，请登录！");
        }
    }

    @Override
    public void destroy() {

    }
}
