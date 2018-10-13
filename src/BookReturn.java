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
			result += "�п�J�Τ�Id!!\r\n";
			check = false;
		}
		if (branch == 0) {
			result += "�п�ܤ��]!!\r\n";
			check = false;
		}
		if (serialNum.equals("")) {
			result += "�п�J�Ѹ�!!";
			check = false;
		}
		if (rDay.equals("")) {
			result += "�п�J���!!\r\n";
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
		if (reqUser == null) { // �ٮѥ���
			result = "�ٮѥ���!!\r\n���Τᤣ�s�b!!";
		} else if (req == null) {
			result = "�ٮѥ���!!\r\n�Ӯ��y���s�b!!";
		} else if (req.status != 0 || user.compareTo(req.bBy) != 0) {
			result = "�ٮѥ���!!\r\n�Τ᥼�ɸӮ�!!";
		} else {
			result = "�ٮѦ��\!!";
			Calendar c = Calendar.getInstance();
			c.setTime(today);

			if (today.after(req.bDue)) { // �O��
				reqUser.bRight = 0;
				c.setTime(req.bDue);
				long borrowDue = c.getTimeInMillis();
				c.setTime(today);
				long returnDay = c.getTimeInMillis();
				long betweenDays = 2 * (returnDay - borrowDue) / (1000 * 3600 * 24);

				if (today.before(reqUser.bRightFrom)) { // �Ȱ��ɮѤ�
					c.setTime(reqUser.bRightFrom);
					if (betweenDays >= 30)
						c.add(Calendar.DATE, 60);
					else
						c.add(Calendar.DATE, (int) betweenDays); // �O���Ѽ�***
				} else {
					if (betweenDays >= 30)
						c.add(Calendar.DATE, 60);
					else
						c.add(Calendar.DATE, (int) betweenDays); // �O���Ѽ�***
				}
				reqUser.bRightFrom = c.getTime();
				reqUser.bQty--;
				result += "\r\n�Ȱ��ɮ��v��" + sdf.format(reqUser.bRightFrom) + "!!\r\n���i�ɼƶq�G" + (10 - reqUser.bQty);
			} else {
				reqUser.bQty--;
				result = "�ٮѦ��\!!\r\n���i�ɼƶq�G" + (10 - reqUser.bQty);
			}
			if (branch == req.originalBranch) { // ���]�ٮ�
				req.status = req.originalBranch;
			} else if (branch != req.originalBranch) { // ���]�ٮ�
				req.status = branch;
				req.bDue = today; // �U�����O�ˬd���y�Ystatus != 0 bDue�����]�ٮѤ�
			}
			if (req.rUsers != 0) { // �Y���w���G�]�wrDue
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
