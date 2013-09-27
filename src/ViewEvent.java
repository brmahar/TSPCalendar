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
	private JLabel dateL = new JLabel("Date: ");
	private JLabel fromL = new JLabel("Starting Time: ");
	private JLabel toL = new JLabel("Ending Time: ");
	private JLabel descripL = new JLabel("Description: ");
	private JLabel locationL = new JLabel("Location: ");
	private JButton close = new JButton("Exit View");
	private JFrame thisThing = this;
	
	ViewEvent(StoreData viewData){
		nameL = new JLabel("Title: " + viewData.getName());
		dateL = new JLabel("Date: " + viewData.getDate());
		fromL = new JLabel("Starting Time: "+ viewData.getSTime());
		toL = new JLabel("Ending Time: "+ viewData.getETime());
		descripL = new JLabel("Description: "+viewData.getDescription());
		locationL = new JLabel("Location: "+viewData.getLocation());
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisThing.dispose();
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
		eventPanel.add(fromL, s);
		s.gridx = 0;
		s.gridy = 3;
		eventPanel.add(toL, s);
		s.gridx = 0;
		s.gridy = 4;
		eventPanel.add(descripL, s);
		s.gridx = 0;
		s.gridy = 5;
		eventPanel.add(locationL, s);
		s.gridx = 0;
		s.gridy = 6;
		eventPanel.add(close, s);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(eventPanel);
		this.setSize(300,400);
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	void addConfirmListener(ActionListener listenForConfirm){
		close.addActionListener(listenForConfirm);
	}
	
}
