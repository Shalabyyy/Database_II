package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import exceptions.DBAppException;

public class DBApp {
	protected Vector<Person> person_table;
	protected Vector<BitMap> bit_map_indicies;
	protected final int maximum_page_storage = 10;
	protected final String table_name = "Person";
	protected final File metadata = new File("metadata.csv");
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String FILE_HEADER = "Table Name,Column Name,Column Type,Key,Indexed";

	public DBApp() {

		this.person_table = new Vector<Person>();
		this.bit_map_indicies= new Vector<BitMap>();
		Hashtable<String, Hashtable> meta_hashtable = new Hashtable<String, Hashtable>();

	}

	public void Serialize() {
		@SuppressWarnings("deprecation")
		Date dob = new Date(1998, 10, 23);

		Person shalaby = new Person(1, "Youssef Shalaby", "London", dob, 0, dob);
		Person hoss = new Person(2, "hoss", "Giza", dob, 6, dob);
		Person zamzamy = new Person(3, "zamzamy", "Cairo", dob, 0, dob);
		person_table.addElement(zamzamy);
		person_table.addElement(shalaby);
		person_table.addElement(hoss);

		try {
			FileOutputStream fileOut = new FileOutputStream(
					"D:/My stuff/MET/eval/DBII/person.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(person_table);
			out.close();
			fileOut.close();
			System.out.println("Serialized");
		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public void Deserialize() {
		try {
			FileInputStream fileIn = new FileInputStream(
					"D:/My stuff/MET/eval/DBII/person.ser");
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
		for (int i = 0; i < person_table.size(); i++) {
			System.out.println(person_table.get(i).toString());
		}
	}

	public Hashtable<String, String> createHashTable() {
		Hashtable<String, String> hash = new Hashtable<String, String>();
		hash.put("id", "java.lang.Integer");
		hash.put("full_name", "java.lang.String");
		hash.put("city", "java.lang.String");
		hash.put("date_of_birth", "java.util.Date");
		hash.put("number_of_kids", "java.lang.Integer");
		hash.put("TouchDate", "java.util.Date");
		return hash;

	}
	public void createTable(String strTableName, String strClusteringKeyColumn,
			Hashtable<String, String> htblColNameType) {

		try {
			FileWriter file = new FileWriter(this.metadata);
			file.append(FILE_HEADER.toString());
			file.append(NEW_LINE_SEPARATOR);

			Set<String> keys= htblColNameType.keySet();

			for(String key: keys){
				file.append(table_name);
				file.append(COMMA_DELIMITER);
				file.append(key);
				file.append(COMMA_DELIMITER);
				file.append(htblColNameType.get(key));
				file.append(COMMA_DELIMITER);
				if(key.equals(strClusteringKeyColumn)){
					file.append("True");
				}
				else{
					file.append("False");
				}
				file.append(COMMA_DELIMITER);
				file.append("False");
				file.append(NEW_LINE_SEPARATOR);	
			}






			file.flush();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	@SuppressWarnings("unused")
	public void insertIntoTable(String strTableName,Hashtable<String,Object> htblColNameValue)	throws DBAppException{
		//table name = Person
		if(strTableName.equals("Person")){
			int id = 0,number_of_kids=0;
			String full_name = null,city=null;
			Date date_of_birth = null,TouchDate=null;		
			try{
				id=(int) htblColNameValue.get("id");
			}
			catch(Exception e){
				System.out.println("You have Entered a wrong data type for the id attribiute");
			}

			try{
				full_name=(String) htblColNameValue.get("full_name");
			}
			catch(Exception e){
				System.out.println("You have Entered a wrong data type for the full name attribiute");
			}

			try{
				city=(String) htblColNameValue.get("city");
			}
			catch(Exception e){
				System.out.println("You have Entered a wrong data type for the city attribiute");
			}

			try{
				number_of_kids=(int)htblColNameValue.get("number_of_kids");
			}
			catch(Exception e){
				System.out.println("You have Entered a wrong data type for the number of kids attribiute");	
			}
			try{
				date_of_birth=(Date) htblColNameValue.get("date_of_birth");
			}
			catch(Exception e){
				System.out.println("You have Entered a wrong data type for the date_of_birth attribiute");
			}
			Date Touchdate= new Date();
			Person p= new Person(id, full_name, city, date_of_birth, number_of_kids, Touchdate);
			person_table.addElement(p);

		}
		else{
			System.out.println("This Table was not Created");
		}


	}
	public void deleteFromTable(String strTableName,
			Hashtable<String,Object> htblColNameValue)throws DBAppException{
		if(strTableName.equals("Person")){
			int id;
			try
			{
				id=(int) htblColNameValue.get("id");
				for(int i=0; i<person_table.size();i++)
				{
					if(person_table.get(i).id==id)
					{
						Person p= person_table.get(i);
						person_table.remove(p);
						break;
					}
				}
			}catch(Exception e)
			{
				System.out.println("No such id");
			}

		}
	} 
	public void updateTable(String strTableName,
			String strKey,
			Hashtable<String,Object> htblColNameValue )
					throws DBAppException {


		if(strTableName.equals("Person")){

			String full_name = null,city=null;
			Date date_of_birth = null,TouchDate=null;		
			int idd=(int) htblColNameValue.get("id");


			if(strKey.equals("full_name")){
				try{
					System.out.println("jhsbkshkhskjhsfdkjhsdkjhfdkshfsohfskh");
					System.out.println(htblColNameValue.get("full_name"));
					htblColNameValue.replace("full_name", htblColNameValue.get("full_name"));
					for(int i=0; i < person_table.size(); i++){

						Person tmp = person_table.get(i);
						if(person_table.get(i).id==idd)
						{
							person_table.get(i).full_name=(String) htblColNameValue.get("full_name");
							Date td= new Date();
							person_table.get(i).TouchDate=td;
						}
					}



				}
				catch(Exception e){
					System.out.println("You have Entered a wrong data type for the full name attribiute");
				}}

			if(strKey.equals("city")){
				try{
					city=(String) htblColNameValue.get("city");
					htblColNameValue.replace("city", htblColNameValue.get("city"));

					for(int i=0; i < person_table.size(); i++){

						Person tmp = person_table.get(i);
						if(person_table.get(i).id==idd)
						{
							person_table.get(i).city=(String) htblColNameValue.get("city");
							Date td= new Date();
							person_table.get(i).TouchDate=td;
						}
					}


				}
				catch(Exception e){
					System.out.println("You have Entered a wrong data type for the city attribiute");
				}}
			if(strKey.equals("number_of_kids")){
				try{

					htblColNameValue.replace("number_of_kids", htblColNameValue.get("number_of_kids"));

					for(int i=0; i < person_table.size(); i++){

						Person tmp = person_table.get(i);
						if(person_table.get(i).id==idd)
						{
							person_table.get(i).number_of_kids=(int) htblColNameValue.get("number_of_kids");
							Date td= new Date();
							person_table.get(i).TouchDate=td;
						}
					}
				}
				catch(Exception e){
					System.out.println("You have Entered a wrong data type for the number of kids attribiute");	
				}}
			if(strKey.equals("date_of_birth")){
				try{
					date_of_birth=(Date) htblColNameValue.get("date_of_birth");
					htblColNameValue.replace("date_of_birth", htblColNameValue.get("date_of_birth"));

					for(int i=0; i < person_table.size(); i++){

						Person tmp = person_table.get(i);
						if(person_table.get(i).id==idd)
						{
							person_table.get(i).date_of_birth=(Date) htblColNameValue.get("date_of_birth");
							Date td= new Date();
							person_table.get(i).TouchDate=td;
						}
					}

				}
				catch(Exception e){
					System.out.println("You have Entered a wrong data type for the date_of_birth attribiute");
				}}
			//Date Touchdate= new Date();
			//htblColNameValue.replace("Touchdate", htblColNameValue.get("Touchdate"));


		}
		else{
			System.out.println("This Table was not Created");
		}

	}
	public void createBitMapIndex(){
		//get columns names from metadata file, pretty staright forward
		String csvFile = "metadata.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<String> bitbit=new ArrayList<String>(); 
		ArrayList<BitMap> bitmap= new ArrayList<BitMap>();
		try {

			br = new BufferedReader(new FileReader(csvFile));
			boolean skip=false;
			while ((line = br.readLine()) != null) {
				// use comma as separator
				if(skip){
					String[] person = line.split(cvsSplitBy);
					System.out.println("Columns Names:["+person[1]+"]");
					bitbit.add(person[1]);
				}
				skip=true;
			}
			int ref= bitbit.size()-1;
			while(ref==-1){
				BitMap to_add= new BitMap(bitbit.get(ref),null,"");
				bitmap.add(to_add);
				ref--;
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		//Columns fetched from meta data,now to get another
		//get unique results
		//columns are not used later on but it's encouraged to be used

		//create container for each attribute
		ArrayList<Integer> ids= new ArrayList<Integer>();
		ArrayList<String> names= new ArrayList<String>();
		ArrayList<Integer> kids= new ArrayList<Integer>();
		ArrayList<String> cities= new ArrayList<String>();
		ArrayList<Date> dobs = new ArrayList<Date>();
		ArrayList<Date> tds= new ArrayList<Date>();

		for(int i=0;i<person_table.size();i++){
			//string should be of length i in the future(bit map index)
			//fetching current attribute
			int id= person_table.get(i).id;
			String full_name=person_table.get(i).full_name;
			int number_of_kids=person_table.get(i).number_of_kids;
			String city=person_table.get(i).city;
			Date date_of_birth=person_table.get(i).date_of_birth;
			Date TouchDate=person_table.get(i).TouchDate;
			
			//if its a unique value then add, else skip
			//TODO read column names from meta data
			// el mogarad container ba check feeh msh aktar
			if(!ids.contains(id)){
				ids.add(id);
				BitMap b= new BitMap("id",id,"");
				bit_map_indicies.add(b);
				
			}
			if(!names.contains(full_name)){
				names.add(full_name);
				BitMap b= new BitMap("full_name",full_name,"");
				bit_map_indicies.add(b);
			}
			if(!kids.contains(number_of_kids)){
				kids.add(number_of_kids);
				BitMap b= new BitMap("number_of_kids",number_of_kids,"");
				bit_map_indicies.add(b);
			}
			if(!cities.contains(city)){
				cities.add(city);
				BitMap b= new BitMap("city",city,"");
				bit_map_indicies.add(b);
			}
			if(!dobs.contains(date_of_birth)){
				dobs.add(date_of_birth);
				BitMap b= new BitMap("date_of_birth",date_of_birth,"");
				bit_map_indicies.add(b);
			}
			if(!tds.contains(TouchDate)){
				tds.add(TouchDate);
				BitMap b= new BitMap("TouchDate",TouchDate,"");
				bit_map_indicies.add(b);
			}
		}
		//e7na hena m3ana arrays feeha makan lekol bitmap index han3melo, bas lesa ma3mlnash generate
		//Generate bitmap index string here through ANOTHER LOOP

		//set bit map index

	}
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws DBAppException {

		DBApp table = new DBApp();
		//table.Serialize();
		//table.Deserialize();
		table.createBitMapIndex();
		Hashtable<String, String> col = table.createHashTable(); //coloumns
		table.createTable(table.table_name, "id", col); //create metadata...
		Hashtable htblColNameValue = new Hashtable();
		System.out.println("****************************************************************************");
		System.out.println();
		htblColNameValue.put("id", new Integer(66));
		htblColNameValue.put("full_name", new String("Batman dodo"));
		htblColNameValue.put("city", new String("Cairo"));
		htblColNameValue.put("date_of_birth", new Date(1992,7,9));
		htblColNameValue.put("number_of_kids",new Integer(4));
		table.insertIntoTable(table.table_name, htblColNameValue);
		htblColNameValue.clear();
		htblColNameValue.put("id", new Integer(11));
		htblColNameValue.put("full_name", new String("Mesut Ozil"));
		htblColNameValue.put("city", new String("London"));
		htblColNameValue.put("date_of_birth", new Date(1987,11,11));
		htblColNameValue.put("number_of_kids",new Integer(99999));
		table.insertIntoTable(table.table_name, htblColNameValue);


		htblColNameValue.put("full_name", new String("James Critchlow"));
		table.updateTable(table.table_name,"full_name",htblColNameValue );
		//htblColNameValue.put("TouchDate", new Date(1992,7,9));
		for (int i = 0; i < table.person_table.size(); i++) {
			System.out.println(table.person_table.get(i).toString());
		}


	}

}
