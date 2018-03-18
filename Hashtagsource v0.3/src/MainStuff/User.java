package MainStuff;

import java.util.ArrayList;

public class User {

	String name;
	String gender;
	int alter;
	ArrayList<String> vorlieben;
	ArrayList<String> nachichten = new ArrayList<String>();
	
	public User (String name){
		this.name = name;
	}
	
	//Setter
	public void SetName(String name) {
		this.name = name;
	}
	
	public void SetGender(String gender) {
		this.gender = gender;
	}
	
	public void SetAlter(int alter){
		this.alter = alter;
	}
	
	public void AddVorliebe(String vorliebe){
		vorlieben.add("" + vorliebe);
	}
	
	public void AddNachicht(String nachicht){
		nachichten.add(nachicht);
	}
	
	public void SetNachichten(ArrayList<String> msgs){
		nachichten = msgs;
	}
	
	//Getter
	public String getName() {
		return this.name;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public int getAlter(){
		return this.alter;
	}
	
	public ArrayList<String> getVorlieben() {
		return this.vorlieben;
	}
	
	public ArrayList<String> getNachichten(){
		return this.nachichten;
	}
	
	public String getFirstMsg(){
		return nachichten.get(0);
	}
}
