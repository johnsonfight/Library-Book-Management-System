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
		result = "��J�Ѹ��G" + serialNum + "\r\n";
		if (inq == null)
			result += "�L����!!";
		else if (inq.status == 0){
			result += "�ѦW�G" + inq.bookTitle + "\r\n���Ѥw�ɨ�";
		}else if (inq.status == inq.originalBranch){
			result += "�ѦW�G" + inq.bookTitle + "\r\n���Ѧb���]�A��" + inq.status + "���]";
		}else {
			result += "�ѦW�G" + inq.bookTitle + "\r\n���Ѧb�O�]�A��" + inq.status + "���]";
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
			result = "�ثe�L�H�D����ɮ��v!!";
		}else {
			result += "��e����ɮ��v�Q�H�ơG" + list.size() + "\r\n";
			for (int i = 0; i < list.size(); i++){
				if (list.get(i).bRight == 0){
					result += "��" + (i+1) + "��A " + list.get(i).uid + "�A " + sdf.format(list.get(i).bRightFrom) + "��l�i�ɮ�!!\r\n";
				}else if (list.get(i).bRight == -1){
					result += "��" + (i+1) + "��A " + list.get(i).uid + "�A �|���ٮ�!!\r\n";
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
			result = "�ثe�L�H�D����w���v!!";
		}else {
			result = "��e����w���v�Q�H�ơG" + list.size() + "\r\n";
			for (int i = 0; i < list.size(); i++){
				result += "��" + (i+1) + "��A " + list.get(i).uid + "�A " + sdf.format(list.get(i).rRightFrom) + "��l�i�w��!!\r\n";
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
			result += "��" + branchRank.get(i).rank + "�W�A��" + branchRank.get(i).name + "���]�A�֭p�ɥX" + branchRank.get(i).value + "����\r\n";
		}
		return result;
	}
	
	public String userLog (String uid, Map<String,Uid> user, Map<String,Book> book){
		String result = "";
		if (uid.equals("")){
			return "�п�JID!!";
		}
		if (user.get(uid) == null){
			return uid + "�Τᤣ�s�b!!";
		}
		Data.readLog(uid);
		ArrayList<History> logB = Data.getHistoryLogB();
		ArrayList<History> logR = Data.getHistoryLogR();
		if (logB.size() == 0 && logR.size() == 0){
			result += "�ثe�L�ɾ\����!!";
		}else if (logR.size() == 0){
			for (int i = logB.size()-1; i >= 0; i--){
				History log = logB.get(i);
				Book temp = book.get(log.book);
				result += "���]�G��" + temp.originalBranch + "���]�A�Ѹ��G" + temp.serialNum + 
						"\r\n\t��" + log.branch + "���]�ɥX�A����G" + sdf.format(log.date) + "�|���k��\r\n";
			}
		}else if (logR.size() > logB.size()){
			result += "fatal error!!";
		}else {
			for (int i = logB.size()-1; i >= 0; i--){
				// ���ӮѸ�� �ϥΪ̸�� ���ٮѭn��b�@�_
				History logb = logB.get(i);
				History logr = null;
				Book temp = book.get(logb.book);
				for (int j = logR.size()-1; j >= 0; j--){
					logr = logR.get(j);
					if (logr.book.equals(temp.serialNum) && !logr.date.before(logb.date)){
						result += "���]�G��" + temp.originalBranch + "���]�A�Ѹ��G" + temp.serialNum + 
								"\r\n\t��" + logb.branch + "���]�ɥX�A����G" + sdf.format(logb.date) +
								"�A��" + logr.branch + "���]�k�١A����G" + sdf.format(logr.date) + "\r\n";
						logR.remove(logr);
					}
				}
				if(logr == null){
					result += "���]�G��" + temp.originalBranch + "���]�A�Ѹ��G" + temp.serialNum + 
							"\r\n\t��" + logb.branch + "�ɥX�A����G" + sdf.format(logb.date) + "�|���k��\r\n";
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
	    	result += "�d�쪺��" + (i+1) + "���A���]�G��" + now.originalBranch + "���]�A�ѽX�G" + now.serialNum + "�A���A�G";
	    	if (now.status != 0 && now.originalBranch == now.status){
	    		result += "�b���]��!!\r\n";
	    	}else if (now.status != 0){
	    		result += "�b�O�]�A��" + now.status + "���]!!\r\n";
	    	}else {
	    		result += "�w�ɨ�!!\r\n";
	    	}
	    }
	    return result;
	}
	    
}

