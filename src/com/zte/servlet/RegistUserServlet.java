package com.zte.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.pojo.ResultInfo;
import com.zte.pojo.User;
import com.zte.service.UserService;
import com.zte.service.UserServiceImpl;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


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
            System.out.println("json"+json);
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
        UserService service = new UserServiceImpl();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
