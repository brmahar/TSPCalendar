import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import javax.swing.*;

import com.mysql.jdbc.PreparedStatement;



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
		MainInterface theCal = new MainInterface();
		theCal.setVisible(true);
		TheCalendar cal = new TheCalendar();
		//WeekView week = new WeekView();
		
	}

}
