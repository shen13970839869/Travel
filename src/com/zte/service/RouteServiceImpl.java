package com.zte.service;

import java.util.List;

import com.zte.dao.FavoriteDao;
import com.zte.dao.FavoriteDaoImpl;
import com.zte.dao.RouteDao;
import com.zte.dao.RouteDaoImpl;
import com.zte.dao.RouteImgDao;
import com.zte.dao.RouteImgDaoImpl;
import com.zte.dao.SellerDao;
import com.zte.dao.SellerDaoImpl;
import com.zte.pojo.PageBean;
import com.zte.pojo.Route;
import com.zte.pojo.RouteImg;
import com.zte.pojo.Seller;

public class RouteServiceImpl implements RouteService {
	private RouteDao routeDao = new RouteDaoImpl();
	private SellerDao sellerDao = new SellerDaoImpl();
	private RouteImgDao routeImgDao = new RouteImgDaoImpl();
	private FavoriteDao favoriteDao = new FavoriteDaoImpl();
	public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname ) {
	    //��װPageBean
	    PageBean<Route> pb = new PageBean<Route>();
	    //���õ�ǰҳ��
	    pb.setCurrentPage(currentPage);
	    //����ÿҳ��ʾ����
	    pb.setPageSize(pageSize);
	    
	    //�����ܼ�¼��
	    int totalCount = routeDao.findTotalCount(cid,rname);
	    pb.setTotalCount(totalCount);
	    //���õ�ǰҳ��ʾ�����ݼ���
	    int start = (currentPage - 1) * pageSize;//��ʼ�ļ�¼��
	    List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
	    pb.setList(list);

	    //������ҳ�� = �ܼ�¼��/ÿҳ��ʾ����
	    int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :(totalCount / pageSize) + 1 ;
	    pb.setTotalPage(totalPage);


	    return pb;
	}
	@Override
	public Route findOne(String rid) {
	    //1.����idȥroute���в�ѯroute����
	    Route route = routeDao.findOne(Integer.parseInt(rid));
	    int count = favoriteDao.findCountByRid(route.getRid());
	    route.setCount(count);

	    //2.����route��id ��ѯͼƬ������Ϣ
	    List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
	    //2.2���������õ�route����
	    route.setRouteImgList(routeImgList);
	    //3.����route��sid���̼�id����ѯ�̼Ҷ���
	    Seller seller = sellerDao.findById(route.getSid());
	    route.setSeller(seller);

	    return route;
	}
	@Override
	public void add(String rid, int uid) {
	    favoriteDao.add(Integer.parseInt(rid),uid);
	}

}
