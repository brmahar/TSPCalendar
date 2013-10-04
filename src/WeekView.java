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

public class WeekView {

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
	static int theDay;
	static int theMonth;
	static int theYear;
	static int otherMonth;
	static int otherYear;
	static int otherDay;
	static boolean first = false;
	static JFrame theParent;

	@SuppressWarnings("unchecked")
	WeekView(JFrame parent){
		first = false;
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

		calendarPanel.setBounds(0, 0, 873, 670);
		month.setBounds(320-month.getPreferredSize().width/2, 50, 200, 50);
		prev.setBounds(20, 50, 100, 50);
		next.setBounds(721, 50, 100, 50);
		calendarScroll.setBounds(20, 100, 800, 500);
		backToMain.setBounds(345, 610, 160, 40);

		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		GregorianCalendar gregCal = new GregorianCalendar();
		theDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
		theMonth = gregCal.get(GregorianCalendar.MONTH);
		theYear = gregCal.get(GregorianCalendar.YEAR);
		otherDay = theDay;
		otherMonth = theMonth;
		otherYear = theYear;

		theCalendar.setFont(new Font("Serif", Font.PLAIN, 18));
		String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
		for (int i = 0; i < 7; i++){
			calendarTable.addColumn(days[i]);
		}

		theCalendar.getParent().setBackground(theCalendar.getBackground());
		theCalendar.getTableHeader().setResizingAllowed(false);
		theCalendar.getTableHeader().setReorderingAllowed(false);
		theCalendar.setColumnSelectionAllowed(true);
		theCalendar.setRowSelectionAllowed(true);
		theCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		theCalendar.setRowHeight(456);
		calendarTable.setColumnCount(7);
		calendarTable.setRowCount(1);

		prev.addActionListener(new prevWeek());
		next.addActionListener(new nextWeek());
		backToMain.addActionListener(new backToMenu());
		final StoreData data = new StoreData();
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
					
					DayView newDay = new DayView(data,theParent,data.getSingleDay().size());
				}
			}
		});
		updateCalendar(theDay, theMonth, theYear);
	}

	public static void updateCalendar(int aDay, int aMonth, int aYear){
		String[] days = {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int firstDay;
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
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
		else{
			month.setText("Week of: 0" + aMonth + "/" + aDay + "/" + aYear);
		}


		prev.setEnabled(true); 
		next.setEnabled(true);

		month.setBounds(417-month.getPreferredSize().width/2, 50, 360, 50); 



		generateWeekDays();

		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setVerticalAlignment(JLabel.TOP);

		for (int i = 0; i < 7; i++){
			theCalendar.getColumnModel().getColumn(i).setCellRenderer(render);
		}
		
	}

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
		for (int j = 0; j < 7; j++){
			
			data.setDate(""+otherMonth+"-"+otherDay+"-"+otherYear);

			String stringA;
			if (otherDay < 10){
				stringA = "0" + otherDay;
			}
			else{
				stringA = "" + otherDay;
			}
			getData.getSpecificData(connection, data,0);
			ArrayList<String> theNames = data.getNames();
			String template = "<html>%s<br>%s<br>%s<br>%s<html>";
			
			if(theNames.size() == 1){
				String put = String.format(template,stringA, theNames.get(0), "","");
				calendarTable.setValueAt(put,0,j);
			}else if(theNames.size() == 2){
				String put = String.format(template,stringA, theNames.get(0), theNames.get(1),"");
				calendarTable.setValueAt(put,0,j);
			}else if(theNames.size() >= 3){
				String put = String.format(template,stringA, theNames.get(0), theNames.get(1),theNames.get(2));
				calendarTable.setValueAt(put,0,j);
			}else{
				String put = String.format(template,stringA, "", "","");
				calendarTable.setValueAt(put,0,j);
			}
			
			data.setDate(null);
			data.resetNames();
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

	static class prevWeek implements ActionListener{
		public void actionPerformed (ActionEvent e){
			Calendar cal = Calendar.getInstance();
			if (first == false){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				cal.add(Calendar.DATE, -7);
			}
			else{
				cal.set(otherYear, otherMonth-1, otherDay);
				cal.add(Calendar.DATE, -7);
			}
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
	static class nextWeek implements ActionListener{
		public void actionPerformed (ActionEvent e){
			Calendar cal = Calendar.getInstance();
			if (first == false){
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				cal.add(Calendar.DATE, +7);
			}
			else{
				cal.set(otherYear, otherMonth-1, otherDay);
				cal.add(Calendar.DATE, +7);
				DateFormat theFuck = new SimpleDateFormat("MM/dd/yyyy");
				System.out.println(theFuck.format(cal.getTime()));
			}
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
	
	static class backToMenu implements ActionListener{
		public void actionPerformed (ActionEvent e){
			mainFrame.dispose();
			theParent.setVisible(true);
		}
	}

}
