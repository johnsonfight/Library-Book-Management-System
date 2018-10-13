import java.io.IOException;
import java.util.Map;

public class test {

	public static void main(String[] args) throws IOException {
		
		Data.initBook();
		Data.initUid();
		Data.initDept();
		Reserve rs = new Reserve();
		Borrow b = new Borrow();
		BookReturn br = new BookReturn();

		System.out.println(Data.getBook().get("37957002588620"));
		// 1st
		System.out.println("1"+rs.reserve("B01608172", "37957002588620", "2016/6/1", Data.getUid(), Data.getBook()));
		System.out.println(Data.getBook().get("37957002588620"));
		// 2nd
		System.out.println("2"+rs.reserve("B34502011", "37957002588620", "2016/6/1", Data.getUid(), Data.getBook()));
		System.out.println(Data.getBook().get("37957002588620"));
		// 1st取書
		System.out.println("3"+b.borrow("B01608172", 1, "37957002588620", "2016/6/2", Data.getUid(), Data.getBook()));
		System.out.println(Data.getBook().get("37957002588620"));
		// 3rd
		System.out.println("4"+rs.reserve("B34606172", "37957002588620", "2016/6/3", Data.getUid(), Data.getBook()));
		System.out.println(Data.getBook().get("37957002588620"));
		// 1st還書
		System.out.println("5"+br.bookReturn("B01608172", 1, "37957002588620", "2016/6/7", Data.getUid(), Data.getBook()));
		System.out.println(Data.getBook().get("37957002588620"));
		// 2nd取書
		System.out.println("6"+b.borrow("B34606172", 1, "37957002588620", "2016/6/7", Data.getUid(), Data.getBook()));
		System.out.println(Data.getBook().get("37957002588620"));
		// another book
		System.out.println("7"+rs.reserve("B01608172", "37957002611950", "2016/6/8", Data.getUid(), Data.getBook()));
		System.out.println(Data.getBook().get("37957002588620"));
		// should fail for not taking the first book
		System.out.println("8"+rs.reserve("B34502011", "37957002588620", "2016/6/8", Data.getUid(), Data.getBook()));
		System.out.println(Data.getBook().get("37957002588620"));
//		
//		Data.record(new History("B01608172",1,"1","37957002588620","2016/06/25"));
//		Data.record(new History("B01608172",1,"1","37957002611950","2016/06/26"));
//		Data.record(new History("B01608172",2,"2","37957002611950","2016/06/28"));
//		Data.record(new History("B01608172",2,"3","37957002588620","2016/06/29"));
//		Data.record(new History("B01608172",1,"1","37957002611737","2016/07/2"));
//		Data.record(new History("B01608172",1,"1","37957002588620","2016/07/5"));
//		Data.record(new History("B01608172",2,"4","37957002611737","2016/07/25"));
//		Data.record(new History("B01608172",2,"1","37957002588620","2016/07/29"));
//		Data.readLog("B01608172");
//		
//		Inquire i = new Inquire();
//		System.out.println(i.userLog("B01608172", Data.getUid(), Data.getBook()));
		
//		Register ru = new Register();
//		BookReturn br = new BookReturn();
//		System.out.println(ru.register("B01608172", "2016/6/3", Data.getUid(), Data.getDept()));
//		System.out.println(ru.register("R04522130", "2016/6/3", Data.getUid(), Data.getDept()));
//		System.out.println(ru.register("B34902010", "2016/6/3", Data.getUid(), Data.getDept()));
//		System.out.println(ru.register("D00522000", "2016/6/4", Data.getUid(), Data.getDept()));
//		System.out.println(ru.register("B02902299", "2016/6/4", Data.getUid(), Data.getDept()));
//		System.out.println(ru.register("R04628151", "2016/6/4", Data.getUid(), Data.getDept()));
//		System.out.println(b.borrow("R04522130", 1, "37957002588620", "2016/6/5", Data.getUid(), Data.getBook()));
//		System.out.println(ru.deRegister("R04522130", "2016/6/12", Data.getUid()));
//		System.out.println(ru.deRegister("B00000000", "2016/6/12", Data.getUid()));
//		System.out.println(ru.deRegister("R04628151", "2016/6/15", Data.getUid()));
//		System.out.println(ru.deRegister("D00522000", "2016/6/20", Data.getUid()));
//		System.out.println(br.bookReturn("R04522130", 1, "37957002588620", "2016/6/25", Data.getUid(), Data.getBook()));
//		System.out.println(ru.deRegister("R04522130", "2016/6/30", Data.getUid()));
	}

}
