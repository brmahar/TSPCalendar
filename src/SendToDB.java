import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import javax.swing.*;

import com.mysql.jdbc.PreparedStatement;


public class SendToDB {
	
	
	public void runStore(StoreData data){
		

		System.out.println("------------ MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered");
		Connection connection = null;

		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://orion.csl.mtu.edu/ajbrowne","ajbrowne", "ajZ4VikY/tnI.");

		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			((Throwable) e).printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("Now Connected!");
			send(connection, data);
		} else {
			System.out.println("Failed to make connection!");
		}
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("The connection was not closed.....Run away now!!!!");
			e.printStackTrace();
		}
	}
	
	public void send(Connection connection, StoreData data){
		PreparedStatement preStmt=null;
		StoreData theData = data;
		String name = theData.getName();
		String local = theData.getLocation();
		String date = theData.getDate();
		String description = theData.getDescription();
		String theSTime = theData.getSTime();
		String theETime = theData.getETime();

		

		
		try {
			preStmt = (PreparedStatement) connection.prepareStatement("INSERT INTO "
					+ "Event(Name,Location,Description,Date, Start_Time, End_Time) VALUES(?,?,?,?,?,?)"); 
		    java.util.Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(date);
		    java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
			preStmt.setString(1,name);
			preStmt.setDate(4,sqlDate);
			preStmt.setString(2,local);
			preStmt.setString(3,description);
			preStmt.setString(5, theSTime);
			preStmt.setString(6, theETime);
			preStmt.executeUpdate();
		} catch (SQLException | ParseException e) {
			System.out.println("Nothing was added lawllawllawl");
			e.printStackTrace();
		}
		
		
	}
	

}
