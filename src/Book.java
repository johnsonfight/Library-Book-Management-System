import java.text.SimpleDateFormat;
import java.util.*;

public class Book {
	
	private static int bookBorrowed = 0;	// ��e�`�ɥX�Ѽ�
	
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

	int originalBranch;// ���]�W:1~5
	String bookTitle;	// �ѦW
	String serialNum;	// �Ѹ�
	int bTime;			// �Q�ɦ��� >= 0
	int status;		// ���A:0~5	0:�ɥX	1~5:�Ҧb���]
	String bBy;			// �ɮѤHuid	
	Date bDue;			// �ٮѴ���
	int rTime;			// ��ɦ���:0~2
	int rUsers;		// �w���H��:0~3
	String r1;			// �w���H1uid
	String r2;			// �w���H2uid
	String r3;			// �w���H3uid
	Date rDue;			// �w���H1���ɮѴ���
	int transTo1;		// �w���H1���վ\�]
	int transTo2;		// �w���H2���վ\�]
	int transTo3;		// �w���H3���վ\�]
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	public String toString(){
		return "���]�G" + originalBranch + " �ѦW�G" + bookTitle 
				+ "\r\n�Ѹ��G" + serialNum + " ���ơG" + bTime 
				+ "\r\n���A�G" + status + " �ɪ̡G" + bBy 
				+ "\r\n����G" + sdf.format(bDue) + " �򦸡G" + rTime
				+ "\r\n���̡G" + rUsers + " ��1�G" + r1
				+ "\r\n��2�G" + r2 + " ��3�G" + r3
				+ "\r\n�����G" + sdf.format(rDue) + " ��1�G" + transTo1
				+ "\r\n��2�G" + transTo2 + " ��3�G" + transTo3;
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

