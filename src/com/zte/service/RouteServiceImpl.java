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
	    //封装PageBean
	    PageBean<Route> pb = new PageBean<Route>();
	    //设置当前页码
	    pb.setCurrentPage(currentPage);
	    //设置每页显示条数
	    pb.setPageSize(pageSize);
	    
	    //设置总记录数
	    int totalCount = routeDao.findTotalCount(cid,rname);
	    pb.setTotalCount(totalCount);
	    //设置当前页显示的数据集合
	    int start = (currentPage - 1) * pageSize;//开始的记录数
	    List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
	    pb.setList(list);

	    //设置总页数 = 总记录数/每页显示条数
	    int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :(totalCount / pageSize) + 1 ;
	    pb.setTotalPage(totalPage);


	    return pb;
	}
	@Override
	public Route findOne(String rid) {
	    //1.根据id去route表中查询route对象
	    Route route = routeDao.findOne(Integer.parseInt(rid));
	    int count = favoriteDao.findCountByRid(route.getRid());
	    route.setCount(count);

	    //2.根据route的id 查询图片集合信息
	    List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
	    //2.2将集合设置到route对象
	    route.setRouteImgList(routeImgList);
	    //3.根据route的sid（商家id）查询商家对象
	    Seller seller = sellerDao.findById(route.getSid());
	    route.setSeller(seller);

	    return route;
	}
	@Override
	public void add(String rid, int uid) {
	    favoriteDao.add(Integer.parseInt(rid),uid);
	}

}
