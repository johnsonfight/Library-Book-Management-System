import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Renew {
	public String renew(String user, String serialNum, String rnDay, Map<String,Uid> uid, Map<String,Book> book){
		
		String result = "";
		boolean check = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		
		if (user.equals("")) {
			result += "請輸入用戶Id!!\r\n";
			check = false;
		}if (rnDay.equals("")) {
			result += "請輸入日期!!\r\n";
			check = false;
		}if (serialNum.equals("")) {
			result += "請輸入書號!!";
			check = false;
		}if (check == true) {
			sdf.setLenient(false);
			try {
				//today = (Date) sdf.parse(date.getText());
				today = sdf.parse(rnDay, new ParsePosition(0));
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
			result = "續借失敗!!\r\n此用戶不存在!!";
		}else if (reqUser.bRight == 0 && reqUser.bRightFrom.after(today)){
			result = "續借失敗!!\r\n用戶因逾期還書遭停權，" + sdf.format(reqUser.bRightFrom) + "始可借書";
		}else if (reqUser.bRight == -1){
			result = "續借失敗!!\r\n用戶因逾期還書遭停權，目前無法續借!!";
		}else if (req == null){
			result = "續借失敗!!\r\n欲借書籍不存在!!";
		}else if (!req.bBy.equals(reqUser.uid)){
			result = "續借失敗!!\r\n用戶未借該書!!";
		}else if (req.rTime == 2){
			result = "續借失敗!!\r\n續借次數已達上限!!";
		}else if (req.rUsers != 0){
			result = "續借失敗!!\r\n欲續借書籍已被預約!!";
		}else {
			Calendar c = Calendar.getInstance(); 
			c.setTime(today); 
			c.add(Calendar.DATE, 30);
			req.bDue = c.getTime();
			req.rTime ++;
			Data.setBook(req);
			
			result = "續借成功!!\r\n請於" + sdf.format(req.bDue) + "前還書!!\r\n仍可借數量：" + (10-reqUser.bQty);
			Data.record(new History(user, 3, null, serialNum, rnDay));
		}
		return result;
	}
}
