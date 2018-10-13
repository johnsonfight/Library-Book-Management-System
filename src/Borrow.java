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
			result += "�п�J�Τ�Id!!\r\n";
			check = false;
		}if (branch == 0) {
			result += "�п�ܤ��]!!\r\n";
			check = false;
		}if (bDay.equals("")) {
			result += "�п�J���!!\r\n";
			check = false;
		}if (serialNum.equals("")) {
			result += "�п�J�Ѹ�!!";
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
			result = "�ɮѥ���!!\r\n���Τᤣ�s�b!!";
		}else if (reqUser.bRight == 0 && reqUser.bRightFrom.after(today)){
			result = "�ɮѥ���!!\r\n�Τ�]�O���ٮѾD���v�A" + sdf.format(reqUser.bRightFrom) + "�l�i�ɮ�";
		}else if (reqUser.bRight == -1){
			result = "�ɮѥ���!!\r\n�Τ�]�O���ٮѾD���v�A�ثe�L�k�ɮ�!!";
		}else if (reqUser.bQty == 10){
			result = "�ɮѥ���!!\r\n�Τ�ɮѼƶq�w�F�W��!!";
		}else if (req == null){
			result = "�ɮѥ���!!\r\n���ɮ��y���s�b!!";
		}else if (req.status != branch){
			result = "�ɮѥ���!!\r\n���ɮ��y���A���w�]��\r\n";
			if (req.status == 0 && user.equals(req.bBy)){
				result += "�Τ�w�ɥX�Ӯ��y!!";
				System.out.println("same" + user + " and" + req.bBy);
			}else if (req.status == 0){
				result += "���ɮ��y�w�ɥX!!";
			}else {
				result += "���ɮ��y�b��" + req.status + "���]!!";
			}
		}else if (req.rUsers != 0 && !req.r1.equals(user) && !req.r2.equals(user) && !req.r3.equals(user)){
			result = "�ɮѥ���!!\r\n���ɮ��y�w�Q�w��!!";
		}else if (req.r2.equals(user) && req.r3.equals(user)){
			result = "�w�����ѥ���!!\r\n�ݨ�L�w���H�ٮѫ�l�i����!!";
		}else if (req.r1.equals(user) && req.transTo1 != branch){
			result = "�w�����ѥ���!!\r\n�Цܽվ\�]�G��" + req.transTo1 + "���]����!!";
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
				result = "�w�����Ѧ��\!!\r\n�Щ�" + sdf.format(req.bDue) + "�e�ٮ�!!\r\n���i�ɼƶq�G" + (10-reqUser.bQty);
			}else {
				result = "�ɮѦ��\!!\r\n�Щ�" + sdf.format(req.bDue) + "�e�ٮ�!!\r\n���i�ɼƶq�G" + (10-reqUser.bQty);
			}
			Data.record(new History(user, 1, String.valueOf(branch), serialNum, bDay));
		}
		// System.out.println(sdf.format(Data.getCurrent()));
		return result;
	}
}
