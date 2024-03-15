package com.zte.dao;

import java.util.List;

import com.zte.pojo.Route;

public interface RouteDao {
	public int findTotalCount(int cid) ;
	public List<Route> findByPage(int cid, int start, int pageSize) ;
	List<Route> findByPage(int cid, int start, int pageSize, String rname);
	int findTotalCount(int cid, String rname);
	Route findOne(int rid);
}
