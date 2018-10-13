import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Register {

	char first;
	String year;
	String deptNum;
	String num;

	public String register(String user, String regDay, Map<String, Uid> uid, Map<String, String> dept) {
		String result = "";
		boolean check = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();

		if (user.equals("")) {
			result += "請輸入用戶Id!!\r\n";
			check = false;
		}if (regDay.equals("")) {
			result += "請輸入日期!!\r\n";
			check = false;
		}if (check == true) {
			sdf.setLenient(false);
			try {
				//today = (Date) sdf.parse(date.getText());
				today = sdf.parse(regDay, new ParsePosition(0));
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

		Data.setCurrent(today);
		Data.check();
		
		if (uid.get(user) != null) {
			result += "註冊失敗!!\r\n此UserID已存在!!";
		} else if (user.length() != 9) {
			result += "註冊失敗!!\r\nUserID不符合規則1!!";
		} else {
			first = user.charAt(0);
			year = user.substring(1, 3);
			deptNum = user.substring(3, 6);
			num = user.substring(6);
			if ((first != 'B' && first != 'R' && first != 'D' && first != 'T')) {
				result += "註冊失敗!!\r\nUserID不符合規則!!";
			} else if (Integer.valueOf(year) < 34 && Integer.valueOf(year) > 4) {
				result += "註冊失敗!!\r\nUserID不符合規則!!";
			} else if (dept.get(deptNum) == null) {
				result += "註冊失敗!!\r\nUserID不符合規則!!";
			} else if (Integer.valueOf(num) > 299) {
				result += "註冊失敗!!\r\nUserID不符合規則!!";
			} else if ((first == 'B' || first == 'T') && !(user.charAt(4) == '0' || user.charAt(4) == '1')) {
				result += "註冊失敗!!\r\nUserID不符合規則!!";
			} else if ((first == 'R' || first == 'D') && !(user.charAt(4) == '2' || user.charAt(4) == '3')) {
				result += "註冊失敗!!\r\nUserID不符合規則!!";
			} else {
				Uid sign = new Uid(user);
				uid.put(user, sign);
				result += "註冊成功!!\r\nUserID : " + user;
				Data.record(new History(user, 0, null, null, regDay));
			}
		}
		return result;
	}
	
	public String deRegister(String user, String derDay, Map<String, Uid> uid) {
		String result = "";
		boolean check = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		
		if (user.equals("")) {
			result += "請輸入用戶Id!!\r\n";
			check = false;
		}if (derDay.equals("")) {
			result += "請輸入日期!!\r\n";
			check = false;
		}if (check == true) {
			sdf.setLenient(false);
			try {
				//today = (Date) sdf.parse(date.getText());
				today = sdf.parse(derDay, new ParsePosition(0));
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
		
		if (uid.get(user) == null) {
			result += "註銷失敗!!\r\n此UserID不存在!!";
		} else if (uid.get(user).bQty != 0) {
			result += "註銷失敗!!\r\n此用戶尚有書未歸還!!";
		} else {
			uid.remove(user);
			result += "註銷成功!!";
		}
		return result;
	}
}
