package database;
import java.io.*;
import java.util.Vector;
public class DeserializeDemo {
	public static void main(String args[]){
		Vector<Person> person_table =null;
		 try {
	         FileInputStream fileIn = new FileInputStream("D:/My stuff/MET/eval/DBII/person.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         person_table = (Vector<Person>) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch (IOException i) {
	         i.printStackTrace();
	         return;
	      } catch (ClassNotFoundException c) {
	         System.out.println("Person class not found");
	         c.printStackTrace();
	         return;
	      }
		 for(int i=0; i<person_table.size();i++){
			 System.out.println(person_table.get(i).toString());
		 }

	}
}
