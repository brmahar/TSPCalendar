import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * 
 * This class is used to display all events with the name of the event that the user wishes to delete.
 * The user clicks delete below that event's details and then that event is deleted.
 *
 */
public class DeleteDayView extends JFrame {

	// Class variables for setting up GUI
	private JLabel nameL = new JLabel("Title: ");
	private JLabel startTime = new JLabel("Start Time: ");
	private JLabel endTime = new JLabel("End Time: ");
	private JLabel startDate = new JLabel("Start Date: ");
	private JLabel endDate = new JLabel("End Date: ");
	private JLabel descripL = new JLabel("Description: ");
	private JLabel locationL = new JLabel("Location: ");
	private JLabel space = new JLabel("<html><br></html>");
	private JButton close = new JButton("Exit View");
	private JButton deleteThis = new JButton("Delete event");
	private JButton backButton = new JButton("Back");
	private JFrame thisThing = this;
	private JScrollPane scroll;
	private JPanel masterPane = new JPanel();
	private GridBagLayout layout;
	private StoreData sendToDelete;
	int theSize = 0;

	// Constructor that accepts a StoreData, parent frame, and the number of events to be chosen from
	DeleteDayView(final StoreData viewData, final JFrame parent, int numOfEvents){
		sendToDelete = viewData;
		layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		scroll = new JScrollPane(masterPane);
		masterPane.setLayout(layout);
		this.add(scroll, BorderLayout.CENTER);

		// Listener for the button that closes the view
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisThing.dispose();
				parent.setVisible(true);
			}
		});

		// For loop that creates a new JPanel for each event that could possibly be deleted
		for (int i = 0; i < numOfEvents; i++){
			// Sets all values for each event shown for possible deletion
			JLabel combine = new JLabel("Title: " + viewData.getSingleDay().get(i).getName());
			nameL = combine;
			deleteThis = new JButton("Delete Event");
			startTime = new JLabel("Start Time: " + viewData.getSingleDay().get(i).getSTime());
			endTime = new JLabel("End Time: "+ viewData.getSingleDay().get(i).getETime());
			startDate = new JLabel("Start Date: "+ viewData.getSingleDay().get(i).getDate());
			endDate = new JLabel("End Date: "+ viewData.getSingleDay().get(i).getEndDate());
			descripL = new JLabel("Description: "+viewData.getSingleDay().get(i).getDescription() + "");
			locationL = new JLabel("Location: "+viewData.getSingleDay().get(i).getLocation());

			// Sets fonts for all values
			combine.setFont(new Font("Serif", Font.PLAIN, 52));
			nameL.setFont(new Font("Serif", Font.PLAIN, 46));
			startTime.setFont(new Font("Serif", Font.PLAIN, 46));
			endTime.setFont(new Font("Serif", Font.PLAIN, 46));
			startDate.setFont(new Font("Serif", Font.PLAIN, 46));
			endDate.setFont(new Font("Serif", Font.PLAIN, 46));
			descripL.setFont(new Font("Serif", Font.PLAIN, 46));
			locationL.setFont(new Font("Serif", Font.PLAIN, 46));
			space.setFont(new Font("Serif", Font.PLAIN, 46));
			// Creates separator used between each event
			JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
			sep.setPreferredSize(new Dimension(300,10));
			// JPanel that is each event with a GridBagLayout
			JPanel eventPanel = new JPanel();
			eventPanel.setLayout(new GridBagLayout());
			GridBagConstraints s = new GridBagConstraints();
			s.anchor = GridBagConstraints.NORTH;
			s.insets = new Insets(0,0,5,0);
			s.gridx = 0;
			s.gridy = 0;
			eventPanel.add(nameL, s);
			s.gridx = 0;
			s.gridy = 1;
			eventPanel.add(startTime, s);
			s.gridx = 0;
			s.gridy = 2;
			eventPanel.add(endTime, s);
			s.gridx = 0;
			s.gridy = 3;
			eventPanel.add(startDate, s);
			s.gridx = 0;
			s.gridy = 4;
			eventPanel.add(endDate, s);
			s.gridx = 0;
			s.gridy = 5;
			eventPanel.add(descripL, s);
			s.gridx = 0;
			s.gridy = 6;
			eventPanel.add(locationL, s);
			s.gridx = 0;
			s.gridy = 7;
			eventPanel.add(deleteThis, s);
			s.gridx = 0;
			s.gridy = 8;
			eventPanel.add(sep, s);
			if (i == numOfEvents-1){
				s.gridx = 0;
				s.gridy = 9;
				eventPanel.add(backButton, s);
			}
			s.fill = GridBagConstraints.VERTICAL;
			// Adds each eventPanel to the parent Panel that holds all events
			con.gridx = 0;
			con.gridy = i;
			masterPane.add(eventPanel, con);
			masterPane.revalidate();
			masterPane.repaint();
			final int storeI = i;
			// Listener added to each event for the deletion of that event
			deleteThis.addActionListener(new ActionListener(){
				SendToDB newRun = new SendToDB();
				@Override
				public void actionPerformed(ActionEvent e){
					thisThing.dispose();
					sendToDelete.setID(viewData.getSingleDay().get(storeI).getID());
					newRun.runStore(sendToDelete, 2);
					thisThing.dispose();
					parent.setVisible(true);
				}
			});
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,550);
		this.setVisible(true);
		this.setResizable(false);
	}
	/*
	 * Listener for confirming desire to close window
	 */
	void addConfirmListener(ActionListener listenForConfirm){
		close.addActionListener(listenForConfirm);
	}

}
