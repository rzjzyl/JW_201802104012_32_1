package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//表示过滤器名称，表示该过滤器对所有请求有效

/**urlPatterns={"/basic/*","/login/*"}对/basic/school.ctl
 * /login
 * /llogn/servletTest
 * login/a/b有效
 */
@WebFilter(filterName = "Filter 10", urlPatterns = {"/*"})
public class Filter10_Date implements Filter {

    @Override
    public void destroy() {}
    /**重写dofilter方法
     * 包含所有过滤器的过滤链
     * 针对符合条件的请求进行处理
     * 把请求和响应对象传递到过滤链中的下一个过滤器并执行该过滤器
     * 如果当前过滤器是连接中的最后一个则执行用户最初请求的资源
     *
     * idea启动服务器的时候会请求一下tomcat的根目录。
     * 过滤器是针对所有的请求起作用，这个请求自然会被过滤器捕捉到
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    //获得请求时间
    private String getTime() {
        //创建date对象
        Date date = new Date();
        //日期格式化
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        //返回
        return df.format(date);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //打印提示信息
        System.out.println("开始 Filter 1 -encoding begins");
        //强制类型转换成HttpServletRequest类型
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        //打印被请求的资源名称和请求时间
        System.out.println(request.getServletPath() + " @ " + this.getTime());
        //执行其他过滤器，若过滤器已经执行完毕，则执行原请求
        filterChain.doFilter(servletRequest,servletResponse);
        //打印提示信息
        System.out.println("结束 Filter 1 - encoding ends");
    }
}
