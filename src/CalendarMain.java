import javax.swing.*;

/**
 * Calendar program that allows the user to add, edit, and delete single or recurring events. Monthly and weekly views
 * allow the user to view their schedule and make appropriate changes.
 * 
 * @author Brady Mahar
 * @author Austin Browne
 * 
 */
@SuppressWarnings("serial")
public class CalendarMain extends JFrame {
	/*
	 * Main method that spawns the main method upon running the program.
	 */
	public static void main(String[] args) {
		MainInterface theCal = new MainInterface();
		theCal.setVisible(true);

	}
}
