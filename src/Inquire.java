import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Inquire {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	public String search(int branch, String serialNum, Map<String,Book> book){
		String result;
		Book inq;
		inq = book.get(serialNum);
		result = "輸入書號：" + serialNum + "\r\n";
		if (inq == null)
			result += "無此書!!";
		else if (inq.status == 0){
			result += "書名：" + inq.bookTitle + "\r\n此書已借走";
		}else if (inq.status == inq.originalBranch){
			result += "書名：" + inq.bookTitle + "\r\n此書在原館，第" + inq.status + "分館";
		}else {
			result += "書名：" + inq.bookTitle + "\r\n此書在別館，第" + inq.status + "分館";
		}
		return result;
	}
	
	public int bookBorrowed(){
		Book.countBookBorrowed(Data.getBook());
		return Book.getBookBorrowed();
	}
	
	public int peopleBorrowing(){
		Uid.countPeopleBorrowing(Data.getUid());
		return Uid.getPeopleBorrowing();
	}
	
	public ArrayList<Rank> top10Bing (Map<String,Uid> uid){
		Data.setuBingRank(uid);
		ArrayList<Rank> top10 = new ArrayList<Rank>();
		int rank = 0;
		for (int i = 0; i < Data.getuBingRank().size() && rank<=10; i++){
			if (Data.getuBingRank().get(i).value == 0){
				continue;
			}else if (i >= 10 && Data.getuBedRank().get(i).value != Data.getuBedRank().get(i-1).value){
				break;
			}else if (i > 0 && Data.getuBingRank().get(i).value == Data.getuBingRank().get(i-1).value){
				Data.getuBingRank().get(i).setRank(rank);
			}else {
				rank++;
				Data.getuBingRank().get(i).setRank(rank);
			}
			top10.add(Data.getuBingRank().get(i));
		}			
		return top10;
	}	
	
	public ArrayList<Rank> top10HasBed (Map<String,Uid> uid){
		Data.setuBedRank(uid);
		ArrayList<Rank> top10 = new ArrayList<Rank>();
		int rank = 0;
		for (int i = 0; i < Data.getuBedRank().size() && rank<=10; i++){
			if (Data.getuBedRank().get(i).value == 0){
				continue;
			}else if (i >= 10 && Data.getuBedRank().get(i).value != Data.getuBedRank().get(i-1).value){
				break;
			}else if (i > 0 && Data.getuBedRank().get(i).value == Data.getuBedRank().get(i-1).value){
				Data.getuBedRank().get(i).setRank(rank);
			}else {
				rank++;
				Data.getuBedRank().get(i).setRank(rank);
			}
			top10.add(Data.getuBedRank().get(i));
		}		
		return top10;
	}	
	
	public ArrayList<Rank> top10BookBed (Map<String,Book> book){
		Data.setbBedRank(book);
		ArrayList<Rank> top10 = new ArrayList<Rank>();
		int rank = 0;
		for (int i = 0; i < Data.getbBedRank().size() && rank<=10; i++){
			if (Data.getbBedRank().get(i).value == 0){
				continue;
			}else if (i >= 10 && Data.getbBedRank().get(i).value != Data.getbBedRank().get(i-1).value){
				break;
			}else if (i > 0 && Data.getbBedRank().get(i).value == Data.getbBedRank().get(i-1).value){
				Data.getbBedRank().get(i).setRank(rank);
			}else {
				rank++;
				Data.getbBedRank().get(i).setRank(rank);
			}
			top10.add(Data.getbBedRank().get(i));
		}		
		return top10;
	}
	
	public String banBList (Map<String,Uid> uid){
		Data.check();
		String result = "";
		Data.setbanBList();
		ArrayList<Uid> list = Data.getBanBList();
		if (list.size() == 0){
			result = "目前無人遭停止借書權!!";
		}else {
			result += "當前停止借書權利人數：" + list.size() + "\r\n";
			for (int i = 0; i < list.size(); i++){
				if (list.get(i).bRight == 0){
					result += "第" + (i+1) + "位， " + list.get(i).uid + "， " + sdf.format(list.get(i).bRightFrom) + "後始可借書!!\r\n";
				}else if (list.get(i).bRight == -1){
					result += "第" + (i+1) + "位， " + list.get(i).uid + "， 尚未還書!!\r\n";
				}
			}
		}
		return result;
	}
	
	public String banRList (Map<String,Uid> uid){
		Data.check();
		String result = "";
		Data.setbanRList(uid);
		ArrayList<Uid> list = Data.getBanRList();
		if (list.size() == 0){
			result = "目前無人遭停止預約權!!";
		}else {
			result = "當前停止預約權利人數：" + list.size() + "\r\n";
			for (int i = 0; i < list.size(); i++){
				result += "第" + (i+1) + "位， " + list.get(i).uid + "， " + sdf.format(list.get(i).rRightFrom) + "後始可預約!!\r\n";
			}
		}
		return result;
	}
	
	public String brcBedBook (){
		String result = "";
		ArrayList<Rank> branchRank = new ArrayList<Rank>(5);
		for (int i = 1; i <= 5; i++){
			int now = Data.getBranchBedBook(i);
			Rank temp = new Rank(Integer.toString(i), now);
			branchRank.add(temp);
		}
		Collections.sort(branchRank);
		int rank = 0;
		for (int i = 0; i < 5; i++){
			if (branchRank.get(i).value == 0){
				branchRank.get(i).setRank(0);
			}else if (i == 0){
				rank++;
				branchRank.get(i).setRank(rank);
			}else if (branchRank.get(i).value == branchRank.get(i-1).value){
				branchRank.get(i).setRank(rank);
			}else {
				rank++;
				branchRank.get(i).setRank(rank);
			}
		}
		for (int i = 0; i < 5; i++){
			if (branchRank.get(i).value == 0){
				break;
			}
			result += "第" + branchRank.get(i).rank + "名，第" + branchRank.get(i).name + "分館，累計借出" + branchRank.get(i).value + "本書\r\n";
		}
		return result;
	}
	
	public String userLog (String uid, Map<String,Uid> user, Map<String,Book> book){
		String result = "";
		if (uid.equals("")){
			return "請輸入ID!!";
		}
		if (user.get(uid) == null){
			return uid + "用戶不存在!!";
		}
		Data.readLog(uid);
		ArrayList<History> logB = Data.getHistoryLogB();
		ArrayList<History> logR = Data.getHistoryLogR();
		if (logB.size() == 0 && logR.size() == 0){
			result += "目前無借閱紀錄!!";
		}else if (logR.size() == 0){
			for (int i = logB.size()-1; i >= 0; i--){
				History log = logB.get(i);
				Book temp = book.get(log.book);
				result += "原館：第" + temp.originalBranch + "分館，書號：" + temp.serialNum + 
						"\r\n\t第" + log.branch + "分館借出，日期：" + sdf.format(log.date) + "尚未歸還\r\n";
			}
		}else if (logR.size() > logB.size()){
			result += "fatal error!!";
		}else {
			for (int i = logB.size()-1; i >= 0; i--){
				// 找到該書資料 使用者資料 借還書要對在一起
				History logb = logB.get(i);
				History logr = null;
				Book temp = book.get(logb.book);
				for (int j = logR.size()-1; j >= 0; j--){
					logr = logR.get(j);
					if (logr.book.equals(temp.serialNum) && !logr.date.before(logb.date)){
						result += "原館：第" + temp.originalBranch + "分館，書號：" + temp.serialNum + 
								"\r\n\t第" + logb.branch + "分館借出，日期：" + sdf.format(logb.date) +
								"，第" + logr.branch + "分館歸還，日期：" + sdf.format(logr.date) + "\r\n";
						logR.remove(logr);
					}
				}
				if(logr == null){
					result += "原館：第" + temp.originalBranch + "分館，書號：" + temp.serialNum + 
							"\r\n\t第" + logb.branch + "借出，日期：" + sdf.format(logb.date) + "尚未歸還\r\n";
				}
			}
		}
		return result;
	}
	public String searchName(String keyword, Map<String,Book> book){
	    String result = "";
	    ArrayList<Book> search = new ArrayList<Book>();
		Book now;
	    for (Map.Entry<String,Book> entry : book.entrySet()){
	    	now = entry.getValue();
			if(now.bookTitle.indexOf(keyword) != -1) {
				search.add(now);
			}
		}
	    for (int i = 0; i < search.size(); i++) {
	    	now = search.get(i);
	    	result += "查到的第" + (i+1) + "本，原館：第" + now.originalBranch + "分館，書碼：" + now.serialNum + "，狀態：";
	    	if (now.status != 0 && now.originalBranch == now.status){
	    		result += "在原館內!!\r\n";
	    	}else if (now.status != 0){
	    		result += "在別館，第" + now.status + "分館!!\r\n";
	    	}else {
	    		result += "已借走!!\r\n";
	    	}
	    }
	    return result;
	}
	    
}

