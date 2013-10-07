import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Shows all existing information about an invent. Events are searched for by name.
 *
 */
public class ViewEvent extends JFrame {

	// Class variables used to set up GUI
	private JLabel view;
	private JLabel nameL = new JLabel("Title: ");
	private JLabel dateL = new JLabel("Starting Date: ");
	private JLabel endDateL = new JLabel("Ending Date: ");
	private JLabel fromL = new JLabel("Starting Time: ");
	private JLabel toL = new JLabel("Ending Time: ");
	private JLabel descripL = new JLabel("Description: ");
	private JLabel locationL = new JLabel("Location: ");
	private JButton close = new JButton("Exit View");
	private JFrame thisThing = this;

	// Constructor that takes a StoreData and a parent frame
	ViewEvent(StoreData viewData, final JFrame parent){
		// Sets font and text for all text fields populated with event details
		JLabel combine = new JLabel(viewData.getName());
		nameL = combine;
		dateL = new JLabel("Start Date: " + viewData.getDate());
		endDateL = new JLabel("End Date: " + viewData.getEndDate());
		fromL = new JLabel("Starting Time: "+ viewData.getSTime());
		toL = new JLabel("Ending Time: "+ viewData.getETime());
		descripL = new JLabel("Description: "+viewData.getDescription());
		locationL = new JLabel("Location: "+viewData.getLocation());
		combine.setFont(new Font("Serif", Font.PLAIN, 46));
		dateL.setFont(new Font("Serif", Font.PLAIN, 34));
		endDateL.setFont(new Font("Serif", Font.PLAIN, 34));
		fromL.setFont(new Font("Serif", Font.PLAIN, 34));
		toL.setFont(new Font("Serif", Font.PLAIN, 34));
		descripL.setFont(new Font("Serif", Font.PLAIN, 34));
		locationL.setFont(new Font("Serif", Font.PLAIN, 34));
		// Listener for closing the view event window
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisThing.dispose();
				parent.setVisible(true);
			}
		});

		// JPanel with GridBagLayout that displays an event and it's details
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		GridBagConstraints s = new GridBagConstraints();
		s.anchor = GridBagConstraints.NORTH;
		s.gridx = 0;
		s.gridy = 0;
		eventPanel.add(combine, s);
		s.gridx = 0;
		s.gridy = 1;
		eventPanel.add(dateL, s);
		s.gridx = 0;
		s.gridy = 2;
		eventPanel.add(endDateL, s);
		s.gridx = 0;
		s.gridy = 3;
		eventPanel.add(fromL, s);
		s.gridx = 0;
		s.gridy = 4;
		eventPanel.add(toL, s);
		s.gridx = 0;
		s.gridy = 5;
		eventPanel.add(descripL, s);
		s.gridx = 0;
		s.gridy = 6;
		eventPanel.add(locationL, s);
		s.gridx = 0;
		s.gridy = 7;
		eventPanel.add(close, s);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(eventPanel);
		this.setSize(450,400);
		this.setVisible(true);
		this.setResizable(false);

	}
	/*
	 * Adds a listener for the confirmation button
	 */
	void addConfirmListener(ActionListener listenForConfirm){
		close.addActionListener(listenForConfirm);
	}

}
