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
 * This class is the single day view that is shown when an event is clicked on in the weekly or monthly view.
 *
 */
public class DayView extends JFrame {

	// Class variables for GUI
	private JLabel view;
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

	// Constructor that accepts a StoreData, parent frame, and the number of events being viewed
	DayView(StoreData viewData, final JFrame parent, int numOfEvents){
		layout = new GridBagLayout();
		GridBagConstraints con = new GridBagConstraints();
		scroll = new JScrollPane(masterPane);
		masterPane.setLayout(layout);
		this.add(scroll, BorderLayout.CENTER);

		// Listener for closing the view
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisThing.dispose();
			}
		});
		
		// Adds a new JPanel for each event that will be viewed.
		for (int i = 0; i < numOfEvents; i++){
			// Sets values for event details
			JLabel combine = new JLabel("Title: " + viewData.getSingleDay().get(i).getName());
			nameL = combine;
			duration = new JLabel("Duration: ");
			descripL = new JLabel("Description: "+viewData.getSingleDay().get(i).getDescription() + "");
			locationL = new JLabel("Location: "+viewData.getSingleDay().get(i).getLocation());
			// Sets fonts for each detail
			combine.setFont(new Font("Serif", Font.PLAIN, 52));
			nameL.setFont(new Font("Serif", Font.PLAIN, 46));
			duration.setFont(new Font("Serif", Font.PLAIN, 46));
			descripL.setFont(new Font("Serif", Font.PLAIN, 46));
			locationL.setFont(new Font("Serif", Font.PLAIN, 46));
			space.setFont(new Font("Serif", Font.PLAIN, 46));
			// Creates separator between each event
			JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
			sep.setPreferredSize(new Dimension(300,10));
			// Creates a new JPanel with a GridBagLayout for each new event
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
			s.weightx = 1;
			eventPanel.add(sep, s);
			con.gridx = 0;
			con.gridy = i;

			masterPane.add(eventPanel, con);
			masterPane.revalidate();
			masterPane.repaint();

		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,550);
		this.setVisible(true);
		this.setResizable(false);
	}
	/*
	 * Adds a listener for the button that closes the view
	 */
	void addConfirmListener(ActionListener listenForConfirm){
		close.addActionListener(listenForConfirm);
	}

}
