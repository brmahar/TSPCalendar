import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.event.ActionListener;


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
			.getConnection("jdbc:mysql://orion.csl.mtu.edu/brmahar","brmahar", "JMDRMzrjfxsFCxpJ");
	 
		} catch (SQLException e) {
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
