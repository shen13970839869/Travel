package com.zte.service;

import com.zte.dao.FavoriteDao;
import com.zte.dao.FavoriteDaoImpl;
import com.zte.pojo.Favorite;

public class FavoriteServiceImpl implements FavoriteService{
	private FavoriteDao favoriteDao = new FavoriteDaoImpl();
	@Override
	public boolean isFavorite(String rid, int uid) {

	    Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
	    
	    return favorite != null;//���������ֵ����Ϊtrue����֮����Ϊfalse
	}
	public void add(String rid, int uid) {
	    favoriteDao.add(Integer.parseInt(rid),uid);
	}
	public static void main(String[] args) {
		FavoriteDao favoriteDao = new FavoriteDaoImpl();
		favoriteDao.add(Integer.parseInt("3"),1);
	}

}
