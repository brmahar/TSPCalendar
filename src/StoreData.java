import java.util.ArrayList;

/**
 * This class is used as a data object to store specific data about the event
 * 
 * @author Brady Mahar
 *
 */
public class StoreData {
	
	//These are the variables used to store the correct data
	private String name = null;
	private String location = null;
	private String description = null;
	private String date = null;
	private String endDate = null;
	private String sTime = null;
	private String eTime = null;
	private String id = null;
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<String> sDates = new ArrayList<String>();
	private ArrayList<StoreData> multiDay = new ArrayList<StoreData>();
	private ArrayList<StoreData> singleDay = new ArrayList<StoreData>();
	

	//This is the setter for the ID
	public void setID(String theID){
		id = theID;
	}
	//This is the setter for the name
	public void setName(String theName){
		name = theName;
	}
	//This is the setter for the Location
	public void setLocation(String theLocal){
		location = theLocal;
	}
	//This is the setter for the description
	public void setDescription(String theDescription){
		description= theDescription;
	}
	//This is the setter for the start date
	public void setDate(String theDate){
		date = theDate;
	}
	//This is the setter for the end date
	public void setEndDate(String theDate){
		endDate = theDate;
	}
	//This is the setter for the start time
	public void setSTime(String theTime){
		sTime = theTime;
	}
	//This is the setter for the end time
	public void setETime(String theTime){
		eTime = theTime;
	}
	//This is the setter for the array list of dates for repeat events 
	public void addDates(String newDate){
		sDates.add(newDate);
	}
	//This is the setter for the array list of names for multiple names
	public void addName(String newName){
		names.add(newName);
	}
	//This is the setter for the array list to store multi day events
	public void addEvent(StoreData newEvent){
		multiDay.add(newEvent);
	}
	//This is the setter for the array list for single day events
	public void addDayEvent(StoreData newEvent){
		singleDay.add(newEvent);
	}
	//Resets the names array list
	public void resetNames(){
		names.clear();
	}
	//This is the getter for the name
	public String getName(){
		return name;
	}
	//This is the getter for the location
	public String getLocation(){
		return location;
	}
	//This is the getter for the description
	public String getDescription(){
		return description;
	}
	//This is the getter for the start date
	public String getDate(){
		return date;
	}
	//This is the getter for the end date
	public String getEndDate(){
		return endDate;
	}
	//This is the getter for the start time
	public String getSTime(){
		return sTime;
	}
	//This is the getter for the end time
	public String getETime(){
		return eTime;
	}
	//This is the getter for the names array list
	public ArrayList<String> getNames(){
		return names;
	}
	//This is the getter for the dates array list
	public ArrayList<String> getDates(){
		return sDates;
	}
	//This is the getter for the multi day array list
	public ArrayList<StoreData>getMultiDay(){
		return multiDay;
	}
	//removes data from the multi day array list
	public void removeData(StoreData remove){
		multiDay.remove(remove);
	}
	//This is the getter for the single day 
	public ArrayList<StoreData>getSingleDay(){
		return singleDay;
	}
	//Removes single day events from the arraylist
	public void removeDayEvent(StoreData remove){
		singleDay.remove(remove);
	}
	//resets the single day array list
	public void resetSingle(){
		singleDay.clear();
	}
	//This is the getter for the ID
	public String getID(){
		return id;
	}
}
