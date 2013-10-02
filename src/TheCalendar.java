import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TheCalendar {

	static JLabel month;
	static JLabel year;
	static JButton prev;
	static JButton next;
	static JTable Calendar;
	@SuppressWarnings("rawtypes")
	static JComboBox yearBox;
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
	
	@SuppressWarnings("unchecked")
	TheCalendar(){
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
	
		mainFrame = new JFrame("Calendar App");
		month = new JLabel("January");
		year = new JLabel("Change Year:");
		yearBox = new JComboBox();
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
		calendarPanel.add(year);
		calendarPanel.add(yearBox);
		calendarPanel.add(prev);
		calendarPanel.add(next);
		calendarPanel.add(calendarScroll);

		calendarPanel.setBounds(0, 0, 640, 670);
		month.setBounds(320-month.getPreferredSize().width/2, 50, 200, 50);
		year.setBounds(20, 610, 160, 40);
		yearBox.setBounds(460, 610, 160, 40);
		prev.setBounds(20, 50, 100, 50);
		next.setBounds(520, 50, 100, 50);
		calendarScroll.setBounds(20, 100, 600, 500);

		mainFrame.setResizable(false);
		mainFrame.setVisible(true);

		GregorianCalendar gregCal = new GregorianCalendar();
		theDay = gregCal.get(GregorianCalendar.DAY_OF_MONTH);
		theMonth = gregCal.get(GregorianCalendar.MONTH);
		theYear = gregCal.get(GregorianCalendar.YEAR);
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
		Calendar.setRowHeight(76);
		calendarTable.setColumnCount(7);
		calendarTable.setRowCount(6);

		for (int i = theYear-100; i < theYear+100; i++){
			yearBox.addItem(String.valueOf(i));
		}

		prev.addActionListener(new btnPrev_Action());
		next.addActionListener(new btnNext_Action());
		yearBox.addActionListener(new cmbYear_Action());

	}

	public static void RefreshCalendar(int aMonth, int aYear){
		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; 
		prev.setEnabled(true); 
		next.setEnabled(true);
		if (aMonth == 0 && aYear <= theYear-10){prev.setEnabled(false);} 
		if (aMonth == 11 && aYear >= theYear+100){next.setEnabled(false);} 
		month.setText(months[aMonth]); 
		month.setBounds(320-month.getPreferredSize().width/2, 50, 360, 50); 
		yearBox.setSelectedItem(String.valueOf(aYear)); 

		GregorianCalendar gregCal = new GregorianCalendar(aYear, aMonth, 1);
		nod = gregCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = gregCal.get(GregorianCalendar.DAY_OF_WEEK);

		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				calendarTable.setValueAt(null, i, j);
			}
		}

		for (int i = 1; i <= nod; i++){
			int row = new Integer((i+som-2)/7);
			int column = (i+som-2)%7;
			calendarTable.setValueAt(i, row, column);
		}
	}

	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			//System.out.println("going back");
			if (otherMonth == 0){ //Back one year
				otherMonth = 11;
				otherYear -= 1;
			}
			else{ //Back one month
				otherMonth -= 1;
			}
			RefreshCalendar(otherMonth, otherYear);
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (otherMonth == 11){ //Foward one year
				otherMonth = 0;
				otherYear += 1;
			}
			else{ //Foward one month
				otherMonth += 1;
			}
			RefreshCalendar(otherMonth, otherYear);
		}
	}
	static class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (yearBox.getSelectedItem() != null){
				String b = yearBox.getSelectedItem().toString();
				otherYear = Integer.parseInt(b);
				RefreshCalendar(otherMonth, otherYear);
			}
		}
	}

}
