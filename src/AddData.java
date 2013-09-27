import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddData extends JFrame {

	private JTextField date = new JTextField(15);
	private JTextField from = new JTextField(7);
	private JTextField to = new JTextField(7);
	private JTextField descrip = new JTextField(15);
	private JTextField location = new JTextField(15);
	private JButton submit = new JButton("Confirm Event");
	
	AddData(){
		JPanel eventPanel = new JPanel();
		this.add(eventPanel);
		this.setSize(400,400);
		
		eventPanel.add(date);
		eventPanel.add(from);
		eventPanel.add(to);
		eventPanel.add(descrip);
		eventPanel.add(location);
		eventPanel.add(submit);
		
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	public String getDate(){
		return date.getText();
	}
	
	public String getStartDate(){
		return from.getText();
	}
	
	public String getEndDate(){
		return to.getText();
	}
	
	public String getDescription(){
		return descrip.getText();
	}
	
	public String getLoca(){
		return location.getText();
	}
	
	void addConfirmListener(ActionListener listenForConfirm){
		submit.addActionListener(listenForConfirm);
	}
	
}
