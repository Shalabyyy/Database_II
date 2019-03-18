package database;

public class BitMap {
	protected String colName;
	protected String bits;
	protected Object value;
	public BitMap(String colName,Object value, String bits){
		this.colName=colName;
		this.value=value;
		this.bits=bits;
		
	}
}
