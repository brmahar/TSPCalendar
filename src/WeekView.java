import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class WeekView {

	static JLabel month;
	static JButton prev;
	static JButton next;
	static JTable Calendar;
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
		Calendar = new JTable(calendarTable);
		calendarScroll = new JScrollPane(Calendar);
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

		Calendar.getParent().setBackground(Calendar.getBackground());
		Calendar.getTableHeader().setResizingAllowed(false);
		Calendar.getTableHeader().setReorderingAllowed(false);
		Calendar.setColumnSelectionAllowed(true);
		Calendar.setRowSelectionAllowed(true);
		Calendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Calendar.setRowHeight(456);
		calendarTable.setColumnCount(7);
		calendarTable.setRowCount(1);

		prev.addActionListener(new prevMonth());
		next.addActionListener(new nextMonth());

		updateCalendar(theMonth, theYear);
	}

	public static void updateCalendar(int aDay, int aMonth){
		String[] months = {"Sudday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
		int startOfWeek; 

		prev.setEnabled(true); 
		next.setEnabled(true);

		month.setText(months[aMonth]); 
		month.setBounds(320-month.getPreferredSize().width/2, 50, 360, 50); 

		//GregorianCalendar gregCal = new GregorianCalendar(aYear, aMonth, 1);
		//startOfWeek = gregCal.get(GregorianCalendar.DAY_OF_WEEK);

		for (int j = 0; j < 7; j++){
			String template = "<html>Event<br>Event<br>Event<html>";
			calendarTable.setValueAt(template, 0, j);
		}

		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setVerticalAlignment(JLabel.TOP);

		for (int i = 0; i < 7; i++){
			Calendar.getColumnModel().getColumn(i).setCellRenderer(render);
		}
	}

	static class prevMonth implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (otherMonth == 0){ 
				otherMonth = 11;
				otherYear -= 1;
			}
			else{ 
				otherMonth -= 1;
			}
			updateCalendar(otherMonth, otherYear);
		}
	}
	static class nextMonth implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (otherMonth == 11){ 
				otherMonth = 0;
				otherYear += 1;
			}
			else{ 
				otherMonth += 1;
			}
			updateCalendar(otherMonth, otherYear);
		}
	}

}
