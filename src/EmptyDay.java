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

/**
 * 
 * Warning window that pops up to alert user that now events are shown for the day that was clicked on.
 *
 */
public class EmptyDay extends JFrame{
	// Class variables for setting up GUI
	private JLabel nameL = new JLabel("Title: ");
	private JLabel duration = new JLabel("Duration: ");
	private JLabel descripL = new JLabel("Description: ");
	private JLabel locationL = new JLabel("Location: ");
	private JLabel space = new JLabel("<html><br></html>");
	private JButton close = new JButton("Exit View");
	private JFrame thisThing = this;
	private JScrollPane scroll;
	private JPanel masterPane = new JPanel();
	private GridBagLayout layout;

	// Constructor that accepts a StoreData, parent frame, and the number of events that don't exist...
	EmptyDay(StoreData viewData, final JFrame parent, int numOfEvents){
		layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		scroll = new JScrollPane(masterPane);
		masterPane.setLayout(layout);
		this.add(scroll, BorderLayout.CENTER);

		// Listener for button that sends user back to weekly/monthly view
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisThing.dispose();
			}
		});
		// Populates window with text
		JLabel combine;
		combine = new JLabel("There are" );
		nameL = combine;
		duration = new JLabel("no events on");
		descripL = new JLabel("the day you have selected.");
		locationL = new JLabel("Please select a day with events.");
		// Sets font for text.
		combine.setFont(new Font("Serif", Font.PLAIN, 52));
		nameL.setFont(new Font("Serif", Font.PLAIN, 46));
		duration.setFont(new Font("Serif", Font.PLAIN, 46));
		descripL.setFont(new Font("Serif", Font.PLAIN, 46));
		locationL.setFont(new Font("Serif", Font.PLAIN, 46));
		space.setFont(new Font("Serif", Font.PLAIN, 46));
		// Creates separator that will never exist
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		sep.setPreferredSize(new Dimension(300,10));
		// Panel that holds the text alerting the user that no events exist on selected day
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
		eventPanel.add(duration, s);
		s.gridx = 0;
		s.gridy = 2;
		eventPanel.add(descripL, s);
		s.gridx = 0;
		s.gridy = 3;
		eventPanel.add(locationL, s);
		s.gridx = 0;
		s.gridy = 4;
		eventPanel.add(close, s);
		s.gridx = 0;
		s.gridy = 5;
		eventPanel.add(space, s);
		//s.fill = GridBagConstraints.VERTICAL;
		s.weightx = 1;
		masterPane.add(eventPanel, con);
		masterPane.revalidate();
		masterPane.repaint();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,550);
		this.setVisible(true);
		this.setResizable(false);
	}
	/*
	 * Adds listener for closing window and returning to weekly/monthly view
	 */
	void addConfirmListener(ActionListener listenForConfirm){
		close.addActionListener(listenForConfirm);
	}
}
