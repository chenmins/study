package org.chenmin.auto.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.chenmin.auto.shared.FlightWG;
import org.chenmin.auto.shared.PassengerWG;

public class Test {
	
	static String url="jdbc:mysql://pektsy.f3322.net/tsy_manger";
	static String user="clm";
	static String password="clm#1";
	//
	
	public static void main(String[] args)    {
//		yyyy-MM-dd
//		String y = "yyyy-MM-dd";
//		String dob = y.substring(8, 10);
//		String mob = y.substring(5, 7);
//		String yob =y.substring(0, 4);
//		System.out.println(dob);
//		System.out.println(mob);
//		System.out.println(yob);
		
		String sysdate = getSysdate( );
		System.out.println(sysdate);
		String orderID = "306245307572";
		List<FlightWG> p = getFlightWGs(orderID);
		System.out.println(p);
		
		List<PassengerWG> pa = getPassengerWGs(orderID);
		System.out.println(pa);
	}

	
	public static List<PassengerWG> getPassengerWGs(String orderID)  {
		Connection conn = DBUtils.getConnection(url, user, password);
		QueryRunner run = new QueryRunner( );
		// Use the BeanHandler implementation to convert the first
//		ResultSetHandler<FlightWG> h = new BeanHandler<FlightWG>(FlightWG.class);
		ResultSetHandler<List<PassengerWG>> h = new BeanListHandler<PassengerWG>(PassengerWG.class);
		List<PassengerWG> p;
		try {
			p = run.query(conn,
			    "SELECT orderID , firstname , lastname , sexy , type , credentialsname , credentialsID , nationality , date_format(birthday,'%Y-%m-%d') birthday, date_format(expirydate,'%Y-%m-%d') expirydate , credentialsnationality  FROM passenger WHERE orderID=?", h, orderID);
			DBUtils.closeResources(conn,null,null);
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}

	public static List<FlightWG> getFlightWGs(String orderID)  {
		Connection conn = DBUtils.getConnection(url, user, password);
		QueryRunner run = new QueryRunner( );
		// Use the BeanHandler implementation to convert the first
//		ResultSetHandler<FlightWG> h = new BeanHandler<FlightWG>(FlightWG.class);
		ResultSetHandler<List<FlightWG>> h = new BeanListHandler<FlightWG>(FlightWG.class);
		List<FlightWG> p;
		try {
			p = run.query(conn,
			    "SELECT orderID,depAirportCode,arrAirportCode,date_format(depTime,'%l:%i%p %a, %b %d') depTime  ,flightNum,carrier FROM flight WHERE orderID=?", h, orderID);
			DBUtils.closeResources(conn,null,null);
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	

	public static String getSysdate( )   {
		String sysdate = "";
		Connection conn = DBUtils.getConnection(url, user, password);
		try {
			Statement statement = conn.createStatement();
			String sql = "select sysdate() a";
			ResultSet rs =  statement.executeQuery(sql);
			if(rs.next())
				sysdate = rs.getString(1);
			DBUtils.closeResources(conn, statement, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sysdate;
	}

}
