import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;



/**
 * Calendar
 * 
 * TSP Program 1
 * 
 * @author Brady Mahar
 * @author Austin Browne
 * 
 */
@SuppressWarnings("serial")
public class CalendarMain extends JFrame {



	public static void main(String[] args) {
		AddEvent theCal = new AddEvent();
		theCal.setVisible(true);
		PreparedStatement preStmt=null;
		Date date = null;

		System.out.println("-------- MySQL JDBC Connection Testing ------------");
		String insertSQL = "INSERT INTO Event (Date, Name, Location) " +
                "VALUES (09/26/2013, Exam, Fisher 326)";
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
			preStmt = (PreparedStatement) connection.prepareStatement("INSERT INTO "
					+ "Event(Name) VALUES(?)");
			String d = "2013-9-26";  
		    java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(d);  
		    java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
			preStmt.setString(1,"Exam");
			//preStmt.setString(2, "Fisher 326");
			preStmt.executeUpdate();

		} catch (SQLException | ParseException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		
	}

}
