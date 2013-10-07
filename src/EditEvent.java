import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * GUI for entering changes to an already existent event. Searches by name to reach this window.
 *
 */
public class EditEvent extends JFrame {

	// Class variables for GUI
	private JLabel startDateL = new JLabel("Start Date (mm-dd-yyyy)");
	private JTextField startDate = new JTextField(15);
	private JLabel endDateL = new JLabel("End Date (mm-dd-yyyy)");
	private JTextField endDate = new JTextField(15);
	private JLabel fromL = new JLabel("Starting Time");
	private JTextField from = new JTextField(15);
	private JLabel toL = new JLabel("Ending Time");
	private JTextField to = new JTextField(15);
	private JLabel descripL = new JLabel("Description");
	private JTextField descrip = new JTextField(15);
	private JLabel locationL = new JLabel("Location");
	private JTextField location = new JTextField(15);
	private JButton submit = new JButton("Confirm Event");
	private JButton backButton = new JButton("Back");
	private JFrame thisThing = this;

	// Constructor that accepts a StoreData and a parent frame
	EditEvent(StoreData data, final JFrame parent){
		final StoreData storeData = data;
		// Sets all attributes of the event to be added
		startDate.setText(storeData.getDate());
		endDate.setText(storeData.getEndDate());
		from.setText(storeData.getSTime());
		to.setText(storeData.getETime());
		descrip.setText(storeData.getDescription());
		location.setText(storeData.getLocation());

		// Adds listener for submitting event detail changes
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				// Sets all attributes of event
				storeData.setLocation(getLoca());
				storeData.setDate(getDate());
				storeData.setSTime(getStartTime());
				storeData.setETime(getEndTime());
				storeData.setDescription(getDescription());
				SendToDB storeDB = new SendToDB();
				storeDB.runStore(storeData, 7);
				thisThing.dispose();
				parent.setVisible(true);
			}
		});
		// Listener for back button
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisThing.dispose();
				parent.setVisible(true);
			}
		});
		// JPanel with GridBagLayout that sets up UI and accepts input
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		GridBagConstraints s = new GridBagConstraints();
		s.anchor = GridBagConstraints.NORTH;
		s.gridx = 0;
		s.gridy = 0;
		eventPanel.add(startDateL, s);
		s.gridx = 0;
		s.gridy = 1;
		eventPanel.add(startDate, s);
		s.gridx = 0;
		s.gridy = 2;
		eventPanel.add(endDateL, s);
		s.gridx = 0;
		s.gridy = 3;
		eventPanel.add(endDate, s);
		s.gridx = 0;
		s.gridy = 4;
		eventPanel.add(fromL, s);
		s.gridx = 0;
		s.gridy = 5;
		eventPanel.add(from, s);
		s.gridx = 0;
		s.gridy = 6;
		eventPanel.add(toL, s);
		s.gridx = 0;
		s.gridy = 7;
		eventPanel.add(to, s);
		s.gridx = 0;
		s.gridy = 8;
		eventPanel.add(descripL, s);
		s.gridx = 0;
		s.gridy = 9;
		eventPanel.add(descrip, s);
		s.gridx = 0;
		s.gridy = 10;
		eventPanel.add(locationL, s);
		s.gridx = 0;
		s.gridy = 11;
		eventPanel.add(location, s);
		s.gridx = 0;
		s.gridy = 12;
		eventPanel.add(submit, s);
		s.gridx = 0;
		s.gridy = 13;
		eventPanel.add(backButton, s);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(eventPanel);
		this.setSize(450,400);
		this.setVisible(true);
		this.setResizable(false);

	}
	/*
	 * Returns start date
	 */
	public String getDate(){
		return startDate.getText();
	}
	/*
	 * Returns end date
	 */
	public String getEndDate(){
		return endDate.getText();
	}
	/*
	 * Returns start time
	 */
	public String getStartTime(){
		return from.getText();
	}
	/*
	 * Returns end time
	 */
	public String getEndTime(){
		return to.getText();
	}
	/*
	 * Returns description
	 */
	public String getDescription(){
		return descrip.getText();
	}
	/*
	 * Returns location
	 */
	public String getLoca(){
		return location.getText();
	}
	/*
	 * Add listener for confirmation button
	 */
	void addConfirmListener(ActionListener listenForConfirm){
		submit.addActionListener(listenForConfirm);
	}

}
