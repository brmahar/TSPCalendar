import java.util.ArrayList;


public class StoreData {

	private String name = null;
	private String location = null;
	private String description = null;
	private String date = null;
	private String endDate = null;
	private String sTime = null;
	private String eTime = null;
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<String> sDates = new ArrayList<String>();
	



	public void setName(String theName){
		name = theName;
	}
	public void setLocation(String theLocal){
		location = theLocal;
	}
	public void setDescription(String theDescription){
		description= theDescription;
	}
	public void setDate(String theDate){
		date = theDate;
	}
	public void setEndDate(String theDate){
		endDate = theDate;
	}
	public void setSTime(String theTime){
		sTime = theTime;
	}
	public void setETime(String theTime){
		eTime = theTime;
	}
	public void addDates(String newDate){
		sDates.add(newDate);
	}
	public void addName(String newName){
		names.add(newName);
	}
	public void resetNames(){
		names.clear();
	}
	public String getName(){
		return name;
	}
	public String getLocation(){
		return location;
	}
	public String getDescription(){
		return description;
	}
	public String getDate(){
		return date;
	}
	public String getEndDate(){
		return endDate;
	}
	public String getSTime(){
		return sTime;
	}
	public String getETime(){
		return eTime;
	}
	
	public ArrayList<String> getNames(){
		return names;
	}
	public ArrayList<String> getDates(){
		return sDates;
	}
}
