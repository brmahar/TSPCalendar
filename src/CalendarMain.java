<<<<<<< HEAD
=======
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

>>>>>>> 6de8acd5269ec8b75a716697c821bb539a8c29c6
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
	
	private JTextField addName = new JTextField(10);
	private JButton addEvent = new JButton("Add Event");
	private JTextField editName = new JTextField(10);
	private JButton editEvent = new JButton("Edit Event");
	private JTextField deleteName = new JTextField(10);
	private JButton deleteEvent = new JButton("Delete Event");
	
	CalendarMain(){
		JPanel eventPanel = new JPanel();
		this.add(eventPanel);
		this.setSize(175,250);
		
		eventPanel.add(addName);
		eventPanel.add(addEvent);
		eventPanel.add(editName);
		eventPanel.add(editEvent);
		eventPanel.add(deleteName);
		eventPanel.add(deleteEvent);
		
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	public String getAddEvent(){
		return addName.getText();
	}
	
	public String getEditEvent(){
		return editName.getText();
	}
	
	public String getDeleteName(){
		return deleteName.getText();
	}
	
	void addAddListener(ActionListener listenForAdd){
		addEvent.addActionListener(listenForAdd);
	}
	
	void addEditListener(ActionListener listenForEdit){
		editEvent.addActionListener(listenForEdit);
	}
	
	void addDeleteListener(ActionListener listenForDelete){
		deleteEvent.addActionListener(listenForDelete);
	}
	
	public static void main(String[] args) {
		CalendarMain theCal = new CalendarMain();
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
