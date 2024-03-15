package com.zte.dao;

import java.util.List;

import com.zte.pojo.RouteImg;

public interface RouteImgDao {

	List<RouteImg> findByRid(int rid);

}
