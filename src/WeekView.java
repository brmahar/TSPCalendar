import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeekView {

	static JLabel month;
	static JButton prev;
	static JButton next;
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

	@SuppressWarnings("unchecked")
	WeekView(){
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

		mainFrame = new JFrame("Week View");
		month = new JLabel("Week of September 29th");
		prev = new JButton("<-");
		next = new JButton("->");
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

		mainFrame.setSize(660,750);
		thePane = mainFrame.getContentPane();
		thePane.setLayout(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));

		thePane.add(calendarPanel);
		calendarPanel.add(month);
		calendarPanel.add(prev);
		calendarPanel.add(next);
		calendarPanel.add(calendarScroll);

		calendarPanel.setBounds(0, 0, 640, 670);
		month.setBounds(320-month.getPreferredSize().width/2, 50, 200, 50);
		prev.setBounds(20, 50, 100, 50);
		next.setBounds(520, 50, 100, 50);
		calendarScroll.setBounds(20, 100, 600, 500);

		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		GregorianCalendar gregCal = new GregorianCalendar();
		theDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
		theMonth = gregCal.get(GregorianCalendar.MONTH);
		theYear = gregCal.get(GregorianCalendar.YEAR);
		otherDay = theDay;
		otherMonth = theMonth;
		otherYear = theYear;

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
			month.setText("Week of: " + df.format(cal.getTime())); 
		}
		else{
			month.setText("Week of: 0" + aMonth + "/" + aDay + "/" + aYear);
		}
		
		
		prev.setEnabled(true); 
		next.setEnabled(true);

		month.setBounds(320-month.getPreferredSize().width/2, 50, 360, 50); 

		

		for (int j = 0; j < 7; j++){
			String template = "<html>Event<br>Event<br>Event<html>";
			calendarTable.setValueAt(template, 0, j);
		}

		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setVerticalAlignment(JLabel.TOP);

		for (int i = 0; i < 7; i++){
			theCalendar.getColumnModel().getColumn(i).setCellRenderer(render);
		}
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
			System.out.print(otherMonth + "-" + otherDay + "-" + otherYear);
			System.out.println();
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
			System.out.print(otherMonth + "-" + otherDay + "-" + otherYear);
			System.out.println();
			updateCalendar(otherDay, otherMonth, otherYear);
		}
	}

}
