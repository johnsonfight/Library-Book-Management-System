import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Transfer {

	public String transfer(String user, int branch, String serialNum, String tDay, Map<String, Uid> uid, Map<String, Book> book) {
		String result = "";
		
		boolean check = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		if (user.equals("")) {
			result += "請輸入用戶Id!!\r\n";
			check = false;
		}if (branch == 0) {
			result += "請選擇分館!!\r\n";
			check = false;
		}if (serialNum.equals("")) {
			result += "請輸入書號!!\r\n";
			check = false;
		}if (tDay.equals("")) {
			result += "請輸入日期!!\r\n";
			check = false;
		}if (check == true) {
			sdf.setLenient(false);
			try {
				today = sdf.parse(tDay, new ParsePosition(0));
				if (today == null){
					throw new NullPointerException();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				result += "日期格式錯誤!!";
				check = false;
			}
			if (check == true && today.before(Data.getCurrent())) {
				result += "日期錯誤!!";
				check = false;
			}
		}
		if (check == false){
			return result;
		}
		
		Uid transferUser = uid.get(user);
		Book transferBook = book.get(serialNum);
		Data.setCurrent(today);
		Data.check();
		
		if (transferUser == null) { 								
			result = "館際調閱失敗!!\r\n此用戶不存在!!";					// 失敗1
		}  else if (transferBook == null) {
			result = "館際調閱失敗!!\r\n該書籍不存在!!";					// 失敗3
		}  else if (!transferUser.uid.equals(transferBook.r1) && !transferUser.uid.equals(transferBook.r2) && !transferUser.uid.equals(transferBook.r3)){
			result = "館際調閱失敗!!\r\n尚未預約該書!!";					// 失敗2
		}  else if (transferUser.uid.equals(transferBook.r1) && today.equals(transferBook.rDue)) {
			result = "館際調閱失敗!!\r\n已達預約取書期間的最後一日!!";		// 失敗4
		}  else if (transferBook.originalBranch == branch) {
			result = "館際調閱失敗!!\r\n欲調閱館為原館!!";				// 失敗5
		}  else { 
			result = "調閱成功!!";
			Calendar c = Calendar.getInstance();
			c.setTime(today);

			if (user.equals(transferBook.r1)){
				transferBook.transTo1 = branch;
			} else if (user.equals(transferBook.r2)){
				transferBook.transTo2 = branch;
			} else if (user.equals(transferBook.r3)){
				transferBook.transTo3 = branch;
			}
			if (transferBook.rUsers == 1 && transferBook.status != 0){	// 書未借出 申請館際調閱
				transferBook.bDue = c.getTime();						// 
			}
			Data.setUid(transferUser);
			Data.setBook(transferBook);
			Data.record(new History(user, 5, String.valueOf(branch), serialNum, tDay));  //操作者記錄operation為5, branch為調閱欲達館名
		}
		return result;
	}
}
