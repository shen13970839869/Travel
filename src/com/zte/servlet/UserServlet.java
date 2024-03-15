package com.zte.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.pojo.ResultInfo;
import com.zte.pojo.User;
import com.zte.service.UserService;
import com.zte.service.UserServiceImpl;

@WebServlet("/user/*") // /user/add /user/find
public class UserServlet extends BaseServlet {

    //����UserServiceҵ�����
    private UserService service = new UserServiceImpl();

    /**
     * ע�Ṧ��
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //��֤У��
        String check = request.getParameter("check");
        //��sesion�л�ȡ��֤��
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//Ϊ�˱�֤��֤��ֻ��ʹ��һ��
        //�Ƚ�
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            //��֤�����
            ResultInfo info = new ResultInfo();
            //ע��ʧ��
            info.setFlag(false);
            info.setErrorMsg("��֤�����");
            //��info�������л�Ϊjson
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //1.��ȡ����
        Map<String, String[]> map = request.getParameterMap();

        //2.��װ����
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.����service���ע��
        //UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.��Ӧ���
        if(flag){
            //ע��ɹ�
            info.setFlag(true);
        }else{
            //ע��ʧ��
            info.setFlag(false);
            info.setErrorMsg("ע��ʧ��!");
        }

        //��info�������л�Ϊjson
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //��json����д�ؿͻ���
        //����content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    /**
     * ��¼����
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.��ȡ�û�������������
        Map<String, String[]> map = request.getParameterMap();
        //2.��װUser����
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.����Service��ѯ
       // UserService service = new UserServiceImpl();
        User u  = service.login(user);

        ResultInfo info = new ResultInfo();

        //4.�ж��û������Ƿ�Ϊnull
        if(u == null){
            //�û�����������
            info.setFlag(false);
            info.setErrorMsg("�û�����������");
        }
        //5.�ж��û��Ƿ񼤻�
        if(u != null && !"Y".equals(u.getStatus())){
            //�û���δ����
            info.setFlag(false);
            info.setErrorMsg("����δ����뼤��");
        }
        //6.�жϵ�¼�ɹ�
        if(u != null && "Y".equals(u.getStatus())){
            request.getSession().setAttribute("user",u);//��¼�ɹ����

            //��¼�ɹ�
            info.setFlag(true);
        }

        //��Ӧ����
        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    /**
     * ��ѯ��������
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //��session�л�ȡ��¼�û�
        Object user = request.getSession().getAttribute("user");
        //��userд�ؿͻ���

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }

    /**
     * �˳�����
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.����session
        request.getSession().invalidate();

        //2.��ת��¼ҳ��
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * �����
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.��ȡ������
        String code = request.getParameter("code");
        if(code != null){
            //2.����service��ɼ���
            //UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            //3.�жϱ��
            String msg = null;
            if(flag){
                //����ɹ�
                msg = "����ɹ�����<a href='login.html'>��¼</a>";
            }else{
                //����ʧ��
                msg = "����ʧ�ܣ�����ϵ����Ա!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
