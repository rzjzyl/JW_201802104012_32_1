package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//表示过滤器名称，表示该过滤器对所有请求有效

/**urlPatterns={"/basic/*","/login/*"}对/basic/school.ctl
 * /login
 * /llogn/servletTest
 * login/a/b有效
 */
@WebFilter(filterName = "Filter 00", urlPatterns = {"/*"})
//实现filter接口
public class Filter00_Encoding implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    /**重写dofilter方法
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * 包含所有过滤器的过滤链
     * 针对符合条件的请求进行处理
     * 把请求和响应对象传递到过滤链中的下一个过滤器并执行该过滤器
     * 如果当前过滤器是连接中的最后一个则执行用户最初请求的资源
     * idea启动服务器的时候会请求一下tomcat的根目录。
     * 过滤器是针对所有的请求起作用，这个请求自然会被过滤器捕捉到
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        //打印提示信息
        System.out.println("开始 Filter 00 -encoding begins");
        //强制类型转换成HttpServletRequest类型
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //获得请求的url
        String path = request.getRequestURI();
        //如果请求路径不含"/login"
        if (!path.contains("/myapp")){
            //打印提示信息
            System.out.println("设置response的编码格式");
            //设置响应字符编码格式
            response.setContentType("text/html;charset=UTF-8");
            //获得请求的方法
            String method = request.getMethod();
            //如果请求方法是POST或PUT
            if ("POST-PUT".contains(method)){
                //设置请求字符编码为UTF-8
                System.out.println("设置request的编码格式");
                request.setCharacterEncoding("UTF-8");
            }
        }
        //执行其他过滤器，若过滤器已经执行完毕，则执行原请求
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("结束 Filter 00 - encoding ends");
    }
}
