package database;
import java.io.*;
import java.util.Date;
import java.util.Vector;
public class SerializeDemo {

	public static void main(String args[]){
		@SuppressWarnings("deprecation")
		Date dob= new Date(1998,10,23);
		
		Person shalaby= new Person(1,"Youssef Shalaby","London",dob,0,dob);
		Person hoss= new Person(2,"hoss","Giza",dob,6,dob);
		Person zamzamy= new Person(3,"zamzamy","Cairo",dob,0,dob);
		DBApp table= new DBApp();
		table.person_table.addElement(zamzamy);
		table.person_table.addElement(shalaby);
		table.person_table.addElement(hoss);
		//should handle page max size
		

		
		try{
			FileOutputStream fileOut = new FileOutputStream("D:/My stuff/MET/eval/DBII/person.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(table.person_table);
			out.close();
			fileOut.close();
			System.out.println("Serialized");
		}
		catch(IOException i){i.printStackTrace();}

	}

}
