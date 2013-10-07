import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * This class is the window for adding a new event and all of its details.
 * 
 */
public class AddEvent extends JFrame {

	// Class variables used to make GUI with swing
	private JLabel title;
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
	private JFrame thisThing = this;
	private JButton backButton;

	// Default constructor for use with non recurring events
	AddEvent(StoreData data, final JFrame parent){
		backButton = new JButton("Back");
		title = new JLabel("Adding Event: " + data.getName());
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		final StoreData storeData = data;

		//Listener for confirming event details
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

				storeData.setLocation(getLoca());
				storeData.setDate(getDate());
				storeData.setEndDate(getEndDate());
				storeData.setSTime(getStartTime());
				storeData.setETime(getEndTime());
				storeData.setDescription(getDescription());
				SendToDB storeDB = new SendToDB();

				storeDB.runStore(storeData, 0);
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

		// JPanel with a GridBagLayout for entering event details
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		GridBagConstraints s = new GridBagConstraints();
		s.anchor = GridBagConstraints.NORTH;
		s.gridx = 0;
		s.gridy = 0;
		eventPanel.add(title, s);
		s.gridx = 0;
		s.gridy = 1;
		eventPanel.add(startDateL, s);
		s.gridx = 0;
		s.gridy = 2;
		eventPanel.add(startDate, s);
		s.gridx = 0;
		s.gridy = 3;
		eventPanel.add(endDateL, s);
		s.gridx = 0;
		s.gridy = 4;
		eventPanel.add(endDate, s);
		s.gridx = 0;
		s.gridy = 5;
		eventPanel.add(fromL, s);
		s.gridx = 0;
		s.gridy = 6;
		eventPanel.add(from, s);
		s.gridx = 0;
		s.gridy = 7;
		eventPanel.add(toL, s);
		s.gridx = 0;
		s.gridy = 8;
		eventPanel.add(to, s);
		s.gridx = 0;
		s.gridy = 9;
		eventPanel.add(descripL, s);
		s.gridx = 0;
		s.gridy = 10;
		eventPanel.add(descrip, s);
		s.gridx = 0;
		s.gridy = 11;
		eventPanel.add(locationL, s);
		s.gridx = 0;
		s.gridy = 12;
		eventPanel.add(location, s);
		s.gridx = 0;
		s.gridy = 13;
		eventPanel.add(submit, s);
		s.gridx = 0;
		s.gridy = 14;
		eventPanel.add(backButton, s);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(eventPanel);
		this.setSize(450,400);
		this.setVisible(true);
		this.setResizable(false);

	}

	// Secondary constructor for recurring events
	AddEvent(final StoreData data, final JFrame parent, final int[] repeatDays){
		backButton = new JButton("       Back       ");
		title = new JLabel("Adding Event: " + data.getName());
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		final StoreData storeData = data;

		// Listener for confirming event details
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int mon = 0;
				int day = 0;
				int newYear = 0;
				int badDay = 0;
				int badMonth = 0;
				int badYear = 0;
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat displayDate = new SimpleDateFormat("MM-dd-yyyy");
				DateFormat someDay = new SimpleDateFormat("dd");
				DateFormat someMonth = new SimpleDateFormat("MM");
				DateFormat someYear = new SimpleDateFormat("yyyy");
				DateFormat goodDay = new SimpleDateFormat("dd");
				DateFormat goodMonth = new SimpleDateFormat("MM");
				DateFormat goodYear = new SimpleDateFormat("yyyy");

				// Parses dates to be used for adding recurring events to the Calendar
				try {
					mon = Integer.parseInt(someMonth.format(displayDate.parse(getDate())));
					day = Integer.parseInt(someDay.format(displayDate.parse(getDate())));
					newYear = Integer.parseInt(someYear.format(displayDate.parse(getDate())));
					badDay = Integer.parseInt(goodDay.format(displayDate.parse(getEndDate())));
					badMonth = Integer.parseInt(goodMonth.format(displayDate.parse(getEndDate())));
					badYear = Integer.parseInt(goodYear.format(displayDate.parse(getEndDate())));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				Date date = null;
				try {
					date = displayDate.parse(mon + "-" + day + "-" + newYear);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cal.setTime(date);
				int start = cal.get(Calendar.DAY_OF_WEEK) - 1;	// Gets index of day in current week
				data.resetSingle();

				// Adds recurring event until end date unless it somehow goes for 100000 days 
				for (int i = start; i < 100000; i++){
					if (day == badDay){
						if (mon == badMonth){
							break;
						}
					}
					day = Integer.parseInt(someDay.format(cal.getTime()));
					mon = Integer.parseInt(someMonth.format(cal.getTime()));
					newYear = Integer.parseInt(someYear.format(cal.getTime()));
					String theDate = newYear + "-" + mon + "-" + day;
					// Adds event to correct days
					if (repeatDays[i] == 1){
						StoreData add = new StoreData();
						add.setDate(theDate);
						add.setEndDate(theDate);
						data.addDayEvent(add);
						cal.add(Calendar.DATE, 1);
					}
					// Increments the day of the week
					else{
						cal.add(Calendar.DATE, 1);
					}
					// Resets the day weekly
					if (i == 6){
						i = -1;
					}
				}

				// Sets the attributes of an event and connects to database
				storeData.setLocation(getLoca());
				storeData.setDate(getDate());
				storeData.setEndDate(getEndDate());
				storeData.setSTime(getStartTime());
				storeData.setETime(getEndTime());
				storeData.setDescription(getDescription());
				SendToDB storeDB = new SendToDB();

				storeDB.runStore(storeData, 8);
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

		// JPanel with GridBagLayout for creating GUI for adding an event
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		GridBagConstraints s = new GridBagConstraints();
		s.anchor = GridBagConstraints.NORTH;
		s.gridx = 0;
		s.gridy = 0;
		eventPanel.add(title, s);
		s.gridx = 0;
		s.gridy = 1;
		eventPanel.add(startDateL, s);
		s.gridx = 0;
		s.gridy = 2;
		eventPanel.add(startDate, s);
		s.gridx = 0;
		s.gridy = 3;
		eventPanel.add(endDateL, s);
		s.gridx = 0;
		s.gridy = 4;
		eventPanel.add(endDate, s);
		s.gridx = 0;
		s.gridy = 5;
		eventPanel.add(fromL, s);
		s.gridx = 0;
		s.gridy = 6;
		eventPanel.add(from, s);
		s.gridx = 0;
		s.gridy = 7;
		eventPanel.add(toL, s);
		s.gridx = 0;
		s.gridy = 8;
		eventPanel.add(to, s);
		s.gridx = 0;
		s.gridy = 9;
		eventPanel.add(descripL, s);
		s.gridx = 0;
		s.gridy = 10;
		eventPanel.add(descrip, s);
		s.gridx = 0;
		s.gridy = 11;
		eventPanel.add(locationL, s);
		s.gridx = 0;
		s.gridy = 12;
		eventPanel.add(location, s);
		s.gridx = 0;
		s.gridy = 13;
		eventPanel.add(submit, s);
		s.gridx = 0;
		s.gridy = 14;
		eventPanel.add(backButton, s);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(eventPanel);
		this.setSize(450,400);
		this.setVisible(true);
		this.setResizable(false);

	}
	/*
	 * Returns the start date
	 */
	public String getDate(){
		return startDate.getText();
	}
	/*
	 * Returns the end date
	 */
	public String getEndDate(){
		return endDate.getText();
	}
	/*
	 * Returns the start time
	 */
	public String getStartTime(){
		return from.getText();
	}
	/*
	 * Returns the end time
	 */
	public String getEndTime(){
		return to.getText();
	}
	/*
	 * Returns the description
	 */
	public String getDescription(){
		return descrip.getText();
	}
	/*
	 * Returns the location
	 */
	public String getLoca(){
		return location.getText();
	}
	/*
	 * Adds a listener for the confirm button
	 */
	void addConfirmListener(ActionListener listenForConfirm){
		submit.addActionListener(listenForConfirm);
	}

}
