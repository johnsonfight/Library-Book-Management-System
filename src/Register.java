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
			result += "�п�J�Τ�Id!!\r\n";
			check = false;
		}if (regDay.equals("")) {
			result += "�п�J���!!\r\n";
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
				result += "����榡���~!!";
				check = false;
			}
			if (check == true && today.before(Data.getCurrent())) {
				result += "������~!!";
				check = false;
			}
		}
		if (check == false)
			return result;

		Data.setCurrent(today);
		Data.check();
		
		if (uid.get(user) != null) {
			result += "���U����!!\r\n��UserID�w�s�b!!";
		} else if (user.length() != 9) {
			result += "���U����!!\r\nUserID���ŦX�W�h1!!";
		} else {
			first = user.charAt(0);
			year = user.substring(1, 3);
			deptNum = user.substring(3, 6);
			num = user.substring(6);
			if ((first != 'B' && first != 'R' && first != 'D' && first != 'T')) {
				result += "���U����!!\r\nUserID���ŦX�W�h!!";
			} else if (Integer.valueOf(year) < 34 && Integer.valueOf(year) > 4) {
				result += "���U����!!\r\nUserID���ŦX�W�h!!";
			} else if (dept.get(deptNum) == null) {
				result += "���U����!!\r\nUserID���ŦX�W�h!!";
			} else if (Integer.valueOf(num) > 299) {
				result += "���U����!!\r\nUserID���ŦX�W�h!!";
			} else if ((first == 'B' || first == 'T') && !(user.charAt(4) == '0' || user.charAt(4) == '1')) {
				result += "���U����!!\r\nUserID���ŦX�W�h!!";
			} else if ((first == 'R' || first == 'D') && !(user.charAt(4) == '2' || user.charAt(4) == '3')) {
				result += "���U����!!\r\nUserID���ŦX�W�h!!";
			} else {
				Uid sign = new Uid(user);
				uid.put(user, sign);
				result += "���U���\!!\r\nUserID : " + user;
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
			result += "�п�J�Τ�Id!!\r\n";
			check = false;
		}if (derDay.equals("")) {
			result += "�п�J���!!\r\n";
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
				result += "����榡���~!!";
				check = false;
			}
			if (check == true && today.before(Data.getCurrent())) {
				result += "������~!!";
				check = false;
			}
		}
		if (check == false)
			return result;
		
		if (uid.get(user) == null) {
			result += "���P����!!\r\n��UserID���s�b!!";
		} else if (uid.get(user).bQty != 0) {
			result += "���P����!!\r\n���Τ�|���ѥ��k��!!";
		} else {
			uid.remove(user);
			result += "���P���\!!";
		}
		return result;
	}
}
