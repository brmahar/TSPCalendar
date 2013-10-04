import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;


public class RepeatingView extends JFrame {
	
	private JCheckBox sunday;
	private JCheckBox monday; 
	private JCheckBox tuesday;
	private JCheckBox wednesday;
	private JCheckBox thursday; 
	private JCheckBox friday;
	private JCheckBox saturday;
	private JButton send;
	private JLabel weekly;
	private JPanel checkPanel;
	private JScrollPane mainPane;
	private GridBagLayout grid;
	private GridBagConstraints s = new GridBagConstraints();
	private JFrame thisThing = this;
	private int[] dayArray = {0,0,0,0,0,0,0};
	
	RepeatingView(final StoreData data, final JFrame parent){
		
		grid = new GridBagLayout();
		checkPanel = new JPanel();
		checkPanel.setLayout(grid);
		mainPane  = new JScrollPane(checkPanel);
		weekly = new JLabel("Weekly");
		sunday = new JCheckBox("Sunday");
		monday = new JCheckBox("Monday");
		tuesday = new JCheckBox("Tuesday");
		wednesday = new JCheckBox("Wednesday");
		thursday = new JCheckBox("Thursday");
		friday = new JCheckBox("Friday");
		saturday = new JCheckBox("Saturday");
		send = new JButton("Confirm Event");
		s.anchor = GridBagConstraints.WEST;
		s.gridx = 0;
		s.gridy = 0;
		checkPanel.add(weekly, s);
		s.gridx = 0;
		s.gridy = 1;
		checkPanel.add(sunday, s);
		s.gridx = 0;
		s.gridy = 2;
		checkPanel.add(monday, s);
		s.gridx = 0;
		s.gridy = 3;
		checkPanel.add(tuesday, s);
		s.gridx = 0;
		s.gridy = 4;
		checkPanel.add(wednesday, s);
		s.gridx = 0;
		s.gridy = 5;
		checkPanel.add(thursday, s);
		s.gridx = 0;
		s.gridy = 6;
		checkPanel.add(friday, s);
		s.gridx = 0;
		s.gridy = 7;
		checkPanel.add(saturday, s);
		s.gridx = 0;
		s.gridy = 8;
		checkPanel.add(send, s);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(checkPanel);
		this.setSize(450,400);		
		this.setVisible(true);
		this.setResizable(false);
		
		send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisThing.setVisible(false);
				if (sunday.isSelected()){
					dayArray[0] = 1;
				}
				if (monday.isSelected()){
					dayArray[1] = 1;
				}
				if (tuesday.isSelected()){
					dayArray[2] = 1;
				}
				if (wednesday.isSelected()){
					dayArray[3] = 1;
				}
				if (thursday.isSelected()){
					dayArray[4] = 1;
				}
				if (friday.isSelected()){
					dayArray[5] = 1;
				}
				if (saturday.isSelected()){
					dayArray[6] = 1;
				}
				AddEvent theView = new AddEvent(data, parent, dayArray);
				theView.setVisible(true);
			}
		});
	}
	
}
