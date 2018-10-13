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
			result += "�п�J�Τ�Id!!\r\n";
			check = false;
		}if (rnDay.equals("")) {
			result += "�п�J���!!\r\n";
			check = false;
		}if (serialNum.equals("")) {
			result += "�п�J�Ѹ�!!";
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
		if (reqUser == null){
			result = "��ɥ���!!\r\n���Τᤣ�s�b!!";
		}else if (reqUser.bRight == 0 && reqUser.bRightFrom.after(today)){
			result = "��ɥ���!!\r\n�Τ�]�O���ٮѾD���v�A" + sdf.format(reqUser.bRightFrom) + "�l�i�ɮ�";
		}else if (reqUser.bRight == -1){
			result = "��ɥ���!!\r\n�Τ�]�O���ٮѾD���v�A�ثe�L�k���!!";
		}else if (req == null){
			result = "��ɥ���!!\r\n���ɮ��y���s�b!!";
		}else if (!req.bBy.equals(reqUser.uid)){
			result = "��ɥ���!!\r\n�Τ᥼�ɸӮ�!!";
		}else if (req.rTime == 2){
			result = "��ɥ���!!\r\n��ɦ��Ƥw�F�W��!!";
		}else if (req.rUsers != 0){
			result = "��ɥ���!!\r\n����ɮ��y�w�Q�w��!!";
		}else {
			Calendar c = Calendar.getInstance(); 
			c.setTime(today); 
			c.add(Calendar.DATE, 30);
			req.bDue = c.getTime();
			req.rTime ++;
			Data.setBook(req);
			
			result = "��ɦ��\!!\r\n�Щ�" + sdf.format(req.bDue) + "�e�ٮ�!!\r\n���i�ɼƶq�G" + (10-reqUser.bQty);
			Data.record(new History(user, 3, null, serialNum, rnDay));
		}
		return result;
	}
}
