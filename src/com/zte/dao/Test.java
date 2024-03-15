package com.zte.dao;

import java.io.InputStream;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.jdbc.Connection;
import com.zte.utils.JdbcUtils;

import redis.clients.jedis.JedisPool;

public class Test {
	public static void main(String[] args) throws Exception {
		System.out.println("-------");
    	DataSource ds = JdbcUtils.getDataSource();
    	System.out.println(ds);
    	/*Connection con = (Connection) ds.getConnection();
    	System.out.println(con);*/
    	JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());
    	System.out.println(template);
	}
}
