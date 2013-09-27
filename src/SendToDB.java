import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import javax.swing.*;

import com.mysql.jdbc.PreparedStatement;


public class SendToDB {
	
	
	public void runStore(StoreData data){
		

		System.out.println("-------- MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://orion.csl.mtu.edu/ajbrowne","ajbrowne", "ajZ4VikY/tnI.");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			((Throwable) e).printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			send(connection, data);
		} else {
			System.out.println("Failed to make connection!");
		}
	}
	
	public void send(Connection connection, StoreData data){
		PreparedStatement preStmt=null;
		StoreData theData = data;
		String name = theData.getName();
		String local = theData.getLocation();
		String date = theData.getDate();
		String description = theData.getDescription();

		
		try {
			preStmt = (PreparedStatement) connection.prepareStatement("INSERT INTO "
					+ "Event(Name,Location,Description,Date) VALUES(?,?,?,?)"); 
		    java.util.Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(date);  
		    java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
			preStmt.setString(1,name);
			preStmt.setDate(4,sqlDate);
			preStmt.setString(2,local);
			preStmt.setString(3,description);
			preStmt.executeUpdate();
		} catch (SQLException | ParseException e) {
			System.out.println("Nothing was added lawllawllawl");
			e.printStackTrace();
		}
		
		
	}
	

}
