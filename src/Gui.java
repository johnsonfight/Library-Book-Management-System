import java.awt.*;
import java.awt.event.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.GroupLayout.Alignment;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;

	Data data = new Data();

	private JPanel contentPane;
	private JPanel west = new JPanel();
	private JPanel center = new JPanel();
	private JLabel north;
	private JPanel east = new JPanel();
	private JLabel south = new JLabel();

	private JButton borrowBtn = new JButton("借書");
	private JButton returnBtn = new JButton("還書");
	private JButton renewBtn = new JButton("續借");
	private JButton reserveBtn = new JButton("預約");
	private JButton inquireBtn = new JButton("查   詢");
	private JButton transferBtn = new JButton("館際調閱");
	private JButton registerBtn = new JButton("註冊/註銷");

	String[] option = { "請選擇", "查詢書籍", "當前借出書數", "當前借書人數", "借書排名", "書籍排名", "分館排名", "停權查詢", "個人紀錄", "關鍵字查書" };
	private JComboBox inquireOption = new JComboBox(option);
	String[] branches = { "請選擇", "1", "2", "3", "4", "5" };
	private JComboBox branchNumB = new JComboBox(branches);
	private JComboBox branchNumBR = new JComboBox(branches);
	private JComboBox branchNumT = new JComboBox(branches);

	private JPanel blank = new JPanel();
	private JPanel borrow = new JPanel();
	private JPanel bookreturn = new JPanel();
	private JPanel renew = new JPanel();
	private JPanel reserve = new JPanel();
	private JPanel search = new JPanel();

	private JPanel search1 = new JPanel();
	private JPanel search2 = new JPanel();
	private JPanel search3 = new JPanel();
	private JPanel search4 = new JPanel();
	private JPanel search5 = new JPanel();
	private JPanel search6 = new JPanel();
	private JPanel search7 = new JPanel();
	private JPanel search8 = new JPanel();
	private JPanel transfer = new JPanel();
	private JPanel register = new JPanel();

	private JLabel infoI1 = new JLabel();
	private JLabel infoI2 = new JLabel();
	private JLabel infoB1 = new JLabel();
	private JLabel infoB2 = new JLabel();
	private JLabel infoB3 = new JLabel();
	private JLabel infoB4 = new JLabel();
	private JLabel infoBR1 = new JLabel();
	private JLabel infoBR2 = new JLabel();
	private JLabel infoBR3 = new JLabel();
	private JLabel infoBR4 = new JLabel();
	private JLabel infoR1 = new JLabel();
	private JLabel infoR2 = new JLabel();
	private JLabel infoR3 = new JLabel();
	private JLabel infoRS1 = new JLabel();
	private JLabel infoRS2 = new JLabel();
	private JLabel infoRS3 = new JLabel();
	private JLabel infoIBR = new JLabel();
	private JLabel infoILB = new JLabel();
	private JLabel infoIL = new JLabel();
	private JLabel infoT1 = new JLabel();
	private JLabel infoT2 = new JLabel();
	private JLabel infoT3 = new JLabel();
	private JLabel infoT4 = new JLabel();
	private JLabel infoReg1 = new JLabel();
	private JLabel infoReg2 = new JLabel();
	private JLabel keywordLbl = new JLabel();
	
	private JTextField inputNumI = new JTextField();
	private JTextField inputNumB1 = new JTextField();
	private JTextField inputNumB2 = new JTextField();
	private JTextField dateB = new JTextField();
	private JTextField inputNumBR1 = new JTextField();
	private JTextField inputNumBR2 = new JTextField();
	private JTextField dateBR = new JTextField();
	private JTextField inputNumR1 = new JTextField();
	private JTextField inputNumR2 = new JTextField();
	private JTextField dateR = new JTextField();
	private JTextField inputNumRS1 = new JTextField();
	private JTextField inputNumRS2 = new JTextField();
	private JTextField dateRS = new JTextField();
	private JTextField logUid = new JTextField();
	private JTextField inputNumT1 = new JTextField();
	private JTextField inputNumT2 = new JTextField();
	private JTextField dateT = new JTextField();
	private JTextField textFieldReg = new JTextField();
	private JTextField inputNumReg = new JTextField();
	private JTextField dateReg = new JTextField();
	private JTextField inputKeyword = new JTextField();

	private JButton doBtnI, doBtnB, doBtnBR, doBtnR, doBtnRS, doBtnL, doBtnT, doBtnIK, userBingRankBtn, userBedRankBtn, banBrwBtn,
			banRsvBtn, regBtn, deRegBtn;

	private JTextArea resultAreaI = new JTextArea();
	private JTextArea resultAreaB = new JTextArea();
	private JTextArea resultAreaBR = new JTextArea();
	private JTextArea resultAreaR = new JTextArea();
	private JTextArea resultAreaRS = new JTextArea();
	private JTextArea resultAreaIB = new JTextArea();
	private JTextArea resultAreaILB = new JTextArea();
	private JTextArea resultAreaLog = new JTextArea();
	private JTextArea resultAreaT = new JTextArea();
	private JTextArea resultAreaReg = new JTextArea();
	private JTextArea resultAreaIK = new JTextArea();

	private String[][] rankData = null;
	private String[] titleU1 = { "排名", "User ID", "當前借閱數量" };
	private String[] titleU2 = { "排名", "User ID", "累計借閱數量" };
	private String[] titleB = { "排名", "原館名", "書碼", "累計借出次數" };
	private String[] titleL = { "排名", "圖書館館名", "累計借出次數" };
	private JTable rankTblU;
	private JTable rankTblB;
	private JScrollPane sPU;
	private JScrollPane sPB;
	private JScrollPane SPIB;
	private JScrollPane sPL;
	private JScrollPane sPIK;

	CardLayout c1 = new CardLayout();
	DefaultTableModel m1 = new DefaultTableModel(rankData, null);
	DefaultTableModel m2 = new DefaultTableModel(rankData, null);
	DefaultTableModel m3 = new DefaultTableModel(rankData, null);
	Font f20 = new Font("標楷體", Font.PLAIN, 20);
	Font f18 = new Font("標楷體", Font.PLAIN, 18);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	Borrow brw = new Borrow();
	BookReturn rtn = new BookReturn();
	Inquire inq = new Inquire();
	Renew rnw = new Renew();
	Reserve rs = new Reserve();
	Transfer tf = new Transfer();
	Register rg = new Register();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		// initialize the data
		Data.initBook();
		Data.initUid();
		Data.initDept();

		setTitle("圖書館系統");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// west
		GridBagLayout gbl_west = new GridBagLayout();
		gbl_west.columnWidths = new int[] { 0, 0 };
		gbl_west.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30 };
		gbl_west.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_west.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		west.setLayout(gbl_west);

		borrowBtn.setFont(f20);
		GridBagConstraints gbc_borrowBtn = new GridBagConstraints();
		gbc_borrowBtn.insets = new Insets(0, 0, 5, 0);
		gbc_borrowBtn.gridx = 0;
		gbc_borrowBtn.gridy = 0;
		west.add(borrowBtn, gbc_borrowBtn);

		returnBtn.setFont(f20);
		GridBagConstraints gbc_returnBtn = new GridBagConstraints();
		gbc_returnBtn.insets = new Insets(0, 0, 5, 0);
		gbc_returnBtn.gridx = 0;
		gbc_returnBtn.gridy = 2;
		west.add(returnBtn, gbc_returnBtn);

		renewBtn.setFont(f20);
		GridBagConstraints gbc_renewBtn = new GridBagConstraints();
		gbc_renewBtn.insets = new Insets(0, 0, 5, 0);
		gbc_renewBtn.gridx = 0;
		gbc_renewBtn.gridy = 4;
		west.add(renewBtn, gbc_renewBtn);

		reserveBtn.setFont(f20);
		GridBagConstraints gbc_reserveBtn = new GridBagConstraints();
		gbc_reserveBtn.insets = new Insets(0, 0, 5, 0);
		gbc_reserveBtn.gridx = 0;
		gbc_reserveBtn.gridy = 6;
		west.add(reserveBtn, gbc_reserveBtn);

		// east
		GridBagLayout gbl_east = new GridBagLayout();
		gbl_east.columnWidths = new int[] { 0, 0 };
		gbl_east.rowHeights = new int[] { 0, 30, 30, 30, 30, 30 };
		gbl_east.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_east.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		east.setLayout(gbl_east);
		
		inquireBtn.setFont(f20);
		GridBagConstraints gbc_inquireBtn = new GridBagConstraints();
		gbc_inquireBtn.insets = new Insets(0, 0, 5, 0);
		gbc_inquireBtn.gridx = 0;
		gbc_inquireBtn.gridy = 0;
		gbc_inquireBtn.fill  = GridBagConstraints.BOTH;
		east.add(inquireBtn, gbc_inquireBtn);
		
		transferBtn.setFont(f20);
		GridBagConstraints gbc_transferBtn = new GridBagConstraints();
		gbc_transferBtn.insets = new Insets(0, 0, 5, 0);
		gbc_transferBtn.gridx = 0;
		gbc_transferBtn.gridy = 2;
		gbc_transferBtn.fill  = GridBagConstraints.BOTH;
		east.add(transferBtn, gbc_transferBtn);
		
		registerBtn.setFont(f20);
		GridBagConstraints gbc_registerBtn = new GridBagConstraints();
		gbc_registerBtn.insets = new Insets(0, 0, 5, 0);
		gbc_registerBtn.gridx = 0;
		gbc_registerBtn.gridy = 4;
		gbc_registerBtn.fill  = GridBagConstraints.BOTH;
		east.add(registerBtn, gbc_registerBtn);

		// center
		center.setLayout(c1);

		inquireOption.setBounds(210, 170, 150, 30);
		inquireOption.setSelectedIndex(0);

		search.add(inquireOption);
		search.setLayout(null);

		c1.show(center, "0");

		// borrow : infoB1, infoB2, infoB3, inputNumB1, inputNumB2, date,
		// doBtnB, resultAreaB
		borrow.setLayout(null);

		infoB1.setText("請輸入用戶ID：");
		infoB1.setBounds(60, 130, 130, 25);
		infoB1.setEnabled(true);
		infoB1.setFont(f18);
		borrow.add(infoB1);

		infoB2.setText("請選擇分館：");
		infoB2.setFont(f18);
		infoB2.setBounds(60, 180, 150, 25);

		borrow.add(infoB2);

		infoB3.setText("請輸入日期(yyyy/mm/dd)：");
		infoB3.setFont(f18);
		infoB3.setBounds(60, 280, 229, 25);
		borrow.add(infoB3);

		infoB4.setText("請輸入書碼：");
		infoB4.setBounds(60, 230, 130, 25);
		infoB4.setEnabled(true);
		infoB4.setFont(f18);
		borrow.add(infoB4);

		inputNumB1.setBounds(280, 130, 150, 25);
		inputNumB1.setEnabled(true);
		inputNumB1.setColumns(10);
		borrow.add(inputNumB1);

		branchNumB.setFont(f18);
		branchNumB.setBounds(280, 180, 88, 25);
		borrow.add(branchNumB);

		dateB.setBounds(280, 280, 150, 25);
		dateB.setColumns(10);
		borrow.add(dateB);

		inputNumB2.setBounds(280, 230, 150, 25);
		inputNumB2.setEnabled(true);
		inputNumB2.setColumns(10);
		borrow.add(inputNumB2);

		resultAreaB.setEditable(false);
		resultAreaB.setBounds(100, 350, 300, 75);
		borrow.add(resultAreaB);

		doBtnB = new JButton("\u78BA\u8A8D");
		doBtnB.setBounds(455, 280, 75, 25);
		doBtnB.setFont(f20);
		borrow.add(doBtnB);

		// bookreturn
		bookreturn.setLayout(null);

		infoBR1.setText("請輸入用戶ID：");
		infoBR1.setBounds(60, 130, 130, 25);
		infoBR1.setEnabled(true);
		infoBR1.setFont(f18);
		bookreturn.add(infoBR1);

		infoBR2.setText("請選擇分館：");
		infoBR2.setFont(f18);
		infoBR2.setBounds(60, 180, 150, 25);
		bookreturn.add(infoBR2);

		infoBR3.setText("請輸入日期(yyyy/mm/dd)：");
		infoBR3.setFont(f18);
		infoBR3.setBounds(60, 280, 229, 25);
		bookreturn.add(infoBR3);

		infoBR4.setText("請輸入書碼：");
		infoBR4.setBounds(60, 230, 130, 25);
		infoBR4.setEnabled(true);
		infoBR4.setFont(f18);
		bookreturn.add(infoBR4);

		inputNumBR1.setBounds(280, 130, 150, 25);
		inputNumBR1.setEnabled(true);
		inputNumBR1.setColumns(10);
		bookreturn.add(inputNumBR1);

		branchNumBR.setFont(f18);
		branchNumBR.setBounds(280, 180, 88, 25);
		bookreturn.add(branchNumBR);

		dateBR.setBounds(280, 280, 150, 25);
		dateBR.setColumns(10);
		bookreturn.add(dateBR);

		inputNumBR2.setBounds(280, 230, 150, 25);
		inputNumBR2.setEnabled(true);
		inputNumBR2.setColumns(10);
		bookreturn.add(inputNumBR2);

		resultAreaBR.setEditable(false);
		resultAreaBR.setBounds(100, 350, 300, 75);
		bookreturn.add(resultAreaBR);

		doBtnBR = new JButton("確認");
		doBtnBR.setBounds(455, 280, 75, 25);
		doBtnBR.setFont(f20);
		bookreturn.add(doBtnBR);

		// renew
		renew.setLayout(null);

		infoR1.setText("請輸入用戶ID：");
		infoR1.setBounds(60, 130, 130, 25);
		infoR1.setEnabled(true);
		infoR1.setFont(f18);
		renew.add(infoR1);

		infoR2.setText("請輸入書碼：");
		infoR2.setFont(f18);
		infoR2.setBounds(60, 200, 150, 25);

		renew.add(infoR2);

		infoR3.setText("請輸入日期(yyyy/mm/dd)：");
		infoR3.setFont(f18);
		infoR3.setBounds(60, 270, 229, 25);
		renew.add(infoR3);

		inputNumR1.setBounds(280, 130, 150, 25);
		inputNumR1.setEnabled(true);
		inputNumR1.setColumns(10);
		renew.add(inputNumR1);

		dateR.setBounds(280, 270, 150, 25);
		dateR.setColumns(10);
		renew.add(dateR);

		inputNumR2.setBounds(280, 200, 150, 25);
		inputNumR2.setEnabled(true);
		inputNumR2.setColumns(10);
		renew.add(inputNumR2);

		resultAreaR.setEditable(false);
		resultAreaR.setBounds(100, 350, 300, 75);
		renew.add(resultAreaR);

		doBtnR = new JButton("確認");
		doBtnR.setBounds(455, 270, 75, 25);
		doBtnR.setFont(f20);
		renew.add(doBtnR);

		// reserve
		reserve.setLayout(null);

		infoRS1.setText("請輸入用戶ID：");
		infoRS1.setBounds(60, 130, 130, 25);
		infoRS1.setEnabled(true);
		infoRS1.setFont(f18);
		reserve.add(infoRS1);

		infoRS2.setText("請輸入書碼：");
		infoRS2.setFont(f18);
		infoRS2.setBounds(60, 200, 150, 25);
		reserve.add(infoRS2);

		infoRS3.setText("請輸入日期(yyyy/mm/dd)：");
		infoRS3.setFont(f18);
		infoRS3.setBounds(60, 270, 229, 25);
		reserve.add(infoRS3);

		inputNumRS1.setBounds(280, 130, 150, 25);
		inputNumRS1.setEnabled(true);
		inputNumRS1.setColumns(10);
		reserve.add(inputNumRS1);

		dateRS.setBounds(280, 270, 150, 25);
		dateRS.setColumns(10);
		reserve.add(dateRS);

		inputNumRS2.setBounds(280, 200, 150, 25);
		inputNumRS2.setEnabled(true);
		inputNumRS2.setColumns(10);
		reserve.add(inputNumRS2);

		resultAreaRS.setEditable(false);
		resultAreaRS.setBounds(100, 350, 300, 75);
		reserve.add(resultAreaRS);

		doBtnRS = new JButton("確認");
		doBtnRS.setBounds(455, 270, 75, 25);
		doBtnRS.setFont(f20);
		reserve.add(doBtnRS);

		// search1
		search1.setLayout(null);

		infoI1.setText("請輸入書號：");
		infoI1.setBounds(60, 160, 108, 25);
		infoI1.setEnabled(true);
		infoI1.setFont(f18);
		search1.add(infoI1);

		inputNumI.setBounds(200, 160, 150, 25);
		inputNumI.setEnabled(true);
		inputNumI.setColumns(10);
		search1.add(inputNumI);

		doBtnI = new JButton("查詢");
		doBtnI.setBounds(400, 160, 75, 25);
		doBtnI.setFont(f20);
		search1.add(doBtnI);

		resultAreaI.setEditable(false);
		resultAreaI.setBounds(122, 240, 310, 150);
		search1.add(resultAreaI);

		// search2
		search2.setLayout(null);

		infoI2.setBounds(150, 245, 270, 25);
		infoI2.setEnabled(true);
		infoI2.setFont(f18);
		search2.add(infoI2);

		// search3
		search3.setLayout(null);

		userBingRankBtn = new JButton("\u7576\u524D\u501F\u95B1\u6392\u540D");
		userBingRankBtn.setBounds(110, 135, 150, 30);
		search3.add(userBingRankBtn);

		userBedRankBtn = new JButton("\u7D2F\u8A08\u501F\u95B1\u6392\u540D");
		userBedRankBtn.setBounds(310, 135, 150, 30);
		search3.add(userBedRankBtn);

		rankTblU = new JTable();
		rankTblU.setModel(m1);
		rankTblU.setLocation(60, 120);
		rankTblU.setSize(400, 200);
		sPU = new JScrollPane(rankTblU);
		sPU.setSize(400, 200);
		sPU.setLocation(90, 230);
		search3.add(sPU);

		// search4
		search4.setLayout(null);
		infoIBR.setFont(f18);
		infoIBR.setHorizontalAlignment(SwingConstants.CENTER);
		infoIBR.setText("Top10書籍");
		infoIBR.setBounds(235, 140, 100, 30);
		search4.add(infoIBR);

		rankTblB = new JTable();
		rankTblB.setModel(m2);
		rankTblB.setLocation(60, 120);
		rankTblB.setSize(400, 200);
		sPB = new JScrollPane(rankTblB);
		sPB.setSize(400, 200);
		sPB.setLocation(85, 220);
		search4.add(sPB);

		// search5
		search5.setLayout(null);
		banBrwBtn = new JButton("停止借書權");
		banBrwBtn.setBounds(110, 135, 150, 30);
		search5.add(banBrwBtn);

		banRsvBtn = new JButton("停止預約權");
		banRsvBtn.setBounds(310, 135, 150, 30);
		search5.add(banRsvBtn);

		resultAreaIB.setEditable(false);
		resultAreaIB.setAutoscrolls(true);
		SPIB = new JScrollPane(resultAreaIB, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		SPIB.setSize(400, 200);
		SPIB.setLocation(90, 230);
		search5.add(SPIB);

		// search6
		search6.setLayout(null);

		infoILB.setFont(f18);
		infoILB.setHorizontalAlignment(SwingConstants.CENTER);
		infoILB.setText("分館借出數量排名");
		infoILB.setBounds(185, 160, 200, 30);
		search6.add(infoILB);

		resultAreaILB.setBounds(135, 245, 300, 150);
		resultAreaILB.setEditable(false);

		search6.add(resultAreaILB);

		// search7
		search7.setLayout(null);

		infoIL.setText("請輸入用戶ID：");
		infoIL.setBounds(80, 140, 130, 25);
		infoIL.setEnabled(true);
		infoIL.setFont(f18);
		search7.add(infoIL);
		
		logUid.setBounds(220, 140, 150, 25);
		logUid.setEnabled(true);
		logUid.setColumns(10);
		search7.add(logUid);

		doBtnL = new JButton("查詢");
		doBtnL.setBounds(420, 140, 75, 25);
		doBtnL.setFont(f20);
		search7.add(doBtnL);

		resultAreaLog.setBounds(135, 165, 250, 150);
		resultAreaLog.setEditable(false);
		resultAreaLog.setAutoscrolls(true);
		
		sPL = new JScrollPane(resultAreaLog, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sPL.setSize(480, 200);
		sPL.setLocation(50, 210);
		search7.add(sPL);
		
		// search8
		search8.setLayout(null);
		
		keywordLbl.setText("請輸入關鍵字：");
		keywordLbl.setBounds(50, 135, 150, 25);
		keywordLbl.setFont(f18);
		search8.add(keywordLbl);

		inputKeyword.setBounds(225, 135, 150, 25);
		inputKeyword.setColumns(10);
		search8.add(inputKeyword);
		
		doBtnIK = new JButton("查詢");
		doBtnIK.setBounds(420, 135, 100, 30);
		search8.add(doBtnIK);
		
		resultAreaIK.setBounds(50, 230, 470, 200);
		resultAreaIK.setEditable(false);
		resultAreaIK.setAutoscrolls(true);
		
		sPIK = new JScrollPane(resultAreaIK, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sPIK.setSize(480, 200);
		sPIK.setLocation(50, 230);		
		
		search8.add(sPIK);
		
		// transfer
		transfer.setLayout(null);

		infoT1.setText("請輸入用戶ID：");
		infoT1.setBounds(60, 130, 130, 25);
		infoT1.setEnabled(true);
		infoT1.setFont(f18);
		transfer.add(infoT1);

		infoT2.setText("請選擇分館：");
		infoT2.setFont(f18);
		infoT2.setBounds(60, 180, 150, 25);
		transfer.add(infoT2);

		infoT3.setText("請輸入日期(yyyy/mm/dd)：");
		infoT3.setFont(f18);
		infoT3.setBounds(60, 280, 229, 25);
		transfer.add(infoT3);

		infoT4.setText("請輸入書碼：");
		infoT4.setBounds(60, 230, 130, 25);
		infoT4.setEnabled(true);
		infoT4.setFont(f18);
		transfer.add(infoT4);

		inputNumT1.setBounds(280, 130, 150, 25);
		inputNumT1.setEnabled(true);
		inputNumT1.setColumns(10);
		transfer.add(inputNumT1);

		branchNumT.setFont(f18);
		branchNumT.setBounds(280, 180, 88, 25);
		transfer.add(branchNumT);

		dateT.setBounds(280, 280, 150, 25);
		dateT.setColumns(10);
		transfer.add(dateT);

		inputNumT2.setBounds(280, 230, 150, 25);
		inputNumT2.setEnabled(true);
		inputNumT2.setColumns(10);
		transfer.add(inputNumT2);

		resultAreaT.setEditable(false);
		resultAreaT.setBounds(100, 350, 300, 75);
		transfer.add(resultAreaT);

		doBtnT = new JButton("確認");
		doBtnT.setBounds(455, 280, 75, 25);
		doBtnT.setFont(f20);
		transfer.add(doBtnT);

		// register
		register.setLayout(null);
		infoReg1.setText("請輸入用戶ID：");
		infoReg1.setBounds(115, 130, 130, 25);
		infoReg1.setEnabled(true);
		infoReg1.setFont(f18);
		register.add(infoReg1);
		
		inputNumReg = new JTextField();
		inputNumReg.setBounds(340, 130, 130, 25);
		inputNumReg.setColumns(10);
		register.add(inputNumReg);

		infoReg2.setText("請輸入日期(yyyy/mm/dd)：");
		infoReg2.setFont(f18);
		infoReg2.setEnabled(true);
		infoReg2.setBounds(115, 190, 230, 25);
		register.add(infoReg2);
		
		regBtn = new JButton("註冊");
		regBtn.setBounds(150, 250, 100, 30);
		regBtn.setFont(f20);
		register.add(regBtn);
		
		deRegBtn = new JButton("註銷");
		deRegBtn.setBounds(330, 250, 100, 30);
		deRegBtn.setFont(f20);
		register.add(deRegBtn);

		resultAreaReg.setEditable(false);
		resultAreaReg.setBounds(110, 310, 360, 100);
		register.add(resultAreaReg);
		
		dateReg.setColumns(10);
		dateReg.setBounds(340, 190, 130, 25);
		register.add(dateReg);
		
		
		// add to center card
		center.add(blank, "0");
		center.add(borrow, "1");
		center.add(bookreturn, "2");
		center.add(renew, "3");
		center.add(reserve, "4");
		center.add(search, "5");
		center.add(search1, "6");
		center.add(search2, "7");
		center.add(search3, "8");
		center.add(search4, "9");
		center.add(search5, "10");
		center.add(search6, "11");
		center.add(search7, "12");
		center.add(search8, "13");
		center.add(transfer, "14");
		center.add(register, "15");
		


		// north
		north = new JLabel("\u5716\u66F8\u9928\u501F\u9084\u66F8\u7CFB\u7D71");
		north.setFont(new Font("標楷體", Font.PLAIN, 36));
		north.setHorizontalAlignment(SwingConstants.CENTER);

		contentPane.add(west, BorderLayout.WEST);
		contentPane.add(center, BorderLayout.CENTER);
		contentPane.add(north, BorderLayout.NORTH);
		contentPane.add(east, BorderLayout.EAST);		
		contentPane.add(south, BorderLayout.SOUTH);

		// action
		borrowBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				north.setText("借書");
				resultAreaB.setText("");
				inputNumB1.setText("");
				branchNumB.setSelectedIndex(0);
				dateB.setText("");
				inputNumB2.setText("");
				repaint();
				c1.show(center, "1");
			}
		});
		doBtnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaB.setText("");
				repaint();
				String result = brw.borrow(inputNumB1.getText(), branchNumB.getSelectedIndex(), inputNumB2.getText(),
						dateB.getText(), Data.getUid(), Data.getBook());
				resultAreaB.setText(result);
				repaint();
			}
		});
		returnBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				north.setText("還書");
				resultAreaBR.setText("");
				inputNumBR1.setText("");
				branchNumBR.setSelectedIndex(0);
				dateBR.setText("");
				inputNumBR2.setText("");
				repaint();
				c1.show(center, "2");
			}
		});
		inquireBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				north.setText("查詢");
				inquireOption.setSelectedIndex(0);
				c1.show(center, "5");
			}
		});
		doBtnBR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaBR.setText("");
				repaint();
				String result = rtn.bookReturn(inputNumBR1.getText(), branchNumBR.getSelectedIndex(),
						inputNumBR2.getText(), dateBR.getText(), Data.getUid(), Data.getBook());
				resultAreaBR.setText(result);
				repaint();
			}
		});
		renewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				north.setText("續借");
				resultAreaR.setText("");
				inputNumR1.setText("");
				dateR.setText("");
				inputNumR2.setText("");
				repaint();
				c1.show(center, "3");
			}
		});
		doBtnR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaR.setText("");
				repaint();
				String result = rnw.renew(inputNumR1.getText(), inputNumR2.getText(), dateR.getText(), Data.getUid(),
						Data.getBook());
				resultAreaR.setText(result);
				repaint();
			}
		});
		reserveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				north.setText("預約");
				resultAreaRS.setText("");
				inputNumRS1.setText("");
				dateRS.setText("");
				inputNumRS2.setText("");
				repaint();
				c1.show(center, "4");
			}
		});
		doBtnRS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaRS.setText("");
				repaint();
				String result = rs.reserve(inputNumRS1.getText(), inputNumRS2.getText(), dateRS.getText(),
						Data.getUid(), Data.getBook());
				resultAreaRS.setText(result);
				repaint();
			}
		});
		inquireOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == inquireOption) {
					if (inquireOption.getSelectedItem().equals("查詢書籍")) {
						c1.show(center, "6");
						inputNumI.setText("");
						resultAreaI.setText("");
						repaint();
					} else if (inquireOption.getSelectedItem().equals("當前借出書數")) {
						infoI2.setText("目前總借出書籍數：" + inq.bookBorrowed());
						c1.show(center, "7");
					} else if (inquireOption.getSelectedItem().equals("當前借書人數")) {
						infoI2.setText("目前總借書人數：" + inq.peopleBorrowing());
						c1.show(center, "7");
					} else if (inquireOption.getSelectedItem().equals("借書排名")) {
						for (int i = m1.getRowCount() - 1; i >= 0; i--) {
							m1.removeRow(i);
						}
						c1.show(center, "8");
					} else if (inquireOption.getSelectedItem().equals("書籍排名")) {
						c1.show(center, "9");
						m2.setColumnIdentifiers(titleB);
						for (int i = m2.getRowCount() - 1; i >= 0; i--) {
							m2.removeRow(i);
						}
						ArrayList<Rank> rank = inq.top10BookBed(Data.getBook());
						for (int i = 0; i < rank.size(); i++) {
							m2.addRow(new Object[] { rank.get(i).rank, rank.get(i).somthing, rank.get(i).name,
									rank.get(i).value });
						}
						rankTblB.getColumnModel().getColumn(0).setPreferredWidth(70);
						rankTblB.getColumnModel().getColumn(1).setPreferredWidth(70);
						rankTblB.getColumnModel().getColumn(2).setPreferredWidth(130);
						rankTblB.getColumnModel().getColumn(3).setPreferredWidth(130);
						rankTblB.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
					} else if (inquireOption.getSelectedItem().equals("停權查詢")) {
						resultAreaIB.setText("");
						c1.show(center, "10");
					} else if (inquireOption.getSelectedItem().equals("分館排名")) {
						resultAreaILB.setText(inq.brcBedBook());
						repaint();
						c1.show(center, "11");
					} else if (inquireOption.getSelectedItem().equals("個人紀錄")) {
						resultAreaLog.setText("");
						logUid.setText("");
						c1.show(center, "12");
					} else if (inquireOption.getSelectedItem().equals("關鍵字查書")) {
						c1.show(center, "13");
					}
				}
			}
		});
		doBtnI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int branch = 0;
				String serialNum = inputNumI.getText();
				resultAreaI.setText(inq.search(branch, serialNum, Data.getBook()));
				repaint();
			}
		});
		userBingRankBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m1.setColumnIdentifiers(titleU1);
				for (int i = m1.getRowCount() - 1; i >= 0; i--) {
					m1.removeRow(i);
				}
				ArrayList<Rank> rank = inq.top10Bing(Data.getUid());
				System.out.println(rank.size());
				for (int i = 0; i < rank.size(); i++) {
					m1.addRow(new Object[] { rank.get(i).rank, rank.get(i).name, rank.get(i).value });
				}
			}
		});
		userBedRankBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m1.setColumnIdentifiers(titleU2);
				for (int i = m1.getRowCount() - 1; i >= 0; i--) {
					m1.removeRow(i);
				}
				ArrayList<Rank> rank = inq.top10HasBed(Data.getUid());
				for (int i = 0; i < rank.size(); i++) {
					m1.addRow(new Object[] { rank.get(i).rank, rank.get(i).name, rank.get(i).value });
				}
			}
		});
		banBrwBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaIB.setText(inq.banBList(Data.getUid()));
				repaint();
			}
		});
		banRsvBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaIB.setText(inq.banRList(Data.getUid()));
				repaint();
			}
		});
		doBtnL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = inq.userLog(logUid.getText(), Data.getUid(), Data.getBook());
				resultAreaLog.setText(result);
				repaint();
			}
		});
		doBtnIK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaIK.setText("");
				repaint();
				String result = inq.searchName(inputKeyword.getText(), Data.getBook());
				resultAreaIK.setText(result);
				repaint();
			}
		});
		transferBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				north.setText("館際調閱");
				resultAreaT.setText("");
				inputNumT1.setText("");
				branchNumT.setSelectedIndex(0);
				dateT.setText("");
				inputNumT2.setText("");
				repaint();
				c1.show(center, "14");
			}
		});
		doBtnT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaT.setText("");
				repaint();
				String result = tf.transfer(inputNumT1.getText(), branchNumT.getSelectedIndex(), inputNumT2.getText(),
						dateT.getText(), Data.getUid(), Data.getBook());
				resultAreaT.setText(result);
				repaint();
			}
		});
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				north.setText("註冊/註銷");
				resultAreaReg.setText("");
				inputNumReg.setText("");
				dateReg.setText("");
				repaint();
				c1.show(center, "15");
			}
		});
		regBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaReg.setText("");
				repaint();
				String result = rg.register(inputNumReg.getText(), dateReg.getText(), Data.getUid(), Data.getDept());
				resultAreaReg.setText(result);
				repaint();
			}
		});
		deRegBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultAreaReg.setText("");
				repaint();
				String result = rg.deRegister(inputNumReg.getText(), dateReg.getText(), Data.getUid());
				resultAreaReg.setText(result);
				repaint();
			}
		});
		
	}
}