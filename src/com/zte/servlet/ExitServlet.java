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
@WebServlet("/exitServlet")
public class ExitServlet extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.Ïú»Ùsession
		request.getSession().invalidate();

		//2.Ìø×ªµÇÂ¼Ò³Ãæ
		response.sendRedirect(request.getContextPath()+"/login.html");



	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
