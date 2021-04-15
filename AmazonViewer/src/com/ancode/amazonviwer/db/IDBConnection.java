package com.ancode.amazonviwer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import static com.ancode.amazonviwer.db.DataBase.*;

public interface IDBConnection {

default Connection connectToDB() {
	Connection connection=null;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection=DriverManager.getConnection(URL+DB,USER,PASSWORD);
		if(connection!=null) {
			System.out.println("se establecion la connecion :)");
		}
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		return connection;
	}
}
}