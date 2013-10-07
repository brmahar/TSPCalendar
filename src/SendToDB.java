import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.UUID;

import com.mysql.jdbc.PreparedStatement;

/**
 * This class is used to connect to the database and to send or retrieve data as needed.
 * 
 * 
 * @author Brady Mahar
 *
 */
public class SendToDB {

	//This method is used to setup the actual connection with the database
	public void runStore(StoreData data, int bool){

		//This try catch block is used to setup the driver used to connect to the database
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		Connection connection = null;
		
		//This try catch block is used to connect to the database using the driver
		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://orion.csl.mtu.edu/ajbrowne","ajbrowne", "ajZ4VikY/tnI.");

		} catch (SQLException e) {
			((Throwable) e).printStackTrace();
			return;
		}
		
		//This block first checks to see which of the following methods should be used.
		if (connection != null) {
			if(bool == 1){
				getData(connection,data);
			}else if(bool == 2){
				deleteEvent(connection, data);
			}else if(bool == 3){
				getSpecificData(connection, data,0);
			}else if(bool == 5){
				getDateEvents(connection, data);
			}else if(bool == 6){
				getNameEvents(connection, data);
			}else if(bool == 4){
				getByID(connection,data);
			}else if(bool == 7){
				editEvent(connection, data);
			}else if(bool == 8){
				addRepeats(connection, data);
			}else{
				send(connection, data);
			}


		} else {
			System.out.println("Failed to make a connection!");
		}
		//This try catch block just closes the connection to the database 
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method is used to store data in the data base
	 * It stores the data that is given to it by the addEvent class
	 * 
	 * @param connection
	 * @param data
	 */
	public void send(Connection connection, StoreData data){
		//The following code prepares a statement to be sent to the database
		//and gets all of the data from a Storedata object
		PreparedStatement preStmt=null;
		StoreData theData = data;
		String name = theData.getName();
		String local = theData.getLocation();
		String startDate = theData.getDate();
		String endDate = theData.getEndDate();
		String description = theData.getDescription();
		String theSTime = theData.getSTime();
		String theETime = theData.getETime();
		String theID = UUID.randomUUID().toString();

		//This try catch block attempts to send a sql statement to the database to store the given data
		try {
			preStmt = (PreparedStatement) connection.prepareStatement("INSERT INTO "
					+ "Event(Name,Location,Description,End_Date, Start_Date, Start_Time, End_Time, id) VALUES(?,?,?,?,?,?,?,?)"); 
			java.util.Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(startDate);
			java.util.Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(endDate);
			java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(date2.getTime());
			preStmt.setString(8, theID);
			preStmt.setString(1,name);
			preStmt.setDate(4,sqlEndDate);
			preStmt.setDate(5, sqlDate);
			preStmt.setString(2,local);
			preStmt.setString(3,description);
			preStmt.setString(6, theSTime);
			preStmt.setString(7, theETime);
			preStmt.executeUpdate();
		} catch (SQLException | ParseException e) {
			System.out.println("Nothing was added.");
			e.printStackTrace();
		}

	}
	
	/**
	 * This method is used to get data from the database based on the name given
	 * 
	 * @param connection
	 * @param data
	 */
	public void getData(Connection connection, StoreData data){
		//This first set of code prepares a statement to get the data from the database
		PreparedStatement preStmt=null;
		StoreData theData = data;
		//This code prepares some date formats to convert what is typed in
		SimpleDateFormat displayDate = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat dbDate = new SimpleDateFormat("yyyy-MM-dd");
		String formatSDate = null;
		String formatEDate = null;
		//The following is the actual sql statement to be used
		String stmt = "SELECT * FROM Event WHERE Name= ?";
		
		//The following try catch block gets all of the events that have the same name as the name typed in
		//It loops through a result set that is returned from the database storing the data in store data objects
		try {
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			preStmt.setString(1, data.getName());
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()){
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String date = rs.getString("Start_Date");
				String endDate = rs.getString("End_Date");
				//This try catch block deals with formatting the date correctly 
				try {

					formatSDate = displayDate.format(dbDate.parse(date));
					formatEDate = displayDate.format(dbDate.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String sTime = rs.getString("Start_Time");
				String eTime = rs.getString("End_Time");
				data.setDescription(description);
				data.setLocation(location);
				data.setDate(formatSDate);
				data.setEndDate(formatEDate);
				data.setSTime(sTime);
				data.setETime(eTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to delete an event from the database based on name
	 * 
	 * @param connection
	 * @param data
	 */
	public void deleteEvent(Connection connection, StoreData data){
		//First a statement is prepared to send to the database to delete the events 
		PreparedStatement preStmt=null;
		String stmt = "DELETE FROM Event WHERE id=?";
		//This try catch block is used to send the delete command to the database
		try{
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			preStmt.setString(1, data.getID());
			preStmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to get an event from the database based on the 
	 * start date of the event
	 * 
	 * @param connection
	 * @param data
	 * @param bool
	 */
	public void getSpecificData(Connection connection, StoreData data, int bool){
		//This code is used to prepare a statement to send to the database based on the start date
		PreparedStatement preStmt=null;
		String stmt = "SELECT * FROM Event WHERE Start_Date = (?)";
		String formatSDate = null;
		String formatEDate = null;
		StoreData extra = new StoreData();
		
		//This try catch block is used to format the date to be sent to the database
		//The command is then sent to get the correct events
		try{
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			SimpleDateFormat displayDate = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat dbDate = new SimpleDateFormat("yyyy-MM-dd");
			//formats the date to match the database
			try {

				formatSDate = dbDate.format(displayDate.parse(data.getDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			preStmt.setString(1, formatSDate);
			ResultSet rs = preStmt.executeQuery();
			//re-adds the correct events back to the storedata object to be used later
			for(int i = 0; i < data.getMultiDay().size();i++){
				data.addName(data.getMultiDay().get(i).getName());
			}
			//loops through the result set returned by the database call
			while(rs.next()){
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String name = rs.getString("Name");
				String endDate = rs.getString("End_Date");
				String sTime = rs.getString("Start_Time");
				String eTime = rs.getString("End_Time");
				//This try catch is used to format the date correctly
				try {
					formatEDate = displayDate.format(dbDate.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				data.setEndDate(formatEDate);
				data.setName(name);
				data.addName(name);
				data.setDescription(description);
				data.setLocation(location);
				data.setSTime(sTime);
				data.setETime(eTime);

				extra.setEndDate(formatEDate);
				extra.setName(name);
				extra.setDescription(description);
				extra.setLocation(location);
				extra.setSTime(sTime);
				extra.setETime(eTime);
				//adds the event to the multi-day arraylist to be used later
				if(!data.getDate().equals(data.getEndDate())){
					data.addEvent(extra);
				}

			}
		}catch(SQLException e){
			e.printStackTrace();
		}

	}
	
	/**
	 * This method is used to get events from the database based on the date
	 * The sql command does not directly use the date
	 * 
	 * 
	 * @param connection
	 * @param data
	 */
	public void getDateEvents(Connection connection, StoreData data){
		//The following code is used to prepare a sql statement to get all the events
		data.resetSingle();
		PreparedStatement preStmt=null;
		String stmt = "SELECT * FROM Event";
		//prepares the date given to this method to be used to get the correct events 
		String passedDate = data.getDate();
		SimpleDateFormat displayDate = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat dbDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat theDay = new SimpleDateFormat("dd");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		String formatSDate = null;
		String formatEDate = null;
		String sMonth = null;
		String eMonth = null;
		String sDay = null;
		String eDay = null;
		String curDay = null;
		String curMonth = null;
		int startD = 0;
		int endD = 0;
		int curD = 0;
		int startM = 0;
		int curM = 0;
		int endM = 0;
		
		//This try catch block is used to get the events that are returned based on
		//the dates that the event has
		try{
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			ResultSet rs = preStmt.executeQuery();
			StoreData newDay;
			//loops through all of the events storing the ones that have the matching dates
			while(rs.next()){
				newDay = new StoreData();
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String date = rs.getString("Start_Date");
				String endDate = rs.getString("End_Date");
				//This try catch formats the dates to be used to check to see if they are correct
				try {

					formatSDate = displayDate.format(dbDate.parse(date));
					formatEDate = displayDate.format(dbDate.parse(endDate));
					sDay = theDay.format(dbDate.parse(date));
					eDay = theDay.format(dbDate.parse(endDate));
					curDay = theDay.format(displayDate.parse(passedDate));
					sMonth = month.format(dbDate.parse(date));
					eMonth = month.format(dbDate.parse(endDate));
					curMonth = month.format(displayDate.parse(passedDate));
					startD = Integer.parseInt(sDay);
					endD = Integer.parseInt(eDay);
					curD = Integer.parseInt(curDay);
					startM = Integer.parseInt(sMonth);
					curM = Integer.parseInt(curMonth);
					endM = Integer.parseInt(eMonth);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String sTime = rs.getString("Start_Time");
				String eTime = rs.getString("End_Time");
				newDay.setName(name);
				newDay.setDescription(description);
				newDay.setLocation(location);
				newDay.setDate(date);
				newDay.setEndDate(endDate);
				newDay.setSTime(sTime);
				newDay.setETime(eTime);
				//stores the events that match the date that was given
				if(curD >= startD && curD <= endD && curM == startM){
					data.addDayEvent(newDay);
				}else if(startM < curM && curM == endM && curD <= endD){
					data.addDayEvent(newDay);
				}else if(curD < startD || curD > endD){
					continue;
				}
			}
		} catch (SQLException e) {
			System.out.println("Man you got problems now");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to get events with a similar name
	 * It is seperate from the one above because it gets all of the events with the same name
	 * 
	 * @param connection
	 * @param data
	 */
	public void getNameEvents(Connection connection, StoreData data){
		//This code prepares a statement to be sent to the database
		data.resetSingle();
		PreparedStatement preStmt=null;
		String stmt = "SELECT * FROM Event";
		//used to format the date from the database 
		SimpleDateFormat displayDate = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat dbDate = new SimpleDateFormat("yyyy-MM-dd");
		String formatSDate = null;
		String formatEDate = null;
		String passedName = data.getName();
		//This try catch is used to get the result set from the database which contains all of the events
		//with the same name
		try{
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			ResultSet rs = preStmt.executeQuery();
			StoreData newDay;
			//Loops through the result set to get and store all of the events
			while(rs.next()){
				newDay = new StoreData();
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String date = rs.getString("Start_Date");
				String endDate = rs.getString("End_Date");
				String sTime = rs.getString("Start_Time");
				String eTime = rs.getString("End_Time");
				String id = rs.getString("id");
				//formats the dates properly
				try {

					formatSDate = displayDate.format(dbDate.parse(date));
					formatEDate = displayDate.format(dbDate.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				newDay.setName(name);
				newDay.setDescription(description);
				newDay.setLocation(location);
				newDay.setDate(formatSDate);
				newDay.setEndDate(formatEDate);
				newDay.setSTime(sTime);
				newDay.setETime(eTime);
				newDay.setID(id);
				//stores an event based on name
				if(name.equals(passedName)){
					data.addDayEvent(newDay);
				}
			}
		} catch (SQLException e) {
			System.out.println("Man you got problems now");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to get events by the ID that is given to
	 * them when they are first stored in the database
	 * 
	 * @param connection
	 * @param data
	 */
	public void getByID(Connection connection, StoreData data){
		//prepares a statement to send to the database and prepares date formatting 
		PreparedStatement preStmt=null;
		StoreData theData = data;
		SimpleDateFormat displayDate = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat dbDate = new SimpleDateFormat("yyyy-MM-dd");
		String formatSDate = null;
		String formatEDate = null;
		String stmt = "SELECT * FROM Event WHERE id= ?";
		//This try catch block is used to make the sql call and to format the dates 
		// and analyze the events to store them 
		try {
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			preStmt.setString(1, data.getID());
			ResultSet rs = preStmt.executeQuery();
			//loops through the result set to get the correct events
			while(rs.next()){
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String date = rs.getString("Start_Date");
				String endDate = rs.getString("End_Date");
				//This try catch block formats the date correctly
				try {

					formatSDate = displayDate.format(dbDate.parse(date));
					formatEDate = displayDate.format(dbDate.parse(endDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String sTime = rs.getString("Start_Time");
				String eTime = rs.getString("End_Time");
				data.setDescription(description);
				data.setLocation(location);
				data.setDate(formatSDate);
				data.setEndDate(formatEDate);
				data.setSTime(sTime);
				data.setETime(eTime);
			}
		} catch (SQLException e) {
			System.out.println("Man you got problems now");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to edit an event based on the given name
	 * 
	 * @param connection
	 * @param data
	 */
	public void editEvent(Connection connection, StoreData data){
		//Prepares a statement to edit a full event based on the correct name
		PreparedStatement preStmt=null;
		StoreData theData = data;
		String name = theData.getName();
		String local = theData.getLocation();
		String startDate = theData.getDate();
		String endDate = theData.getEndDate();
		String description = theData.getDescription();
		String theSTime = theData.getSTime();
		String theETime = theData.getETime();
		String id = theData.getID();
		//This try catch block is used to make the sql call and to edit the correct event
		try {
			preStmt = (PreparedStatement) connection.prepareStatement("UPDATE "
					+ "Event SET Name=?, Location=?, Description=?, End_Date=?, Start_Date=?, Start_Time=?, End_Time=? WHERE id=?"); 
			java.util.Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(startDate);
			java.util.Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(endDate);
			java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(date2.getTime());

			preStmt.setString(1,name);
			preStmt.setDate(4,sqlEndDate);
			preStmt.setDate(5, sqlDate);
			preStmt.setString(2,local);
			preStmt.setString(3,description);
			preStmt.setString(6, theSTime);
			preStmt.setString(7, theETime);
			preStmt.setString(8, id);
			preStmt.executeUpdate();
		} catch (SQLException | ParseException e) {
			System.out.println("Nothing was added.");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to add repeat events to the database
	 * 
	 * @param connection
	 * @param data
	 */
	public void addRepeats(Connection connection, StoreData data){
		//This code is used to prepare a statement to be sent to the database
		//getting the rest of the data from the current event
		PreparedStatement preStmt=null;
		StoreData theData = data;
		String name = theData.getName();
		String local = theData.getLocation();
		String startDate = theData.getDate();
		String endDate = theData.getEndDate();
		String description = theData.getDescription();
		String theSTime = theData.getSTime();
		String theETime = theData.getETime();
		String theID = UUID.randomUUID().toString();
		//This loop loops through the days adding the correct event to the correct days
		for(int i = 0; i < data.getSingleDay().size(); i++){
			//This try catch block is used to send a sql statemnet to the database updating the date correctly
			try {
				preStmt = (PreparedStatement) connection.prepareStatement("INSERT INTO "
						+ "Event(Name,Location,Description,End_Date, Start_Date, Start_Time, End_Time, id) VALUES(?,?,?,?,?,?,?,?)"); 
				java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(data.getSingleDay().get(i).getDate());
				java.util.Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(data.getSingleDay().get(i).getEndDate());
				java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				java.sql.Date sqlEndDate = new java.sql.Date(date2.getTime());
				preStmt.setString(8, theID);
				preStmt.setString(1,name);
				preStmt.setDate(4,sqlEndDate);
				preStmt.setDate(5, sqlDate);
				preStmt.setString(2,local);
				preStmt.setString(3,description);
				preStmt.setString(6, theSTime);
				preStmt.setString(7, theETime);
				preStmt.executeUpdate();
			} catch (SQLException | ParseException e) {
				System.out.println("Nothing was added");
				e.printStackTrace();
			}
		}
		
	}
}
