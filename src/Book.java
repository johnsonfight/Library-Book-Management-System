import java.text.SimpleDateFormat;
import java.util.*;

public class Book {
	
	private static int bookBorrowed = 0;	// 當前總借出書數
	
	public static int getBookBorrowed() {
		return bookBorrowed;
	}

	public static void setBookBorrowed(int n) {
		bookBorrowed += n;
	}
	
	public static void countBookBorrowed(Map<String,Book> book) {
		bookBorrowed = 0;
		for (Map.Entry<String,Book> entry : book.entrySet()){
			Book temp = entry.getValue();
			if (temp.status == 0){
				bookBorrowed ++;
			}
		}
	}

	int originalBranch;// 原館名:1~5
	String bookTitle;	// 書名
	String serialNum;	// 書號
	int bTime;			// 被借次數 >= 0
	int status;		// 狀態:0~5	0:借出	1~5:所在分館
	String bBy;			// 借書人uid	
	Date bDue;			// 還書期限
	int rTime;			// 續借次數:0~2
	int rUsers;		// 預約人數:0~3
	String r1;			// 預約人1uid
	String r2;			// 預約人2uid
	String r3;			// 預約人3uid
	Date rDue;			// 預約人1的借書期限
	int transTo1;		// 預約人1的調閱館
	int transTo2;		// 預約人2的調閱館
	int transTo3;		// 預約人3的調閱館
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	public String toString(){
		return "原館：" + originalBranch + " 書名：" + bookTitle 
				+ "\r\n書號：" + serialNum + " 次數：" + bTime 
				+ "\r\n狀態：" + status + " 借者：" + bBy 
				+ "\r\n到期：" + sdf.format(bDue) + " 續次：" + rTime
				+ "\r\n約者：" + rUsers + " 約1：" + r1
				+ "\r\n約2：" + r2 + " 約3：" + r3
				+ "\r\n約期：" + sdf.format(rDue) + " 調1：" + transTo1
				+ "\r\n調2：" + transTo2 + " 調3：" + transTo3;
	}
	
	public Book(Book a){
		this.originalBranch = a.originalBranch; 
		this.bookTitle = a.bookTitle;
		this.serialNum = a.serialNum;
		this.bTime = a.bTime;
		this.status = a.status;
		this.bBy = a.bBy;
		this.bDue = a.bDue;
		this.rTime = a.rTime;
		this.rUsers = a.rUsers;
		this.r1 = a.r1;
		this.r2 = a.r2;
		this.r3 = a.r3;
		this.rDue = a.rDue;
		this.transTo1 = a.transTo1;
		this.transTo2 = a.transTo2;
		this.transTo3 = a.transTo3;
	}
	
	public Book(){
		this.originalBranch = 0; 
		this.bookTitle = "";
		this.serialNum = "";
		this.bTime = 0;
		this.status = 0;
		this.bBy = "";
		this.bDue = null;
		this.rTime = 0;
		this.rUsers = 0;
		this.r1 = "";
		this.r2 = "";
		this.r3 = "";
		this.rDue = null;
		this.transTo1 = 0;
		this.transTo2 = 0;
		this.transTo3 = 0;
	}
}

