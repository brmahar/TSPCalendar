import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewEvent extends JFrame {

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
	
	ViewEvent(StoreData viewData, final JFrame parent){
		JLabel combine = new JLabel("Title: " + viewData.getName());
		combine.setFont(new Font("Serif", Font.PLAIN, 22));
		nameL = combine;
		dateL = new JLabel("Start Date: " + viewData.getDate());
		endDateL = new JLabel("End Date: " + viewData.getEndDate());
		fromL = new JLabel("Starting Time: "+ viewData.getSTime());
		toL = new JLabel("Ending Time: "+ viewData.getETime());
		descripL = new JLabel("Description: "+viewData.getDescription());
		locationL = new JLabel("Location: "+viewData.getLocation());
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisThing.dispose();
				parent.setVisible(true);
			}
		});
		
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new GridBagLayout());
		GridBagConstraints s = new GridBagConstraints();
		s.anchor = GridBagConstraints.NORTH;
		s.gridx = 0;
		s.gridy = 0;
		eventPanel.add(nameL, s);
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
	
	void addConfirmListener(ActionListener listenForConfirm){
		close.addActionListener(listenForConfirm);
	}
	
}
