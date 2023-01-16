import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
//회원가입 클래스
class join_Screen extends JFrame implements ActionListener {
	//컨테이너 생성
	private Container ct = getContentPane();
	//Join클래스 생성
	private Join join = new Join();
	//라벨 설정
	private JLabel Title_Label = new JLabel("회 원 가 입");
	private JLabel ID_Label = new JLabel("아이디:");
	private JLabel NickName_Label = new JLabel("닉네임:");
	private JLabel PW_Label = new JLabel("비밀번호:");
	private JLabel CheckPW_Label = new JLabel("비밀번호 재확인:");
	private JLabel Birth_Label = new JLabel("이메일:");
	private JLabel jl = new JLabel("@");
//	JLabel jl6 = new JLabel("사용하실 닉네임을 입력해주세요.");
//	JLabel jl7 = new JLabel("비밀번호를 입력해주세요.");
//	JLabel jl8 = new JLabel("비밀번호를 다시 한번 입력해주세요.");
	//아이디, 닉네임 중복확인 버튼 누른 여부 확인
	private boolean name;
	private boolean id;
	//패널 생성 및 레이아웃 설정
	private JPanel Label_Panel = new JPanel(new BorderLayout());
	private JPanel Text_Panel = new JPanel(new GridLayout(5,1,0,15));
	private JPanel Input_Panel = new JPanel(new GridLayout(5,1,0,15));
	private JPanel Check_Panel = new JPanel(new GridLayout(5,1,0,15));
	private JPanel Button_Panel = new JPanel(new GridLayout(1,2,20,20));	
	private JPanel Email_Panel = new JPanel(new GridLayout(1,3,0,0));
	private JPanel Back_Panel = new JPanel();
	//텍스트필드 생성
	private JTextField ID_In = new JTextField();
	private JTextField NickName_In = new JTextField();
	private JTextField email = new JTextField(20);
	//패스워드 필드 생성
	private JPasswordField PW_In = new JPasswordField();
	private JPasswordField CheckPW_In = new JPasswordField();
	
	//콤보박스 생성
	private JComboBox com = new JComboBox();
	//버튼 생성
	private JButton ID_Overlap =  new JButton("중복확인");
	private JButton NickName_Overlap =  new JButton("중복확인");
	private JButton Back = new JButton("뒤로 가기");
	private JButton Check = new JButton("확인");
	private JButton New_In = new JButton("새로 입력");
	//생성자
	public join_Screen() {
		//컨테이너 레이아웃 설정
		ct.setLayout(new BorderLayout(20,20));
		//콤보박스 아이템 추가
		com.addItem("naver.com");
		com.addItem("nate.com");
		com.addItem("gmail.com");
		//타이틀 폰트 설정 및 가운데 맞춤
		Title_Label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Title_Label.setHorizontalAlignment(JLabel.CENTER);
		//뒤로가기 버튼 위치 조절
		Back.setHorizontalAlignment(JButton.NORTH_EAST);
		//패널 여백 조정
		Label_Panel.setBorder(BorderFactory.createEmptyBorder(10, 90, 20, 0));
		Text_Panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 10));
		Button_Panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20));
		Check_Panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 5));
		//패스워드 입력 시 암호화하기 위해 '*'로 설정
		CheckPW_In.setEchoChar('*');
		PW_In.setEchoChar('*');
		//Label패널에 추가
		Label_Panel.add(Title_Label, BorderLayout.CENTER);
		Label_Panel.add(Back, BorderLayout.EAST);
		//텍스트 패널에 라벨추가
		Text_Panel.add(ID_Label);
		Text_Panel.add(NickName_Label);
		Text_Panel.add(PW_Label);
		Text_Panel.add(CheckPW_Label);
		Text_Panel.add(Birth_Label);
		//Input 패널에 필드 추가
		Input_Panel.add(ID_In);
		Input_Panel.add(NickName_In);
		Input_Panel.add(PW_In);
		Input_Panel.add(CheckPW_In);
		//이메일 패널에 이메일관련 추가
		Email_Panel.add(email);
		Email_Panel.add(jl);
		Email_Panel.add(com);
		//Input패널에 이메일패널 추가
		Input_Panel.add(Email_Panel);
		//중복확인 패널에 추가
		Check_Panel.add(ID_Overlap);
		Check_Panel.add(NickName_Overlap);
		//버튼 패널에 추가
		Button_Panel.add(Check);
		Button_Panel.add(New_In);
		//이벤트 액션리스너
		ID_Overlap.addActionListener(this);
		NickName_Overlap.addActionListener(this);
		Check.addActionListener(this);
		New_In.addActionListener(this);
		Back.addActionListener(this);
		//컨테이너에 패널 추가 및 위치 지정
		ct.add(Label_Panel, BorderLayout.NORTH);
		ct.add(Text_Panel, BorderLayout.WEST);
		ct.add(Input_Panel, BorderLayout.CENTER);
		ct.add(Check_Panel, BorderLayout.EAST);
		ct.add(Button_Panel, BorderLayout.SOUTH);
		
		setTitle("회원가입 화면");
		setSize(500, 500);
		setVisible(true);
		setLocationRelativeTo(null);

	}
	//이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		int n;
		//이미 아이디나 닉네임이 있을경우에
		//showInputDialog() 이용해서 재입력받기
		Sudoku_DB sdb = new Sudoku_DB();
		//아이디 중복확인 버튼
		if (obj == ID_Overlap) {
			id = false;
			//DB에 입력받은 아이디가 중복되는지 안되는지 확인
			if(sdb.overlap(ID_In.getText(), "ID")) {
				
				//예스면 0, 노면 1 반환
				n = JOptionPane.showConfirmDialog(this, "사용 가능한 아이디입니다.\n사용하시겠습니까?", "아이디 중복여부", JOptionPane.YES_NO_OPTION);
				//예 눌렸을 경우
				if(n == 0) {
					id = true;
					join.setID(ID_In.getText());
				}
			}
			//DB에 저장된 아이디 중 중복됐을 경우
			else JOptionPane.showMessageDialog(this, "이미 사용중인 아이디입니다.!!");
			
		}
		//닉네임 중복확인 버튼
		else if (obj == NickName_Overlap) {
			//DB에 입력받은 닉네임이 중복되는지 안되는지 확인
			if(sdb.overlap(NickName_In.getText(), "name")) {
				name = false;
				//예스면 0, 노면 1 반환
				n = JOptionPane.showConfirmDialog(this, "사용 가능한 닉네임입니다.\n사용하시겠습니까?", "닉네임 중복여부", JOptionPane.YES_NO_OPTION);
				//예 눌렸을 경우
				if(n == 0) {
					join.setName(NickName_In.getText());
					name = true;
				}
			}
			//DB에 저장된 닉네임 중 중복됐을 경우
			else JOptionPane.showMessageDialog(this, "이미 사용중인 닉네임입니다.!!");
			
		}
		
		//확인 버튼
		else if (obj == Check) {
			//입력한 이메일 문자열 s에 저장
			String s = email.getText()+ jl.getText() + com.getSelectedItem();
			//이메일 중복여부 확인
			if(!sdb.overlap(s, "email")) {
				JOptionPane.showMessageDialog(this, "이미 사용중인 이메일입니다.!!");
			}
			//name과 id 중복확인을 했는지 체크, 빈공간 없는지 체크
			else if (name && id && !ID_In.getText().equals("") && !PW_In.getPassword().equals("") && !NickName_In.getText().equals("") && !email.getText().equals("")) {	
				//패스워드 필드 값 char형이기에 String으로 변환과정
				String pw = "";
				char [] c = PW_In.getPassword();
				for(int i = 0; i < c.length; i++)
					pw+=c[i];
				//join클래스에 set
				join.setPW(pw);
				join.setEmail(s);
				//DB에 insert 성공 시
				if(sdb.insertmember(join)) {
					JOptionPane.showMessageDialog(this, "회원가입에 성공하셨습니다.!!");
					dispose();
					new login_Screen();
				}
				//실패 시
				else JOptionPane.showMessageDialog(this, "회원가입에 실패하셨습니다.!!");
				
			}
			//아이디 입력 또는 비번 입력 또는 닉네임 입력을 안했을 경우
			else if(ID_In.getText().equals("") || PW_In.getPassword().equals("") || CheckPW_In.getPassword().equals("") || NickName_In.getText().equals("") || email.getText().equals(""))
				JOptionPane.showMessageDialog(this, "모든항목을 입력해주세요");
			//id중복확인 안했을경우
			else if(!id && name) JOptionPane.showMessageDialog(this, "아이디 중복확인을 해주세요");
			//닉네임 중복확인 안했을 경우
			else if(id && !name) JOptionPane.showMessageDialog(this, "닉네임 중복확인을 해주세요");
			else if(!id && !name) JOptionPane.showMessageDialog(this, "아이디와 닉네임 중복확인을 해주세요");
		}
		//새로 입력 버튼
		else if (obj == New_In) {
			ID_In.setText("");
			NickName_In.setText("");
			PW_In.setText("");
			CheckPW_In.setText("");
			email.setText("");
		}
		//뒤로가기 버튼 클릭 시
		else if (obj == Back) {
			dispose();
			new login_Screen();
		}
	}
}