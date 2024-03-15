package com.zte.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zte.service.UserService;
import com.zte.service.UserServiceImpl;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet{

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//1.��ȡ������
		String code = request.getParameter("code");
		if(code != null){
		    //2.����service��ɼ���
		    UserService service = new UserServiceImpl();
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
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        this.doPost(request, response);
	    }
}
