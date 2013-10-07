import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * This class creates the view that displays event by week and also allows
 * the user to view the events that are occurring on each day.
 *
 */
public class WeekView {
	// Class variables that set up the week view
	static JLabel month;
	static JButton prev;
	static JButton next;
	static JButton backToMain;
	static JTable theCalendar;
	@SuppressWarnings("rawtypes")
	static JFrame mainFrame;
	static Container thePane;
	static DefaultTableModel calendarTable;
	static JScrollPane calendarScroll;
	static JPanel calendarPanel;
	// Ints that allow for days population
	static int theDay;
	static int theMonth;
	static int theYear;
	static int otherMonth;
	static int otherYear;
	static int otherDay;
	static boolean first = false;
	static JFrame theParent;
	// Constructor that takes only a parent frame
	@SuppressWarnings("unchecked")
	WeekView(JFrame parent){
		first = false;
		// Sets look to that of the OS
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e) {

		}
		catch (InstantiationException e) {

		}
		catch (IllegalAccessException e) {

		}
		catch (UnsupportedLookAndFeelException e) {

		}
		// Setting up of basic calendar attributes
		theParent = parent;
		mainFrame = new JFrame("Week View");
		month = new JLabel("Week of September 29th");
		month.setFont(new Font("Serif", Font.PLAIN, 18));
		prev = new JButton("<-");
		next = new JButton("->");
		backToMain = new JButton("Return to Menu");
		calendarTable = new DefaultTableModel()
		{
			public boolean isCellEditable(int rowIndex, int mColIndex)
			{
				return false;
			}
		};
		theCalendar = new JTable(calendarTable);
		calendarScroll = new JScrollPane(theCalendar);
		calendarPanel = new JPanel(null);
		mainFrame.setSize(900,750);
		thePane = mainFrame.getContentPane();
		thePane.setLayout(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
		thePane.add(calendarPanel);
		calendarPanel.add(month);
		calendarPanel.add(prev);
		calendarPanel.add(next);
		calendarPanel.add(calendarScroll);
		calendarPanel.add(backToMain);
		// Sets bounds of many attributes of the calendar
		calendarPanel.setBounds(0, 0, 873, 670);
		month.setBounds(320-month.getPreferredSize().width/2, 50, 200, 50);
		prev.setBounds(20, 50, 100, 50);
		next.setBounds(721, 50, 100, 50);
		calendarScroll.setBounds(20, 100, 800, 500);
		backToMain.setBounds(345, 610, 160, 40);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		// Creates calendar to allow for correct day population
		GregorianCalendar gregCal = new GregorianCalendar();
		theDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
		theMonth = gregCal.get(GregorianCalendar.MONTH);
		theYear = gregCal.get(GregorianCalendar.YEAR);
		otherDay = theDay;
		otherMonth = theMonth;
		otherYear = theYear;
		// Array that holds days of the week
		theCalendar.setFont(new Font("Serif", Font.PLAIN, 18));
		String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
		for (int i = 0; i < 7; i++){
			calendarTable.addColumn(days[i]);
		}
		// More various settings
		theCalendar.getParent().setBackground(theCalendar.getBackground());
		theCalendar.getTableHeader().setResizingAllowed(false);
		theCalendar.getTableHeader().setReorderingAllowed(false);
		theCalendar.setColumnSelectionAllowed(true);
		theCalendar.setRowSelectionAllowed(true);
		theCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		theCalendar.setRowHeight(456);
		calendarTable.setColumnCount(7);
		calendarTable.setRowCount(1);
		// Adds listeners to the week view
		prev.addActionListener(new prevWeek());
		next.addActionListener(new nextWeek());
		backToMain.addActionListener(new backToMenu());
		final StoreData data = new StoreData();
		// Listener that is added to each cell for a day
		theCalendar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent clicked) {
				SendToDB newRun = new SendToDB();
				int row = theCalendar.rowAtPoint(clicked.getPoint());
				int col = theCalendar.columnAtPoint(clicked.getPoint());
				if (row >= 0 && col >= 0) {
					String selectedData = null;
					selectedData = (String) theCalendar.getValueAt(row, col);
					selectedData = selectedData.substring(6, 8);
					data.setDate(""+otherMonth+"-"+selectedData+"-"+otherYear);
					newRun.runStore(data, 5);
					// Shows the empty day dialog
					if(data.getSingleDay().size() == 0){
						EmptyDay newEmpty = new EmptyDay(data,theParent,data.getSingleDay().size());
						// Shows the events for a day
					}else{
						DayView newDay = new DayView(data,theParent,data.getSingleDay().size());
					}
				}
			}
		});
		updateCalendar(theDay, theMonth, theYear);
	}
	/*
	 * Updates the calendar when a new week is switched to
	 */
	public static void updateCalendar(int aDay, int aMonth, int aYear){
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		// Sets the first week to be the current week that the user is living in
		if (first == false){
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			DateFormat someDay = new SimpleDateFormat("dd");
			DateFormat someMonth = new SimpleDateFormat("MM");
			DateFormat someYear = new SimpleDateFormat("yyyy");
			otherDay = Integer.parseInt(someDay.format(cal.getTime()));
			otherMonth = Integer.parseInt(someMonth.format(cal.getTime()));
			otherYear = Integer.parseInt(someYear.format(cal.getTime()));
			month.setText("Week of: " + df.format(cal.getTime())); 
		}
		// Sets week according to what should be displayed
		else{
			month.setText("Week of: " + aMonth + "/" + aDay + "/" + aYear);
		}
		prev.setEnabled(true); 
		next.setEnabled(true);
		month.setBounds(417-month.getPreferredSize().width/2, 50, 360, 50); 
		generateWeekDays();
		// Puts days and events at the top of the cell
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setVerticalAlignment(JLabel.TOP);
		for (int i = 0; i < 7; i++){
			theCalendar.getColumnModel().getColumn(i).setCellRenderer(render);
		}

	}
	/*
	 * This method populates the week view with the events that occur on each day.
	 */
	private static void generateWeekDays() {
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
			System.out.println("Now Connected, so please stay and look around!");


		} else {
			System.out.println("Failed to make a connection!");
		}

		final StoreData data = new StoreData();
		final SendToDB getData = new SendToDB();
		String monthActual;
		String dayActual;
		// Sets days correctly for the given week
		for (int j = 0; j < 7; j++){
			if(otherMonth < 10){
				monthActual = "0"+otherMonth;
			}else{
				monthActual = ""+otherMonth;
			}
			if(otherDay < 10){
				dayActual = "0"+otherDay;
			}else{
				dayActual = ""+otherDay;
			}
			data.setDate(monthActual+"-"+dayActual+"-"+otherYear);
			String stringA;
			// Appends a zero to a day less than 10
			if (otherDay < 10){
				stringA = "0" + otherDay;
			}
			else{
				stringA = "" + otherDay;
			}
			String curDate = data.getDate();
			getData.getSpecificData(connection, data,0);
			String template = "<html>%s<br>%s<br>%s<br>%s<html>";
			// Covers multiday events in the weekly view
			for(int k = 0; k < data.getMultiDay().size(); k++){
				if(data.getMultiDay().get(k).getEndDate().equals(curDate)){
					data.addDayEvent(data.getMultiDay().get(k));
					data.removeData(data.getMultiDay().get(k));
					k--;
				}
			}
			ArrayList<String> theNames = data.getNames();
			// Here the events are added to each day based on the number of events on that day
			if(theNames.size() == 1){
				String put = String.format(template, stringA, theNames.get(0), "","");
				calendarTable.setValueAt(put, 0, j);
			}else if(theNames.size() == 2){
				String put = String.format(template, stringA, theNames.get(0), theNames.get(1),"");
				calendarTable.setValueAt(put, 0, j);
			}else if(theNames.size() >= 3){
				String put = String.format(template, stringA, theNames.get(0), theNames.get(1), theNames.get(2));
				calendarTable.setValueAt(put, 0, j);
			}else{
				String put = String.format(template, stringA,"","","");
				calendarTable.setValueAt(put, 0, j);
			}

			data.setDate(null);
			data.resetNames();
			// Math used to correctly increment to the next or previous week 
			Calendar cal = Calendar.getInstance();
			cal.set(otherYear ,otherMonth -1, otherDay);
			cal.add(Calendar.DATE, 1);
			DateFormat someDay = new SimpleDateFormat("dd");
			DateFormat someMonth = new SimpleDateFormat("MM");
			DateFormat someYear = new SimpleDateFormat("yyyy");
			otherDay = Integer.parseInt(someDay.format(cal.getTime()));
			otherMonth = Integer.parseInt(someMonth.format(cal.getTime()));
			otherYear = Integer.parseInt(someYear.format(cal.getTime()));
			//calendarTable.setValueAt(template, 0, j);
		}
		otherDay -= 7;
	}
	/*
	 * Class that allows for moving to the previous week
	 */
	static class prevWeek implements ActionListener{
		public void actionPerformed (ActionEvent e){
			Calendar cal = Calendar.getInstance();
			// Sets back to Sunday for appropriate population of view
			if (first == false){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				cal.add(Calendar.DATE, -7);
			}
			// Sets appropriately from there on out
			else{
				cal.set(otherYear, otherMonth-1, otherDay);
				cal.add(Calendar.DATE, -7);
			}
			// Grabs current week info
			first = true;
			DateFormat day = new SimpleDateFormat("dd");
			DateFormat month = new SimpleDateFormat("MM");
			DateFormat year = new SimpleDateFormat("yyyy");
			otherDay = Integer.parseInt(day.format(cal.getTime()));
			otherMonth = Integer.parseInt(month.format(cal.getTime()));
			otherYear = Integer.parseInt(year.format(cal.getTime()));
			updateCalendar(otherDay, otherMonth, otherYear);
		}
	}
	/*
	 * Class that allows for moving to the next week
	 */
	static class nextWeek implements ActionListener{
		public void actionPerformed (ActionEvent e){
			Calendar cal = Calendar.getInstance();
			// Sets to Sunday first time for appropriate population of view
			if (first == false){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				cal.add(Calendar.DATE, +7);
			}
			// Sets appropriately from there on out
			else{
				cal.set(otherYear, otherMonth-1, otherDay);
				cal.add(Calendar.DATE, +7);
				DateFormat theFuck = new SimpleDateFormat("MM/dd/yyyy");
				System.out.println(theFuck.format(cal.getTime()));
			}
			// Grabs current week info
			first = true;
			DateFormat day = new SimpleDateFormat("dd");
			DateFormat month = new SimpleDateFormat("MM");
			DateFormat year = new SimpleDateFormat("yyyy");
			otherDay = Integer.parseInt(day.format(cal.getTime()));
			otherMonth = Integer.parseInt(month.format(cal.getTime()));
			otherYear = Integer.parseInt(year.format(cal.getTime()));
			updateCalendar(otherDay, otherMonth, otherYear);
		}
	}
	/*
	 * Class that allows for returning to the main menu
	 */
	static class backToMenu implements ActionListener{
		public void actionPerformed (ActionEvent e){
			mainFrame.dispose();
			theParent.setVisible(true);
		}
	}

}
