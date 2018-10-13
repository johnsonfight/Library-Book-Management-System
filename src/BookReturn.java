import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class BookReturn {

	public String bookReturn(String user, int branch, String serialNum, String rDay, Map<String, Uid> uid,
			Map<String, Book> book) {
		String result = "";

		boolean check = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		if (user.equals("")) {
			result += "請輸入用戶Id!!\r\n";
			check = false;
		}
		if (branch == 0) {
			result += "請選擇分館!!\r\n";
			check = false;
		}
		if (serialNum.equals("")) {
			result += "請輸入書號!!";
			check = false;
		}
		if (rDay.equals("")) {
			result += "請輸入日期!!\r\n";
			check = false;
		}
		if (check == true) {
			sdf.setLenient(false);
			try {
				// today = (Date) sdf.parse(date.getText());
				today = sdf.parse(rDay, new ParsePosition(0));
				if (today == null) {
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
		if (reqUser == null) { // 還書失敗
			result = "還書失敗!!\r\n此用戶不存在!!";
		} else if (req == null) {
			result = "還書失敗!!\r\n該書籍不存在!!";
		} else if (req.status != 0 || user.compareTo(req.bBy) != 0) {
			result = "還書失敗!!\r\n用戶未借該書!!";
		} else {
			result = "還書成功!!";
			Calendar c = Calendar.getInstance();
			c.setTime(today);

			if (today.after(req.bDue)) { // 逾期
				reqUser.bRight = 0;
				c.setTime(req.bDue);
				long borrowDue = c.getTimeInMillis();
				c.setTime(today);
				long returnDay = c.getTimeInMillis();
				long betweenDays = 2 * (returnDay - borrowDue) / (1000 * 3600 * 24);

				if (today.before(reqUser.bRightFrom)) { // 暫停借書中
					c.setTime(reqUser.bRightFrom);
					if (betweenDays >= 30)
						c.add(Calendar.DATE, 60);
					else
						c.add(Calendar.DATE, (int) betweenDays); // 逾期天數***
				} else {
					if (betweenDays >= 30)
						c.add(Calendar.DATE, 60);
					else
						c.add(Calendar.DATE, (int) betweenDays); // 逾期天數***
				}
				reqUser.bRightFrom = c.getTime();
				reqUser.bQty--;
				result += "\r\n暫停借書權至" + sdf.format(reqUser.bRightFrom) + "!!\r\n仍可借數量：" + (10 - reqUser.bQty);
			} else {
				reqUser.bQty--;
				result = "還書成功!!\r\n仍可借數量：" + (10 - reqUser.bQty);
			}
			if (branch == req.originalBranch) { // 原館還書
				req.status = req.originalBranch;
			} else if (branch != req.originalBranch) { // 異館還書
				req.status = branch;
				req.bDue = today; // 下次指令檢查書籍若status != 0 bDue為異館還書日
			}
			if (req.rUsers != 0) { // 若有預約：設定rDue
				c.setTime(today);
				c.add(Calendar.DATE, 5);
				req.rDue = c.getTime();
			}
			// UID set
			reqUser.bTimes++;
			Data.setUid(reqUser);
			req.bTime++; // Book set
			req.rTime = 0;
			Data.setBook(req);
			Data.record(new History(user, 2, String.valueOf(branch), serialNum, rDay));
		}
		return result;
	}
}
