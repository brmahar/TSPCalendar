import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;


public class RepeatingView extends JFrame {
	
	private JCheckBox sunday;
	private JCheckBox monday; 
	private JCheckBox tuesday;
	private JCheckBox wednesday;
	private JCheckBox thursday; 
	private JCheckBox friday;
	private JCheckBox saturday;
	private JTextField onceWeek;
	private JTextField onceMonth;
	private JTextField onceYear;
	private JButton send;
	private JLabel weekly;
	private JLabel onceAWeek;
	private JLabel onceAMonth;
	private JLabel onceAYear;
	private JPanel checkPanel;
	private JScrollPane mainPane;
	private GridBagLayout grid;
	private GridBagConstraints s = new GridBagConstraints();
	
	RepeatingView(){
		grid = new GridBagLayout();
		checkPanel = new JPanel();
		checkPanel.setLayout(grid);
		mainPane  = new JScrollPane(checkPanel);
		onceAWeek = new JLabel("Once a Week");
		onceWeek = new JTextField(10);
		onceAMonth = new JLabel("Once a Month");
		onceMonth = new JTextField(10);
		onceAYear = new JLabel("Once a Year");
		onceYear = new JTextField(10);
		weekly = new JLabel("Weekly");
		sunday = new JCheckBox("Sunday");
		monday = new JCheckBox("Monday");
		tuesday = new JCheckBox("Tuesday");
		wednesday = new JCheckBox("Wednesday");
		thursday = new JCheckBox("Thursday");
		friday = new JCheckBox("Friday");
		saturday = new JCheckBox("Saturday");
		send = new JButton("Confirm Event");
		
		s.gridx = 1;
		s.gridy = 0;
		checkPanel.add(weekly, s);
		s.gridx = 3;
		s.gridy = 0;
		checkPanel.add(onceAWeek, s);
		s.gridx = 1;
		s.gridy = 1;
		checkPanel.add(sunday, s);
		s.gridx = 3;
		s.gridy = 1;
		checkPanel.add(onceWeek, s);
		s.gridx = 1;
		s.gridy = 2;
		checkPanel.add(monday, s);
		s.gridx = 3;
		s.gridy = 2;
		checkPanel.add(onceAMonth, s);
		s.gridx = 1;
		s.gridy = 3;
		checkPanel.add(tuesday, s);
		s.gridx = 3;
		s.gridy = 3;
		checkPanel.add(onceMonth, s);
		s.gridx = 1;
		s.gridy = 4;
		checkPanel.add(wednesday, s);
		s.gridx = 3;
		s.gridy = 4;
		checkPanel.add(onceAYear, s);
		s.gridx = 1;
		s.gridy = 5;
		checkPanel.add(thursday, s);
		s.gridx = 3;
		s.gridy = 5;
		checkPanel.add(onceYear, s);
		s.gridx = 1;
		s.gridy = 6;
		checkPanel.add(friday, s);
		s.gridx = 1;
		s.gridy = 7;
		checkPanel.add(saturday, s);
		s.gridx = 2;
		s.gridy = 9;
		checkPanel.add(send, s);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(checkPanel);
		this.setSize(450,400);		
		this.setVisible(true);
		this.setResizable(false);
	}
	
}
