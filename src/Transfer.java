import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Transfer {

	public String transfer(String user, int branch, String serialNum, String tDay, Map<String, Uid> uid, Map<String, Book> book) {
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
		}if (serialNum.equals("")) {
			result += "�п�J�Ѹ�!!\r\n";
			check = false;
		}if (tDay.equals("")) {
			result += "�п�J���!!\r\n";
			check = false;
		}if (check == true) {
			sdf.setLenient(false);
			try {
				today = sdf.parse(tDay, new ParsePosition(0));
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
		if (check == false){
			return result;
		}
		
		Uid transferUser = uid.get(user);
		Book transferBook = book.get(serialNum);
		Data.setCurrent(today);
		Data.check();
		
		if (transferUser == null) { 								
			result = "�]�ڽվ\����!!\r\n���Τᤣ�s�b!!";					// ����1
		}  else if (transferBook == null) {
			result = "�]�ڽվ\����!!\r\n�Ӯ��y���s�b!!";					// ����3
		}  else if (!transferUser.uid.equals(transferBook.r1) && !transferUser.uid.equals(transferBook.r2) && !transferUser.uid.equals(transferBook.r3)){
			result = "�]�ڽվ\����!!\r\n�|���w���Ӯ�!!";					// ����2
		}  else if (transferUser.uid.equals(transferBook.r1) && today.equals(transferBook.rDue)) {
			result = "�]�ڽվ\����!!\r\n�w�F�w�����Ѵ������̫�@��!!";		// ����4
		}  else if (transferBook.originalBranch == branch) {
			result = "�]�ڽվ\����!!\r\n���վ\�]�����]!!";				// ����5
		}  else { 
			result = "�վ\���\!!";
			Calendar c = Calendar.getInstance();
			c.setTime(today);

			if (user.equals(transferBook.r1)){
				transferBook.transTo1 = branch;
			} else if (user.equals(transferBook.r2)){
				transferBook.transTo2 = branch;
			} else if (user.equals(transferBook.r3)){
				transferBook.transTo3 = branch;
			}
			if (transferBook.rUsers == 1 && transferBook.status != 0){	// �ѥ��ɥX �ӽ��]�ڽվ\
				transferBook.bDue = c.getTime();						// 
			}
			Data.setUid(transferUser);
			Data.setBook(transferBook);
			Data.record(new History(user, 5, String.valueOf(branch), serialNum, tDay));  //�ާ@�̰O��operation��5, branch���վ\���F�]�W
		}
		return result;
	}
}
