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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddEvent extends JFrame {
	
	
	
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
	
	AddEvent(StoreData data, final JFrame parent){
		title = new JLabel("Adding Event: " + data.getName());
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		final StoreData storeData = data;
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
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(eventPanel);
		this.setSize(450,400);
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	AddEvent(final StoreData data, final JFrame parent, final int[] repeatDays){
		title = new JLabel("Adding Event: " + data.getName());
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		final StoreData storeData = data;
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
				try {
					mon = Integer.parseInt(someMonth.format(displayDate.parse(getDate())));
					day = Integer.parseInt(someDay.format(displayDate.parse(getDate())));
					newYear = Integer.parseInt(someYear.format(displayDate.parse(getDate())));
					badDay = Integer.parseInt(goodDay.format(displayDate.parse(getEndDate())));
					badMonth = Integer.parseInt(goodMonth.format(displayDate.parse(getEndDate())));
					badYear = Integer.parseInt(goodYear.format(displayDate.parse(getEndDate())));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cal.set(newYear, mon, day);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				data.resetSingle();
				for (int i = 0; i < 100000; i++){
					if (day == badDay){
						if (mon == badMonth){
							break;
						}
					}
					day = Integer.parseInt(someDay.format(cal.getTime()));
					mon = Integer.parseInt(someMonth.format(cal.getTime()));
					newYear = Integer.parseInt(someYear.format(cal.getTime()));
					String theDate = newYear + "-" + mon + "-" + day;
					//System.out.println(theDate);
					if (repeatDays[i] == 1){
						StoreData add = new StoreData();
						add.setDate(theDate);
						add.setEndDate(theDate);
						data.addDayEvent(add);
						cal.add(Calendar.DATE, 1);
					}
					else{
						cal.add(Calendar.DATE, 1);
					}
					if (i == 6){
						i = -1;
					}
				}
				for (int i = 0; i < data.getSingleDay().size(); i++){
					System.out.println(data.getSingleDay().get(i).getDate());
				}
				
				
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
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(eventPanel);
		this.setSize(450,400);
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	public String getDate(){
		return startDate.getText();
	}
	
	public String getEndDate(){
		return endDate.getText();
	}
	
	public String getStartTime(){
		return from.getText();
	}
	
	public String getEndTime(){
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
