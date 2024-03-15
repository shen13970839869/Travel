package com.zte.service;

import com.zte.pojo.PageBean;
import com.zte.pojo.Route;

public interface RouteService {
	public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname ) ;

	public Route findOne(String rid);

	void add(String rid, int uid);
}
