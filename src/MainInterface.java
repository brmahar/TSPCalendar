import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * This class is the main interface from which the user can add, edit, and delete new events.
 * They can also move to the weekly or monthly view.
 *
 */
@SuppressWarnings("serial")
public class MainInterface extends JFrame{
	// Class variables used for setting up GUI
	private JLabel title = new JLabel("Main Menu");
	private JTextField addName = new JTextField(15);
	private JButton addEvent = new JButton("Add Event");
	private JTextField editName = new JTextField(15);
	private JButton editEvent = new JButton("Edit Event");
	private JTextField deleteName = new JTextField(15);
	private JButton deleteEvent = new JButton("Delete Event");
	private JFrame thisFrame = this;
	private JButton monthCal = new JButton("Month View");
	private JButton weeklyCal = new JButton("Weekly View");
	private JCheckBox check = new JCheckBox("Repeated");
	// Constructor for the main interface
	MainInterface(){
		// Adds a listener for adding a new event
		addEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				// Catches a missing event name
				if (addName.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please enter an event name");
				}
				else{
					thisFrame.setVisible(false);
					StoreData storeName = new StoreData();
					storeName.setName(addName.getText());
					addName.setText("");
					// Sends the user to create a recurring event
					if (check.isSelected()){
						RepeatingView check = new RepeatingView(storeName, thisFrame);
						check.setVisible(true);
					}
					// Sends the user to create a normal event
					else{
						AddEvent add = new AddEvent(storeName, thisFrame);
						add.setVisible(true);
					}
				}
			}
		});
		// Adds a listener for editing an event
		editEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				// Catches a missing event name
				if (editName.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please enter an event name");
				}
				// Sends to user off to edit an event by name
				else{
					thisFrame.setVisible(false);
					StoreData storeName = new StoreData();
					storeName.setName(editName.getText());
					storeName.setLocation("12345");
					SendToDB editDB = new SendToDB();
					editDB.runStore(storeName, 6);
					editName.setText("");
					EditDayView edit = new EditDayView(storeName, thisFrame, storeName.getSingleDay().size());
					edit.setVisible(true);
				}
			}
		});
		// Adds a listener for deleting an event
		deleteEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				// Catches a missing event name
				if (deleteName.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please enter an event name");
				}
				// Sends the user off to delete an event by name
				else{
					thisFrame.setVisible(false);
					StoreData storeName = new StoreData();
					storeName.setName(deleteName.getText());
					SendToDB deleteDB = new SendToDB();
					deleteDB.runStore(storeName, 6);
					deleteName.setText("");
					DeleteDayView delete = new DeleteDayView(storeName, thisFrame, storeName.getSingleDay().size());
					delete.setVisible(true);
				}
			}
		});
		// Adds a listener that sends the user off to the month view
		monthCal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisFrame.setVisible(false);
				TheCalendar cal = new TheCalendar(thisFrame);
			}
		});
		// Adds a listener that sends the user off to the weekly view
		weeklyCal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisFrame.setVisible(false);
				WeekView theView = new WeekView(thisFrame);
			}
		});
		// JPanel that uses a GridBagLayout that houses the main interface
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		JPanel eventPanel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		eventPanel.setLayout(layout);
		GridBagConstraints s = new GridBagConstraints();
		s.anchor = GridBagConstraints.NORTH;
		s.gridx = 2;
		s.gridy = 0;
		eventPanel.add(title, s);
		s.gridx = 2;
		s.gridy = 1;
		eventPanel.add(addName, s);
		s.gridx = 2;
		s.gridy = 2;
		eventPanel.add(addEvent, s);
		s.gridx = 3;
		s.gridy = 2;
		eventPanel.add(check, s);
		s.gridx = 2;
		s.gridy = 3;
		eventPanel.add(editName, s);
		s.gridx = 2;
		s.gridy = 4;
		eventPanel.add(editEvent, s);
		s.gridx = 2;
		s.gridy = 5;
		eventPanel.add(deleteName, s);
		s.gridx = 2;
		s.gridy = 6;
		eventPanel.add(deleteEvent, s);
		s.gridx = 3;
		s.gridy = 7;
		eventPanel.add(monthCal, s);
		s.gridx = 1;
		s.gridy = 7;
		eventPanel.add(weeklyCal, s);

		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(eventPanel);
		this.setSize(450,400);		
		this.setVisible(true);
		this.setResizable(false);

	}
	/*
	 * Returns the name of an event to be added
	 */
	public String getAddEvent(){
		return addName.getText();
	}
	/*
	 * Returns the name of an event to be edited
	 */
	public String getEditEvent(){
		return editName.getText();
	}
	/*
	 * Returns the name of an event to be deleted
	 */
	public String getDeleteName(){
		return deleteName.getText();
	}
	/*
	 * Adds a listener for adding an event
	 */
	void addAddListener(ActionListener listenForAdd){
		addEvent.addActionListener(listenForAdd);
	}
	/*
	 * Adds a listener for editing an event
	 */
	void addEditListener(ActionListener listenForEdit){
		editEvent.addActionListener(listenForEdit);
	}
	/*
	 * Adds a listener for deleting an event
	 */
	void addDeleteListener(ActionListener listenForDelete){
		deleteEvent.addActionListener(listenForDelete);
	}

}
