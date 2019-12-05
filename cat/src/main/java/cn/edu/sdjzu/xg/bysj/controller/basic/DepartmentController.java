package cn.edu.sdjzu.xg.bysj.controller.basic;

import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 将所有方法组织在一个Controller(Servlet)中
 */
@WebServlet("/department.ctl")
public class DepartmentController extends HttpServlet {
    /**
     * POST,   http://49.234.103.40:8080/workspace/department.ctl, 增加院系
     * 增加一个院系对象：将来自前端请求的JSON对象，增加到数据库表中
     * @param request 请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        //request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String department_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Department对象
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        //设置响应字符编码为UTF-8
        //response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Department对象
        try {
            DepartmentService.getInstance().add(departmentToAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * DELETE,   http://49.234.103.40:8080/workspace/department.ctl?id=1, 删除id=1的院系
     * 删除一个院系对象：根据来自前端请求的id，删除数据库表中id的对应记录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        //设置响应字符编码为UTF-8
        //response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        //到数据库表中删除对应的院系
        try {
            DepartmentService.getInstance().delete(id);
            message.put("message", "删除成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }


    /**
     * PUT,  http://49.234.103.40:8080/workspace/department.ctl, 修改院系
     *
     * 修改一个院系对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        //request.setCharacterEncoding("UTF-8");
        String department_json = JSONUtil.getJSON(request);
        //将JSON字串解析为department对象
        Department departmentToAdd = JSON.parseObject(department_json, Department.class);
        //设置响应字符编码为UTF-8
        //response.setContentType("text/html;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改department对象对应的记录
        try {
            DepartmentService.getInstance().update(departmentToAdd);
            message.put("message", "修改成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * GET,  http://49.234.103.40:8080/workspace/department.ctl, 查询id=1的院系
     * GET, http://49.234.103.40:8080/workspace/department.ctl, 查询所有的院系
     * 把一个或所有学院对象响应到前端
     * 3. 在DepartmentController中doGet方法中
     * 3.1 令department.ctl接收两个参数：
     * 3.1.1 department.ctl?paraType=school&id=1表示要求服务器响应SchoolId=1的所有系
     * 3.1.2 department.ctl?id=1表示要求服务器响应Id=1的所有系 （功能同前）
     * 3.1.3 department.ctl表示要求服务器响应所有系 （功能同前）
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        //response.setContentType("text/html;charset=UTF-8");
        //读取参数id
        String id_str = request.getParameter("id");
        //读取参数paraType
        String paraType = request.getParameter("paraType");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        if (id_str==null&&paraType==null){
            try{
                responseDepartments(response);
            }catch (SQLException e){
                e.printStackTrace();
                message.put("message", "数据库操作异常");
            }
        }else if (id_str!=null&&paraType==null){
            int id = Integer.parseInt(id_str);
            try{
                responseDepartment(id,response);
            }catch (SQLException e){
                e.printStackTrace();
                message.put("message", "数据库操作异常");
            }catch (Exception e){
                e.printStackTrace();
                message.put("message", "网络异常");
            }
        }else if (id_str!=null&&paraType.equals("school")){
            int id = Integer.parseInt(id_str);
            try{
                responseFindAllBySchool(id,response);
            }catch (SQLException e){
                e.printStackTrace();
                message.put("message", "数据库操作异常");
            }catch (Exception e){
                e.printStackTrace();
                message.put("message", "网络异常");
            }
        }
    }
    //响应SchoolId=1的所有系
    private void responseFindAllBySchool(int school_id, HttpServletResponse response)
            throws ServletException,IOException,SQLException{
        // //根据School_id获得相对应的所有系
        Collection<Department> departments = DepartmentService.getInstance().findAllBySchool(school_id);
        //SerializerFeature是个枚举类型,消除对同一对象循环引用的问题,默认为false
        String departmentsbyschool_json = JSON.toJSONString(departments, SerializerFeature.DisableCircularReferenceDetect);

        //响应departmentsbyschool_json到前端
        response.getWriter().println(departmentsbyschool_json);
    }
    //响应一个院系对象
    private void responseDepartment(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找院系
        Department department = DepartmentService.getInstance().find(id);
        String department_json = JSON.toJSONString(department);

        //响应Department_json到前端
        response.getWriter().println(department_json);
    }
    //响应所有院系对象
    private void responseDepartments(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有院系
        Collection<Department> departments = DepartmentService.getInstance().findAll();
        String departments_json = JSON.toJSONString(departments, SerializerFeature.DisableCircularReferenceDetect);
        //响应Departments_json到前端
        response.getWriter().println(departments_json);
    }
}