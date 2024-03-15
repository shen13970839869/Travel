package com.zte.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BaseServlet extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("baseServlet��service������ִ����...");

        //��ɷ����ַ�
        //1.��ȡ����·��
        String uri = req.getRequestURI(); //   /travel/user/add
        System.out.println("����uri:"+uri);//  /travel/user/add
        //2.��ȡ��������
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println("�������ƣ�"+methodName);
        //3.��ȡ��������Method
        //˭�����ң��Ҵ���˭
        System.out.println(this);//UserServlet�Ķ���cn.itcast.travel.web.servlet.UserServlet@4903d97e
        try {
            //��ȡ����
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.ִ�з���
            //��������
            //method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
    /**
     * ֱ�ӽ�����Ķ������л�Ϊjson������д�ؿͻ���
     * @param obj
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * ������Ķ������л�Ϊjson������
     * @param obj
     * @return
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}
