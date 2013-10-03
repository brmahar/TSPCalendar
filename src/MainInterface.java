import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


@SuppressWarnings("serial")
public class MainInterface extends JFrame{

	private JLabel title = new JLabel("Main Menu");
	private JTextField addName = new JTextField(15);
	private JButton addEvent = new JButton("Add Event");
	private JTextField editName = new JTextField(15);
	private JButton editEvent = new JButton("Edit Event");
	private JTextField deleteName = new JTextField(15);
	private JButton deleteEvent = new JButton("Delete Event");
	private JTextField viewName = new JTextField(15);
	private JButton viewEvent = new JButton("View Event");
	private JFrame thisFrame = this;
	private JButton monthCal = new JButton("Month View");
	private JButton weeklyCal = new JButton("Weekly View");
	private JCheckBox check = new JCheckBox("Repeated");

	MainInterface(){

		addEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisFrame.setVisible(false);
				StoreData storeName = new StoreData();
				storeName.setName(addName.getText());
				addName.setText("");
				if (check.isSelected()){
					System.out.println("hi");
				}
				AddEvent add = new AddEvent(storeName, thisFrame);
				add.setVisible(true);
			}
		});

		viewEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisFrame.setVisible(false);
				StoreData pullViewData = new StoreData();
				pullViewData.setName(viewName.getText());
				SendToDB storeDB = new SendToDB();
				storeDB.runStore(pullViewData, 1);
				viewName.setText("");
				ViewEvent view = new ViewEvent(pullViewData, thisFrame);
				view.setVisible(true);
			}
		});

		editEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisFrame.setVisible(false);
				StoreData storeName = new StoreData();
				storeName.setName(editName.getText());
				SendToDB editDB = new SendToDB();
				editDB.runStore(storeName, 1);
				editName.setText("");
				EditEvent edit = new EditEvent(storeName, thisFrame);
				edit.setVisible(true);
			}
		});

		deleteEvent.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				StoreData storeName = new StoreData();
				storeName.setName(deleteName.getText());
				SendToDB deleteEvent = new SendToDB();
				deleteEvent.runStore(storeName, 2);
				deleteName.setText("");

			}
		});

		monthCal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisFrame.setVisible(false);
				TheCalendar cal = new TheCalendar(thisFrame);
			}
		});
		
		weeklyCal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				thisFrame.setVisible(false);
				WeekView theView = new WeekView(thisFrame);
			}
		});

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
		s.gridx = 2;
		s.gridy = 7;
		eventPanel.add(viewName, s);
		s.gridx = 2;
		s.gridy = 8;
		eventPanel.add(viewEvent, s);
		s.gridx = 3;
		s.gridy = 10;
		eventPanel.add(monthCal, s);
		s.gridx = 1;
		s.gridy = 10;
		eventPanel.add(weeklyCal, s);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(eventPanel);
		this.setSize(450,400);		
		this.setVisible(true);
		this.setResizable(false);

	}

	public String getAddEvent(){
		return addName.getText();
	}

	public String getEditEvent(){
		return editName.getText();
	}

	public String getDeleteName(){
		return deleteName.getText();
	}

	void addAddListener(ActionListener listenForAdd){
		addEvent.addActionListener(listenForAdd);
	}

	void addEditListener(ActionListener listenForEdit){
		editEvent.addActionListener(listenForEdit);
	}

	void addDeleteListener(ActionListener listenForDelete){
		deleteEvent.addActionListener(listenForDelete);
	}

}
