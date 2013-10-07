import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.awt.event.*;

/**
 * 
 * This class creates the view that displays events for a while month and allows the user to view what events happen
 * on a specific day.
 *
 */
public class TheCalendar {
	// Class varaibles that are used to make the Calendar
	static JLabel month;
	static JLabel year;
	static JButton prev;
	static JButton next;
	static JButton backToMain;
	static JTable Calendar;
	@SuppressWarnings("rawtypes")
	static JComboBox yearBox;
	static JFrame mainFrame;
	static Container thePane;
	static DefaultTableModel calendarTable;
	static JScrollPane calendarScroll;
	static JPanel calendarPanel;
	// Ints used in making and populating the calendar
	static int theDay;
	static int theMonth;
	static int theYear;
	static int otherMonth;
	static int otherYear;
	private static JFrame thisFrame;
	static JFrame theParent;
	// Constructor that accepts only a parent frame
	@SuppressWarnings("unchecked")
	TheCalendar(JFrame parent){
		// Sets OS specific look to the calendar
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
		// Sets basic attributes of calendar features
		theParent = parent;
		mainFrame = new JFrame("Month View");
		month = new JLabel("January");
		month.setFont(new Font("Serif", Font.PLAIN, 18));
		year = new JLabel("Change Year:");
		yearBox = new JComboBox();
		backToMain = new JButton("Return to Menu");
		prev = new JButton("<-");
		next = new JButton("->");
		calendarTable = new DefaultTableModel()
		{
			public boolean isCellEditable(int rowIndex, int mColIndex)
			{
				return false;
			}
		};
		// Continues to set attributes and set up calendar
		Calendar = new JTable(calendarTable);
		calendarScroll = new JScrollPane(Calendar);
		calendarPanel = new JPanel(null);
		mainFrame.setSize(900,750);
		thePane = mainFrame.getContentPane();
		thePane.setLayout(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
		thePane.add(calendarPanel);
		calendarPanel.add(month);
		calendarPanel.add(year);
		calendarPanel.add(yearBox);
		calendarPanel.add(prev);
		calendarPanel.add(next);
		calendarPanel.add(calendarScroll);
		calendarPanel.add(backToMain);
		// Sets boundaries of Calendar objects
		calendarPanel.setBounds(0, 0, 873, 700);
		month.setBounds(320-month.getPreferredSize().width/2, 50, 200, 50);
		year.setBounds(20, 610, 160, 40);
		yearBox.setBounds(660, 610, 160, 40);
		prev.setBounds(20, 50, 100, 50);
		next.setBounds(721, 50, 100, 50);
		calendarScroll.setBounds(20, 100, 800, 500);
		backToMain.setBounds(340, 610, 160, 40);
		// Creates Gregorian Calendar for making calendar accurate
		GregorianCalendar gregCal = new GregorianCalendar();
		theDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
		theMonth = gregCal.get(GregorianCalendar.MONTH);
		theYear = gregCal.get(GregorianCalendar.YEAR);
		otherMonth = theMonth;
		otherYear = theYear;
		// Populates the days of the week on the calendar
		String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
		for (int i = 0; i < 7; i++){
			calendarTable.addColumn(days[i]);
		}
		// Basic settings
		Calendar.getParent().setBackground(Calendar.getBackground());
		Calendar.getTableHeader().setResizingAllowed(false);
		Calendar.getTableHeader().setReorderingAllowed(false);
		Calendar.setColumnSelectionAllowed(true);
		Calendar.setRowSelectionAllowed(true);
		Calendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Calendar.setRowHeight(76);
		calendarTable.setColumnCount(7);
		calendarTable.setRowCount(6);
		// Allows you to go forward or backward 50 years because why not
		for (int i = theYear-50; i < theYear+50; i++){
			yearBox.addItem(String.valueOf(i));
		}
		// Adds all listeners and sets up calendar to start in current month
		prev.addActionListener(new prevMonth());
		next.addActionListener(new nextMonth());
		yearBox.addActionListener(new changeYear());
		backToMain.addActionListener(new backToMenu());
		thisFrame = new MainInterface();
		thisFrame.setVisible(false);
		updateCalendar(theMonth, theYear);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		final StoreData theData = new StoreData();
		// Adds listener to each cell of the calendar
		Calendar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent clicked) {
				SendToDB newRun = new SendToDB();
				int row = Calendar.rowAtPoint(clicked.getPoint());
				int col = Calendar.columnAtPoint(clicked.getPoint());
				if (row >= 0 && col >= 0) {
					String selectedData = null;
					selectedData = (String) Calendar.getValueAt(row, col);
					selectedData = selectedData.substring(6, 8);
					theMonth++;
					theData.setDate(""+theMonth+"-"+selectedData+"-"+theYear);
					theMonth--;
					newRun.runStore(theData, 5);
				}
				if(theData.getSingleDay().size() == 0){
					EmptyDay newEmpty = new EmptyDay(theData,theParent,theData.getSingleDay().size());
				}else{
					DayView newDay = new DayView(theData,theParent,theData.getSingleDay().size());
				}
			}
		});
	}
	/*
	 * Updates the calendar when it is switched to a new month or year
	 */
	public static void updateCalendar(int aMonth, int aYear){
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int days;
		int startOfMonth; 
		prev.setEnabled(true); 
		next.setEnabled(true);
		// Prevents going backward or forward too far
		if (aMonth == 0 && aYear <= theYear-10){
			prev.setEnabled(false);
		} 
		if (aMonth == 11 && aYear >= theYear+50){
			next.setEnabled(false);
		} 
		month.setText(months[aMonth]); 
		month.setBounds(418-month.getPreferredSize().width/2, 50, 360, 50); 
		yearBox.setSelectedItem(String.valueOf(aYear)); 
		// Creates calendar for correctly adding days for a specific month
		GregorianCalendar gregCal = new GregorianCalendar(aYear, aMonth, 1);
		days = gregCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startOfMonth = gregCal.get(GregorianCalendar.DAY_OF_WEEK);
		// Adds all days to a specific month
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				calendarTable.setValueAt(null, i, j);
			}
		}
		generateDays(days, startOfMonth);
		// Sets days of the month to the top of each cell
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setVerticalAlignment(JLabel.TOP);
		for (int i = 0; i < 7; i++){
			Calendar.getColumnModel().getColumn(i).setCellRenderer(render);
		}
	}
	/*
	 * This method populates the month view with the events that are on each day of a given month
	 */
	private static void generateDays(int days, int startOfMonth) {
		//This try catch block is used to setup the driver used for connection
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		Connection connection = null;
		
		//Sets up the connection which uses the driver created above
		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://orion.csl.mtu.edu/ajbrowne","ajbrowne", "ajZ4VikY/tnI.");

		} catch (SQLException e) {
			((Throwable) e).printStackTrace();
			return;
		}
		
		//Checks to see if the connection was creates correctly
		if (connection != null) {
			System.out.println("Now Connected, so please stay and look around!");
		} else {
			System.out.println("Failed to make a connection!");
		}
		//Sets up the sendtodb and storedata objects to be used to get the data
		final SendToDB getData = new SendToDB();
		final StoreData data = new StoreData();
		//Loops through the days of the month getting the correct events for each
		//day and adding those to the actual view
		for (int i = 1; i <= days; i++){

			int row = new Integer((i+startOfMonth-2)/7);
			int column = (i+startOfMonth-2)%7;
			theMonth ++;
			//Gets the correct date with zeros in front of values less than ten
			if(i < 10){
				data.setDate(""+theMonth+"-0"+i+"-"+theYear);
			}else{
				data.setDate(""+theMonth+"-"+i+"-"+theYear);
			}
			//sets up the dates and events to print them on the view
			String curDate = data.getDate();
			theMonth--;
			String stringA;
			getData.getSpecificData(connection, data,0);
			String template = "<html>%s<br>%s<br>%s<br>%s<html>";
			//loops through the multiday array list and removes certain events if needed and adds the others back
			for(int k = 0; k < data.getMultiDay().size(); k++){
				if(data.getMultiDay().get(k).getEndDate().equals(curDate)){
					data.addDayEvent(data.getMultiDay().get(k));
					data.removeData(data.getMultiDay().get(k));
					k--;
				}
			}
			//Formats dates correctly again?
			if(i < 10){
				stringA ="0" + String.valueOf(i);
			}else{
				stringA = String.valueOf(i);
			}
			//Stores the names of the events for each day
			ArrayList<String> theNames = data.getNames();
			//Adds the events to the view
			if(theNames.size() == 1){
				String put = String.format(template, stringA, theNames.get(0), "","");
				calendarTable.setValueAt(put, row, column);
			}else if(theNames.size() == 2){
				String put = String.format(template, stringA, theNames.get(0), theNames.get(1),"");
				calendarTable.setValueAt(put, row, column);
			}else if(theNames.size() >= 3){
				String put = String.format(template, stringA, theNames.get(0), theNames.get(1), theNames.get(2));
				calendarTable.setValueAt(put, row, column);
			}else{
				String put = String.format(template, stringA,"","","");
				calendarTable.setValueAt(put, row, column);
			}
			//resets fields to be used again, just in case
			data.setDate(null);
			data.resetNames();
		}
		//Closes the connection
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("The connection was not closed.....Run away now!!!!");
			e.printStackTrace();
		}
	}
	/*
	 * Class that sets up moving a month backward
	 */
	static class prevMonth implements ActionListener{
		public void actionPerformed (ActionEvent e){
			// Moves back into a new year
			if (otherMonth == 0){ 
				otherMonth = 11;
				otherYear -= 1;
				theMonth = 11;
			}
			// Just moves back a month
			else{ 
				otherMonth -= 1;
				theMonth --;
			}
			updateCalendar(otherMonth, otherYear);
		}
	}
	/*
	 * Class that sets up moving a month forward
	 */
	static class nextMonth implements ActionListener{
		public void actionPerformed (ActionEvent e){
			// Moves forward into a new year
			if (otherMonth == 11){ 
				otherMonth = 0;
				otherYear += 1;
				theMonth = 0;
			}
			// Just moves forward a month
			else{ 
				otherMonth += 1;
				theMonth ++;
			}
			updateCalendar(otherMonth, otherYear);
		}
	}
	/*
	 * Class that allows the changing of a year without moving through the months
	 */
	static class changeYear implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (yearBox.getSelectedItem() != null){
				String b = yearBox.getSelectedItem().toString();
				otherYear = Integer.parseInt(b);
				theYear = otherYear;
				updateCalendar(otherMonth, otherYear);
			}
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
