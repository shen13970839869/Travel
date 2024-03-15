package com.zte.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zte.pojo.PageBean;
import com.zte.pojo.Route;
import com.zte.pojo.User;
import com.zte.service.FavoriteService;
import com.zte.service.FavoriteServiceImpl;
import com.zte.service.RouteService;
import com.zte.service.RouteServiceImpl;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * ��ҳ��ѯ
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.���ܲ���
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //����rname ��·����
        String rname = request.getParameter("rname");


        int cid = 0;//���id
        //2.�������
        if(cidStr != null && cidStr.length() > 0){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;//��ǰҳ�룬��������ݣ���Ĭ��Ϊ��һҳ
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        int pageSize = 0;//ÿҳ��ʾ��������������ݣ�Ĭ��ÿҳ��ʾ5����¼
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //3. ����service��ѯPageBean����
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);

        //4. ��pageBean�������л�Ϊjson������
        writeValue(pb,response);

    }
    /**
     * ����id��ѯһ��������·����ϸ��Ϣ
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.����id
        String rid = request.getParameter("rid");
        //2.����service��ѯroute����
        Route route = routeService.findOne(rid);
        //3.תΪjsonд�ؿͻ���
        writeValue(route,response);
    }
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. ��ȡ��·id
        String rid = request.getParameter("rid");

        //2. ��ȡ��ǰ��¼���û� user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//�û�id
        if(user == null){
            //�û���δ��¼
            uid = 0;
        }else{
            //�û��Ѿ���¼
            uid = user.getUid();
        }

        //3. ����FavoriteService��ѯ�Ƿ��ղ�
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4. д�ؿͻ���
        writeValue(flag,response);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. ��ȡ��·rid
        String rid = request.getParameter("rid");
        //2. ��ȡ��ǰ��¼���û�
        User user = (User) request.getSession().getAttribute("user");
        int uid;//�û�id
        if(user == null){
            //�û���δ��¼
            return ;
        }else{
            //�û��Ѿ���¼
            uid = user.getUid();
        }

        
        //3. ����service���
        favoriteService.add(rid,uid);

    }

}

