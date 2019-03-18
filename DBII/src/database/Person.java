package database;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;


public class Person implements Serializable{

	protected int id;
	protected String full_name;
	protected String city;
	protected Date date_of_birth;
	protected int number_of_kids;
	protected Date TouchDate;
	
	protected final String table_name="Person";
	protected final int maximum_page_storage=10;
	

	public Person(int id,String full_name,String city,Date date_of_birth,int number_of_kids, Date TouchDate){
		this.id=id;
		this.full_name=full_name;
		this.city=city;
		this.date_of_birth=date_of_birth;
		this.number_of_kids=number_of_kids;
		this.TouchDate=TouchDate;


	}
	public String toString(){
		return id+","+full_name+","+date_of_birth.toString()+","+number_of_kids+","+TouchDate.toString();
	}
	public void init(){} //requested method
	
	public void Serilaize(){
		
		
		
	}
	public Hashtable<String,String> createHashTable(){
		Hashtable<String, String> hash = new Hashtable<String, String>();
		hash.put("id","java.lang.Integer");
		hash.put("full_name","java.lang.String");
		hash.put("city", "java.lang.String");
		hash.put("date_of_birth", "java.util.Date");
		hash.put("number_of_kids","java.lang.Integer");
		hash.put("TouchDate", "java.util.Date");
		return hash;
		
		
	}
	
	public static void main (String args[]){
			
	}




}
