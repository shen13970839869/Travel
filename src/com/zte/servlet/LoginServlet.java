package com.zte.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zte.pojo.ResultInfo;
import com.zte.pojo.User;
import com.zte.service.UserService;
import com.zte.service.UserServiceImpl;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		UserService service = new UserServiceImpl();
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
		    //��¼�ɹ�
		    info.setFlag(true);
		    request.getSession().setAttribute("user", u);
		}

		//��Ӧ����
		ObjectMapper mapper = new ObjectMapper();

		response.setContentType("application/json;charset=utf-8");
		mapper.writeValue(response.getOutputStream(),info);
		
		

	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}	
