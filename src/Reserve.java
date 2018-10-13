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
			result += "�п�J�Τ�Id!!\r\n";
			check = false;
		}
		if (rsDay.equals("")) {
			result += "�п�J���!!\r\n";
			check = false;
		}
		if (serialNum.equals("")) {
			result += "�п�J�Ѹ�!!";
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

		Uid reqUser = uid.get(user);
		Book req = book.get(serialNum);

		Data.setCurrent(today);
		Data.check();

		// ------------------------------------------------------------------------------------------------
		if (reqUser == null) {
			result = "�w������!!\r\n���Τᤣ�s�b!!";
		} else if (reqUser.bRight == 0 && reqUser.bRightFrom.after(today)) {
			result = "�w������!!\r\n�Τ�]�O���ٮѾD���v�A" + sdf.format(reqUser.bRightFrom) + "�l�i�w��";
		} else if (reqUser.bRight == -1) {
			result = "�w������!!\r\n�Τ�]�O���ٮѾD���v�A�ثe�L�k�w��!!";
		} else if (reqUser.rRightFrom.after(today)) {
			result = "�w������!!\r\n�Τ�]�O�����ѾD���v�A" + sdf.format(reqUser.rRightFrom) + "�l�i�w��";
		} else if (reqUser.rQty == 5) {
			result = "�w������!!\r\n�w���ƶq�w�F�W��!!";
		} else if (req == null) {
			result = "�w������!!\r\n���w�����y���s�b!!";
		} else if (req.bBy.equals(reqUser.uid)) {
			result = "�w������!!\r\n�Τ�w�ɦ���!!";
		} else if (req.rUsers == 3) {
			result = "�w������!!\r\n���w�����y�w�Q��L�T�H�w��!!";
		} else if (req.r1.equals(user) || req.r2.equals(user) || req.r3.equals(user)) {
			result = "�w������!!\r\n�Τ�w�w������!!";
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
			result = "�w�����\!!";
			if (req.status != 0 && req.rUsers == 1) {  // �w���̬��Ĥ@�� �B�ѥ��Q�ɨ�
				Calendar c = Calendar.getInstance();
				c.setTime(today);
				c.add(Calendar.DATE, 5);
				req.rDue = c.getTime();
				result += "\r\n�Щ�" + sdf.format(req.rDue) + "�e����!!";
			}else {
				result += "\r\n�ݫe�@��ɮѤH�ٮѫ᤭�Ѥ��Y�i����";
			}
			result += "\r\n���i�w���ƶq�G" + (5 - reqUser.rQty);
			Data.setBook(req);
			Data.record(new History(user, 4, null, serialNum, rsDay));
		}
		return result;
	}
}
