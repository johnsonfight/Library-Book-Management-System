import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Data {
	
	static Scanner books = null;
	static Scanner uids = null;
	static Scanner log = null;
	static Scanner deptNum = null;
	private static Map<String,Book>book = new HashMap<>();
	private static Map<String,Uid>uid = new HashMap<>();
	private static Map<String,String>dept = new HashMap<>();
	private static Date current = new Date(1900/1/1);

	private static ArrayList<Rank> uBingRank = new ArrayList<Rank>();
	private static ArrayList<Rank> uBedRank = new ArrayList<Rank>();
	private static ArrayList<Rank> bBedRank = new ArrayList<Rank>();
	private static ArrayList<Uid> banBList = new ArrayList<Uid>();
	private static ArrayList<Uid> banRList = new ArrayList<Uid>();
	private static ArrayList<History> historyLogB = new ArrayList<History>();
	private static ArrayList<History> historyLogR = new ArrayList<History>();
	
	
	private static int[] branchBedBook = new int[] {0,0,0,0,0};


	// getters
	public static int getBranchBedBook(int branch) {
		return branchBedBook[branch-1];
	}

	public static Map<String, Book> getBook() {
		return book;
	}

	public static Map<String, Uid> getUid() {
		return uid;
	}
	
	public static Date getCurrent() {
		return current;
	}

	public static ArrayList<Uid> getBanRList() {
		return banRList;
	}

	public static ArrayList<Uid> getBanBList() {
		return banBList;
	}

	public static ArrayList<Rank> getbBedRank() {
		return bBedRank;
	}

	public static ArrayList<Rank> getuBingRank() {
		return uBingRank;
	}

	public static ArrayList<Rank> getuBedRank() {
		return uBedRank;
	}
	
	public static ArrayList<History> getHistoryLogB() {
		return historyLogB;
	}
	
	public static ArrayList<History> getHistoryLogR() {
		return historyLogR;
	}

	public static Map<String, String> getDept() {
		return dept;
	}
	
	// setters
	public static void setBranchBedBook(int branch) {
		branchBedBook[branch-1] ++;
	}

	public static void setbanBList() {
		banBList.clear();
		// 逾期還書
		for (Map.Entry<String,Uid> entry : uid.entrySet()){
			Uid now = entry.getValue();
			if (now.bRight == 0 && now.bRightFrom.after(current)){
				banBList.add(now);
			}else if (now.bRight == -1){
				banBList.add(now);
			}
		}
	}
	
	public static void setbanRList(Map<String,Uid> uid) {
		banRList.clear();
		for (Map.Entry<String,Uid> entry : uid.entrySet()){
			Uid now = entry.getValue();
			if (now.rRightFrom.after(current)){
				banRList.add(now);
			}
		}
	}

	public static void setuBingRank(Map<String,Uid> uid) {
		uBingRank.clear();
		for (Map.Entry<String,Uid> entry : uid.entrySet()){
			Uid now = entry.getValue();
			Rank temp = new Rank(now.uid, now.bQty);
			uBingRank.add(temp);
		}
		Collections.sort(uBingRank);
	}

	public static void setuBedRank(Map<String,Uid> uid) {
		uBedRank.clear();
		for (Map.Entry<String,Uid> entry : uid.entrySet()){
			Uid now = entry.getValue();
			Rank temp = new Rank(now.uid, now.bTimes);
			uBedRank.add(temp);
		}
		Collections.sort(uBedRank);
	}

	public static void setbBedRank(Map<String,Book> book) {
		bBedRank.clear();
		for (Map.Entry<String,Book> entry : book.entrySet()){
			Book now = entry.getValue();
			Rank temp = new Rank(now.serialNum, now.bTime, now.originalBranch);
			bBedRank.add(temp);
		}
		Collections.sort(bBedRank);
	}
	
	// update local data
	public static void setBook(Book book) {
		Data.book.put(book.serialNum, book);
	}

	public static void setUid(Uid uid) {
		Data.uid.put(uid.uid, uid);
	}
	
	public static void setCurrent(Date current) {
		Data.current = current;
	}
	
	// read from the file
	public static void initBook(){
		try {
			books = new Scanner(new FileInputStream("data/booksAndBranches.csv"), "Unicode");
			// System.out.println("open!!");
		}catch(FileNotFoundException e) {
			System.out.println("file not found");
		}
		
		books.nextLine(); // avoid to read the first line
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		while (books.hasNextLine()){
			String line = books.nextLine();
			StringTokenizer st = new StringTokenizer(line, ",", false);

			Book temp = new Book();
			temp.originalBranch = Integer.valueOf(st.nextToken());
			temp.bookTitle = st.nextToken();
			temp.serialNum = st.nextToken();
			temp.bTime = Integer.valueOf(st.nextToken());
			temp.status = Integer.valueOf(st.nextToken());
			temp.bBy = st.nextToken();
			String bDue = st.nextToken();
			temp.rTime = Integer.valueOf(st.nextToken());
			temp.rUsers = Integer.valueOf(st.nextToken());
			temp.r1 = st.nextToken();
			temp.r2 = st.nextToken();
			temp.r3 = st.nextToken();
			String rDue = st.nextToken();
			temp.transTo1 = Integer.valueOf(st.nextToken());
			temp.transTo2 = Integer.valueOf(st.nextToken());
			temp.transTo3 = Integer.valueOf(st.nextToken());

			try {  
                temp.bDue = (Date) sdf.parse(bDue); 
            } catch (java.text.ParseException e) {  
                e.printStackTrace();  
            }
			
			try {  
                temp.rDue = (Date) sdf.parse(rDue);   
            } catch (java.text.ParseException e) {  
                e.printStackTrace();  
            }
			// temp.print();
			book.put(temp.serialNum, temp);
		}
		books.close();
	}
	
	public static void initUid(){
		try {
			uids = new Scanner(new FileInputStream("data/UID.csv"), "Unicode");
			// System.out.println("open!!");
		}catch(FileNotFoundException e) {
			System.out.println("file not found");
		}
		
		uids.nextLine();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		while(uids.hasNextLine()){
			String line = uids.nextLine();
			StringTokenizer st = new StringTokenizer(line, ",", false);
			
			Uid temp = new Uid();
			temp.uid = st.nextToken();
			temp.bRight = Integer.valueOf(st.nextToken());
			String bRightFrom = st.nextToken();
			temp.bQty = Integer.valueOf(st.nextToken());
			temp.bTimes = Integer.valueOf(st.nextToken());
			String rRightFrom = st.nextToken();
			temp.rQty = Integer.valueOf(st.nextToken());
			
			try {  
	            temp.bRightFrom = (Date) sdf.parse(bRightFrom);   
	        } catch (java.text.ParseException e) {  
	            e.printStackTrace();  
	        }
			try {  
	            temp.rRightFrom = (Date) sdf.parse(rRightFrom);   
	        } catch (java.text.ParseException e) {  
	            e.printStackTrace();  
	        }
			uid.put(temp.uid, temp);
			// temp.print();
		}
		uids.close();
	}
	
	public static void initDept(){
		try {
			deptNum = new Scanner(new FileInputStream("data/deptNum.csv"), "Unicode");
			// System.out.println("open!!");
		}catch(FileNotFoundException e) {
			System.out.println("file not found");
		}
		
		deptNum.nextLine();
		
		while(deptNum.hasNextLine()){
			String line = deptNum.nextLine();
			StringTokenizer st = new StringTokenizer(line, ",", false);
			String num = st.nextToken();
			String name = st.nextToken();
			dept.put(num, name);
			// temp.print();
		}
		uids.close();
	}
	

	// 每次有日期的指令時做一次check	book: bDue, RDue
	// 								uid : bRightFrom, rRightFrom --> 借書 或 預約 時 檢查 
	public static void check(){
		for (Map.Entry<String,Book> entry : book.entrySet()){
			Book theBook = entry.getValue();
			if (theBook.status == 0 && theBook.bDue.before(current)){
				// 逾期未還
				Uid user = uid.get(theBook.bBy);
				user.bRight = -1;
				setUid(user);
			}
			if (theBook.status != 0 && theBook.rUsers != 0 && theBook.rDue.before(current)){
				// 預約逾期	book : rUsers--, r1 = r2, r2 = r3, r3 = 預設, rDue +=5
				// 			uid  : rRightFrom = current + 90, rQty--
				Calendar c = Calendar.getInstance();
				c.setTime(theBook.rDue);
				Uid user = uid.get(theBook.r1);
				c.add(Calendar.DATE, 90);
				user.rRightFrom = c.getTime();
				user.rQty--;
				setUid(user);
				
				theBook.r1 = theBook.r2;
				theBook.r2 = theBook.r3;
				theBook.r3 = "B00000000";
				theBook.rUsers --;
				c.setTime(theBook.rDue);
				c.add(Calendar.DATE, 5);
				theBook.rDue = c.getTime();
				theBook.transTo1 = theBook.transTo2;
				theBook.transTo2 = theBook.transTo3;
				theBook.transTo3 = theBook.originalBranch;
				setBook(theBook);
			}
			// bDue.before(current) -> 是否有預約人 -> 是否調閱 -> 是否在原館
			if (theBook.status != 0 && theBook.bDue.before(current)){
				if (theBook.rUsers != 0 && theBook.transTo1 != theBook.originalBranch){
					theBook.status = theBook.transTo1;
				}else if (theBook.status != theBook.originalBranch){
					theBook.status = theBook.originalBranch;
				}
				setBook(theBook);
			}
		}
	}
	public static void readLog(String user){ 									// 建立userLog
		historyLogB.clear();
		historyLogR.clear();
		try {
			log = new Scanner(new FileInputStream("data/HistoryLog.csv"));
		}catch(FileNotFoundException e) {
			System.out.println("file not found");
		}
		log.nextLine();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		while(log.hasNextLine()){
			String line = log.nextLine();
			StringTokenizer st = new StringTokenizer(line, ",", false);
			
			History temp = new History();
			temp.user = st.nextToken();
			temp.oper = Integer.valueOf(st.nextToken());
			if (temp.user.equals(user) && temp.oper == 1){
				try {
					temp.branch = Integer.valueOf(st.nextToken());
				}catch (NumberFormatException nfe){
					temp.branch = null;
				}
				temp.book = st.nextToken();
				String date = st.nextToken();
				try {  
		            temp.date = (Date) sdf.parse(date);   
		        } catch (java.text.ParseException e) {  
		            e.printStackTrace();  
		        }
				historyLogB.add(temp);
			}else if (temp.user.equals(user) && temp.oper == 2){
				try {
					temp.branch = Integer.valueOf(st.nextToken());
				}catch (NumberFormatException nfe){
					temp.branch = null;
				}
				temp.book = st.nextToken();
				String date = st.nextToken();
				try {  
		            temp.date = (Date) sdf.parse(date);   
		        } catch (java.text.ParseException e) {  
		            e.printStackTrace();  
		        }
				historyLogR.add(temp);
			}
		}
		log.close();
	}
	
	public static void record(History h){								// 每次指令完 紀錄至HistoryLog
		PrintWriter outputStream = null;
		try {			
			outputStream = new PrintWriter(new FileOutputStream("data/historyLog.csv",true));
			outputStream.println(h.toString());
		}catch (Exception e){
			System.out.println("can't do anything");
		}finally {
			outputStream.close();
		}	
	}
}
