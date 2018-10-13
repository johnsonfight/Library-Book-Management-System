import java.text.SimpleDateFormat;
import java.util.Date;

public class History {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	String user;
	int oper;
	Integer branch;
	String book;
	Date date;

	public History(){
		
	}
	
	public History(String uid, int op, String br, String serialNum, String today){
		user = uid;
		oper = op;
		try{
			branch = Integer.valueOf(br);
		}catch (NumberFormatException nfe){
			branch = null;
		}
		book = serialNum;
//		date = today;
		try{
			date = (Date) sdf.parse(today);			
		}catch (Exception e){
			date = null;
		}
	}
	
	public String toString(){
		return user + "," + oper + "," + branch + "," + book + "," + sdf.format(date);
	}
}
