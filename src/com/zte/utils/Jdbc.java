package com.zte.utils;

import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

public class Jdbc {
	public static void main(String[] args){
		Driver driver;
		try {
			driver = new Driver();
			String url = "jdbc:mysql:localhost:3306:shen";
			Properties pro = new Properties();
			pro.setProperty("user", "root");
			pro.setProperty("password", "123456");
			Connection conn = (Connection) driver.connect(url, pro);
			System.out.println(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void abTest(){
		System.out.println(1);
	}
}
