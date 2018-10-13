import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class Borrow {

	public String borrow(String user, int branch, String serialNum, String bDay, Map<String,Uid> uid, Map<String,Book> book){
		
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
		}if (bDay.equals("")) {
			result += "請輸入日期!!\r\n";
			check = false;
		}if (serialNum.equals("")) {
			result += "請輸入書號!!";
			check = false;
		}if (check == true) {
			sdf.setLenient(false);
			try {
				//today = (Date) sdf.parse(date.getText());
				today = sdf.parse(bDay, new ParsePosition(0));
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
		if (check == false)
			return result;
		
		Uid reqUser = uid.get(user);
		Book req = book.get(serialNum);

		Data.setCurrent(today);
		Data.check();
		if (reqUser == null){
			result = "借書失敗!!\r\n此用戶不存在!!";
		}else if (reqUser.bRight == 0 && reqUser.bRightFrom.after(today)){
			result = "借書失敗!!\r\n用戶因逾期還書遭停權，" + sdf.format(reqUser.bRightFrom) + "始可借書";
		}else if (reqUser.bRight == -1){
			result = "借書失敗!!\r\n用戶因逾期還書遭停權，目前無法借書!!";
		}else if (reqUser.bQty == 10){
			result = "借書失敗!!\r\n用戶借書數量已達上限!!";
		}else if (req == null){
			result = "借書失敗!!\r\n欲借書籍不存在!!";
		}else if (req.status != branch){
			result = "借書失敗!!\r\n欲借書籍不再指定館內\r\n";
			if (req.status == 0 && user.equals(req.bBy)){
				result += "用戶已借出該書籍!!";
				System.out.println("same" + user + " and" + req.bBy);
			}else if (req.status == 0){
				result += "欲借書籍已借出!!";
			}else {
				result += "欲借書籍在第" + req.status + "分館!!";
			}
		}else if (req.rUsers != 0 && !req.r1.equals(user) && !req.r2.equals(user) && !req.r3.equals(user)){
			result = "借書失敗!!\r\n欲借書籍已被預約!!";
		}else if (req.r2.equals(user) && req.r3.equals(user)){
			result = "預約取書失敗!!\r\n待其他預約人還書後始可取書!!";
		}else if (req.r1.equals(user) && req.transTo1 != branch){
			result = "預約取書失敗!!\r\n請至調閱館：第" + req.transTo1 + "分館取書!!";
		}else {
			reqUser.bQty++;
			Data.setUid(reqUser);
			
			req.bBy = reqUser.uid;
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(today); 
			c.add(Calendar.DATE, 30);
			req.bDue = c.getTime();
			
			req.status = 0;
			Data.setBook(req);
			Data.setBranchBedBook(branch);
			// System.out.println(Data.getBranchBedBook(branch));
			if (req.r1.equals(user)){
				req.rUsers--;
				req.r1 = req.r2;
				req.r2 = req.r3;
				req.r3 = "B00000000";
				result = "預約取書成功!!\r\n請於" + sdf.format(req.bDue) + "前還書!!\r\n仍可借數量：" + (10-reqUser.bQty);
			}else {
				result = "借書成功!!\r\n請於" + sdf.format(req.bDue) + "前還書!!\r\n仍可借數量：" + (10-reqUser.bQty);
			}
			Data.record(new History(user, 1, String.valueOf(branch), serialNum, bDay));
		}
		// System.out.println(sdf.format(Data.getCurrent()));
		return result;
	}
}
