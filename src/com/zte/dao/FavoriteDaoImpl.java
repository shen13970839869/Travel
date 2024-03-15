package com.zte.dao;

import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zte.pojo.Favorite;
import com.zte.utils.JdbcUtils;

public class FavoriteDaoImpl extends FavoriteDao {
	private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());
	@Override
	public Favorite findByRidAndUid(int rid, int uid) {
	    Favorite favorite = null;
	    try {
	        String sql = " select * from tab_favorite where rid = ? and uid = ?";
	        favorite = (Favorite) template.queryForList(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
	    } catch (DataAccessException e) {
	        e.printStackTrace();
	    }
	    return favorite;
	}
	@Override
	public int findCountByRid(int rid) {
	    String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";

	    return template.queryForObject(sql,Integer.class,rid);
	}
	@Override
	public void add(int rid, int uid) {
	    String sql = "insert into tab_favorite values(?,?,?)";

	    template.update(sql,rid,new Date(),uid);
	}

}	
