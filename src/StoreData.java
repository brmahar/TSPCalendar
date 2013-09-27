
public class StoreData {

	private String name = null;
	private String location = null;
	private String description = null;
	private String date = null;
	private String sTime = null;
	private String eTime = null;
	



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
	public void setSTime(String theTime){
		sTime = theTime;
	}
	public void setETime(String theTime){
		eTime = theTime;
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
	public String getSTime(){
		return sTime;
	}
	public String getETime(){
		return eTime;
	}
}
