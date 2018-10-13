import java.text.SimpleDateFormat;
import java.util.*;


public class Uid{

	
	String uid;			// 學號
	int bRight;			// 權限:0:可借 1:逾期已還,bRightFrom後可借 2:逾期未還不可借
	Date bRightFrom;	// 逾期還書可借日期
	int bQty;			// 目前借書數:0~10(未還)
	int bTimes;			// 總借書數:>=0(已還)
	Date rRightFrom;	// 可預約日期 預設為1900/1/1
	int rQty;			// 預約書數:0~5
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	private static int peopleBorrowing = 0; // 目前總借書人數
	
	public static int getPeopleBorrowing() {
		return peopleBorrowing;
	}

	public static void setPeopleBorrowing(int n){
		peopleBorrowing += n;
	}
	public static void countPeopleBorrowing(Map<String,Uid> uid) {
		peopleBorrowing = 0;
		for (Map.Entry<String,Uid> entry : uid.entrySet()){
			Uid temp = entry.getValue();
			if (temp.bQty != 0){
				peopleBorrowing ++;
			}
		}
	}
	public Uid(){
		
	}
	public Uid(String user){
		uid = user;
		bRight = 1;
		bRightFrom = new Date(0);
		bQty = 0;
		bTimes = 0;
		rRightFrom = new Date(0);
		rQty = 0;
	}

	
	public String toString(){
		return "學號：" + uid + " 權限：" + bRight
				+ "\r\n借期：" + sdf.format(bRightFrom) + " 數量：" + bQty
				+ "\r\n次數：" + bTimes + " 約期：" + sdf.format(rRightFrom)
				+ "\r\n約數：" + rQty;
	}

}
