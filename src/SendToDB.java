import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.UUID;

import javax.swing.*;

import com.mysql.jdbc.PreparedStatement;


public class SendToDB {


	public void runStore(StoreData data, int bool){


		System.out.println("------------ MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered");
		Connection connection = null;

		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://orion.csl.mtu.edu/ajbrowne","ajbrowne", "ajZ4VikY/tnI.");

		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			((Throwable) e).printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("Now Connected, so please stay and look around!");
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
			}else{
			
				send(connection, data);
			}


		} else {
			System.out.println("Failed to make a connection!");
		}
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("The connection was not closed.....Run away now!!!!");
			e.printStackTrace();
		}
	}

	public void send(Connection connection, StoreData data){
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
			System.out.println("Nothing was added lawllawllawl");
			e.printStackTrace();
		}

	}

	public void getData(Connection connection, StoreData data){
		PreparedStatement preStmt=null;
		StoreData theData = data;
		SimpleDateFormat displayDate = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat dbDate = new SimpleDateFormat("yyyy-MM-dd");
		String formatSDate = null;
		String formatEDate = null;
		String stmt = "SELECT * FROM Event WHERE Name= ?";
		try {
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			preStmt.setString(1, data.getName());
			ResultSet rs = preStmt.executeQuery();
			while(rs.next()){
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String date = rs.getString("Start_Date");
				String endDate = rs.getString("End_Date");
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

	public void deleteEvent(Connection connection, StoreData data){
		PreparedStatement preStmt=null;
		String stmt = "DELETE FROM Event WHERE Name=?";
		try{
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			preStmt.setString(1, data.getName());
			preStmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("Man you got problems now");
			e.printStackTrace();
		}
	}

	public void getSpecificData(Connection connection, StoreData data, int bool){
		PreparedStatement preStmt=null;
		String stmt = "SELECT * FROM Event WHERE Start_Date = (?)";
		String formatSDate = null;
		String formatEDate = null;
		StoreData extra = new StoreData();
		try{
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			SimpleDateFormat displayDate = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat dbDate = new SimpleDateFormat("yyyy-MM-dd");
			try {

				formatSDate = dbDate.format(displayDate.parse(data.getDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			preStmt.setString(1, formatSDate);
			ResultSet rs = preStmt.executeQuery();
			for(int i = 0; i < data.getMultiDay().size();i++){
				data.addName(data.getMultiDay().get(i).getName());
			}
			while(rs.next()){
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String name = rs.getString("Name");
				String endDate = rs.getString("End_Date");
				String sTime = rs.getString("Start_Time");
				String eTime = rs.getString("End_Time");

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
				if(!data.getDate().equals(data.getEndDate())){
					data.addEvent(extra);
				}

			}
		}catch(SQLException e){
			System.out.println("Man you got problems now");
			e.printStackTrace();
		}

	}

	public void getDateEvents(Connection connection, StoreData data){
		data.resetSingle();
		PreparedStatement preStmt=null;
		String stmt = "SELECT * FROM Event";
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

		try{
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			ResultSet rs = preStmt.executeQuery();
			StoreData newDay;
			while(rs.next()){
				newDay = new StoreData();
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String date = rs.getString("Start_Date");
				String endDate = rs.getString("End_Date");
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
	
	public void getNameEvents(Connection connection, StoreData data){
		data.resetSingle();
		PreparedStatement preStmt=null;
		String stmt = "SELECT * FROM Event";
		SimpleDateFormat displayDate = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat dbDate = new SimpleDateFormat("yyyy-MM-dd");
		String formatSDate = null;
		String formatEDate = null;
		String passedName = data.getName();
		
		try{
			preStmt = (PreparedStatement) connection.prepareStatement(stmt);
			ResultSet rs = preStmt.executeQuery();
			StoreData newDay;
			while(rs.next()){
				newDay = new StoreData();
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				String location = rs.getString("Location");
				String date = rs.getString("Start_Date");
				String endDate = rs.getString("End_Date");
				String sTime = rs.getString("Start_Time");
				String eTime = rs.getString("End_Time");
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
				if(name.equals(passedName)){
					data.addDayEvent(newDay);
				}
			}
		} catch (SQLException e) {
			System.out.println("Man you got problems now");
			e.printStackTrace();
		}
	}
}
