package cn.edu.sdjzu.xg.bysj.controller.secyrity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/getSession")
public class GetSession extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如果当前请求对应着服务器内存中的一个session对象，则返回该对象
        //如果服务器内存中的没有session对象与当前请求对应，则返回null
        HttpSession session = req.getSession(false);
        resp.getWriter().println(session);
    }
}
