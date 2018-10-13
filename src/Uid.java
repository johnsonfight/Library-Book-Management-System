import java.text.SimpleDateFormat;
import java.util.*;


public class Uid{

	
	String uid;			// �Ǹ�
	int bRight;			// �v��:0:�i�� 1:�O���w��,bRightFrom��i�� 2:�O�����٤��i��
	Date bRightFrom;	// �O���ٮѥi�ɤ��
	int bQty;			// �ثe�ɮѼ�:0~10(����)
	int bTimes;			// �`�ɮѼ�:>=0(�w��)
	Date rRightFrom;	// �i�w����� �w�]��1900/1/1
	int rQty;			// �w���Ѽ�:0~5
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	private static int peopleBorrowing = 0; // �ثe�`�ɮѤH��
	
	public static int getPeopleBorrowing() {
		return peopleBorrowing;
	}

	public static void setPeopleBorrowing(int n){
		peopleBorrowing += n;
	}
	public static void countPeopleBorrowing(Map<String,Uid> uid) {
		peopleBorrowing = 0;
		for (Map.Entry<String,Uid> entry : uid.entrySet()){
			Uid temp = entry.getValue();
			if (temp.bQty != 0){
				peopleBorrowing ++;
			}
		}
	}
	public Uid(){
		
	}
	public Uid(String user){
		uid = user;
		bRight = 1;
		bRightFrom = new Date(0);
		bQty = 0;
		bTimes = 0;
		rRightFrom = new Date(0);
		rQty = 0;
	}

	
	public String toString(){
		return "�Ǹ��G" + uid + " �v���G" + bRight
				+ "\r\n�ɴ��G" + sdf.format(bRightFrom) + " �ƶq�G" + bQty
				+ "\r\n���ơG" + bTimes + " �����G" + sdf.format(rRightFrom)
				+ "\r\n���ơG" + rQty;
	}

}
