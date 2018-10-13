import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Reserve {
	public String reserve(String user, String serialNum, String rsDay, Map<String, Uid> uid, Map<String, Book> book) {
		String result = "";
		boolean check = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();

		if (user.equals("")) {
			result += "請輸入用戶Id!!\r\n";
			check = false;
		}
		if (rsDay.equals("")) {
			result += "請輸入日期!!\r\n";
			check = false;
		}
		if (serialNum.equals("")) {
			result += "請輸入書號!!";
			check = false;
		}
		if (check == true) {
			sdf.setLenient(false);
			try {
				// today = (Date) sdf.parse(date.getText());
				today = sdf.parse(rsDay, new ParsePosition(0));
				if (today == null) {
					throw new NullPointerException();
				}
			} catch (Exception ex) {
				// ex.printStackTrace();
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

		// ------------------------------------------------------------------------------------------------
		if (reqUser == null) {
			result = "預約失敗!!\r\n此用戶不存在!!";
		} else if (reqUser.bRight == 0 && reqUser.bRightFrom.after(today)) {
			result = "預約失敗!!\r\n用戶因逾期還書遭停權，" + sdf.format(reqUser.bRightFrom) + "始可預約";
		} else if (reqUser.bRight == -1) {
			result = "預約失敗!!\r\n用戶因逾期還書遭停權，目前無法預約!!";
		} else if (reqUser.rRightFrom.after(today)) {
			result = "預約失敗!!\r\n用戶因逾期取書遭停權，" + sdf.format(reqUser.rRightFrom) + "始可預約";
		} else if (reqUser.rQty == 5) {
			result = "預約失敗!!\r\n預約數量已達上限!!";
		} else if (req == null) {
			result = "預約失敗!!\r\n欲預約書籍不存在!!";
		} else if (req.bBy.equals(reqUser.uid)) {
			result = "預約失敗!!\r\n用戶已借此書!!";
		} else if (req.rUsers == 3) {
			result = "預約失敗!!\r\n欲預約書籍已被其他三人預約!!";
		} else if (req.r1.equals(user) || req.r2.equals(user) || req.r3.equals(user)) {
			result = "預約失敗!!\r\n用戶已預約此書!!";
		} else {
			reqUser.rQty++;
			Data.setUid(reqUser);
			req.rUsers++;
			switch (req.rUsers) {
			case 1:
				req.r1 = user;
				break;
			case 2:
				req.r2 = user;
				break;
			case 3:
				req.r3 = user;
				break;
			default:
				System.out.println("error! reserved users number : " + req.rUsers);
			}
			result = "預約成功!!";
			if (req.status != 0 && req.rUsers == 1) {  // 預約者為第一位 且書未被借走
				Calendar c = Calendar.getInstance();
				c.setTime(today);
				c.add(Calendar.DATE, 5);
				req.rDue = c.getTime();
				result += "\r\n請於" + sdf.format(req.rDue) + "前取書!!";
			}else {
				result += "\r\n待前一位借書人還書後五天內即可取書";
			}
			result += "\r\n仍可預約數量：" + (5 - reqUser.rQty);
			Data.setBook(req);
			Data.record(new History(user, 4, null, serialNum, rsDay));
		}
		return result;
	}
}
