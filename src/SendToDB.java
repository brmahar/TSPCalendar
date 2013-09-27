import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import javax.swing.*;

import com.mysql.jdbc.PreparedStatement;


public class SendToDB {
	
	
	public void runStore(){
		

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
			send(connection);
		} else {
			System.out.println("Failed to make connection!");
		}
	}
	
	public void send(Connection connection){
		PreparedStatement preStmt=null;
		StoreData theData = new StoreData();
		String name = theData.getName();
		String local = theData.getLocation();
		String date = theData.getDate();
		String description = theData.getDescription();
		System.out.println(name);
		System.out.println(local);
		System.out.println(description);
		
		try {
			preStmt = (PreparedStatement) connection.prepareStatement("INSERT INTO "
					+ "Event(Name,Location,Description) VALUES(?,?,?)"); 
		    //java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		    //java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
			preStmt.setString(1,name);
			//preStmt.setDate(2,sqlDate);
			preStmt.setString(2,local);
			preStmt.setString(3,description);
			preStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Nothing was added lawllawllawl");
			e.printStackTrace();
		}
		
		
	}
	

}
