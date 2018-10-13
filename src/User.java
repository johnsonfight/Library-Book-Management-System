import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class User {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	public String addUser(String user, Date regDate, Map<String,Uid> uid){
		String result="";
		String error = " 學號不符合規則!!";
		char prefix[] = {'B', 'R', 'D', 'T'};
		Uid tmp = uid.get(user);
		if (tmp != null){
			result = "註冊失敗，此User ID已存在!!";
			return result;
		}
		else {
			if (user.length() != 9){
				result = error;
			}else if (user.charAt(0) != 'B' || user.charAt(0) != 'R' || user.charAt(0) != 'D' || user.charAt(0) != 'T'){
				result = error;
			}
			return result;
		}
	}

}
