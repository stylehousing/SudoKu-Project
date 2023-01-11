import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//아이디 비번 찾는 클래스
public class search extends JFrame {
	Join join = new Join();
	Container ct = getContentPane();
	//아이디 찾는 메소드
	public void idSearch() {
		//레이아웃 설정
		ct.setLayout(new GridLayout(3, 1));
		//라벨 설정
		JLabel Title_Label = new JLabel("아이디 찾기");
		JLabel Text_Label = new JLabel("이메일");
		JLabel jl = new JLabel("@");
		//텍스트 필드 설정
		JTextField email = new JTextField(10);
		JTextField ID_out = new JTextField();
		//콤보박스 생성
		JComboBox com = new JComboBox();
		//패널 생성
		JPanel set_Panel = new JPanel();
		//버튼 생성
		JButton get_ID = new JButton("확인");
		//콤보박스 아이템 추가
		com.addItem("naver.com");
		com.addItem("nate.com");
		com.addItem("gmail.com");
		//라벨 위치 폰트 설정
		Title_Label.setHorizontalAlignment(JLabel.CENTER);
		Title_Label.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		//패널에 추가
		set_Panel.add(Text_Label);
		set_Panel.add(email);
		set_Panel.add(jl);
		set_Panel.add(com);
		//이벤트 처리
		get_ID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//email 주소 String s에 저장
				String s = email.getText() + jl.getText() + com.getSelectedItem();
				//DB생성
				Sudoku_DB sdb = new Sudoku_DB();
				//DB에 이메일 값 보내서 아이디 찾기
				join = sdb.searchID(s);
				//join이 null이 아니면
				if (join != null)
					JOptionPane.showMessageDialog(null, "당신의 아이디는 " + join.getID() + "입니다.!!");
				else JOptionPane.showMessageDialog(null, "해당하는 이메일에 아이디가 존재하지않습니다.");
			}
		});
		//컨테이너에 추가
		ct.add(Title_Label);
		ct.add(set_Panel);
		ct.add(get_ID);
		
		setTitle("ID 찾기");
		setSize(300, 300);
		setVisible(true);
		// 창 듸울 때 가운데로 위치
		setLocationRelativeTo(null);
	}
	//비번찾는 메소드
	public void pwSearch() {
		//레이아웃 설정
		ct.setLayout(new GridLayout(4, 1));
		//라벨 생성
		JLabel Title_Label = new JLabel("비밀번호 찾기");
		JLabel Text_Label = new JLabel("이메일");
		JLabel ID_Label = new JLabel("아이디");
		JLabel jl = new JLabel("@");
		//텍스트필드 생성
		JTextField email = new JTextField(10);
		JTextField id = new JTextField(10);
		JTextField ID_out = new JTextField();
		//콤보박스 생성
		JComboBox com = new JComboBox();
		//패널 두개 생성
		JPanel ID_Panel = new JPanel();
		JPanel set_Panel = new JPanel();
		//버튼 생성
		JButton get_ID = new JButton("확인");
		//콤보박스 아이템 추가
		com.addItem("naver.com");
		com.addItem("nate.com");
		com.addItem("gmail.com");
		//타이틀 라벨 가운데 위치, 폰트 설정
		Title_Label.setHorizontalAlignment(JLabel.CENTER);
		Title_Label.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		//패널에 추가
		ID_Panel.add(ID_Label);
		ID_Panel.add(id);
		//패널에 추가
		set_Panel.add(Text_Label);
		set_Panel.add(email);
		set_Panel.add(jl);
		set_Panel.add(com);
		//이벤트 처리
		get_ID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//문자열 s에 email 주소 저장
				String s = email.getText() + jl.getText() + com.getSelectedItem();
				//DB 클래스 생성
				Sudoku_DB sdb = new Sudoku_DB();
				//DB에 searchPW메소드에 이메일주소와 입력한 id값 매개변수
				join = sdb.searchPW(s, id.getText());
				//DB에서 받아온 join클래스가 null이 아니면
				if (join != null) {
					int n = JOptionPane.showConfirmDialog(null, "당신의 비밀번호는 " + join.getPW() + "입니다.!!\n변경 하시겠습니까?", "비밀번호 변경여부", JOptionPane.YES_NO_OPTION);
					//yes 클릭 시 비밀번호 변경
					if(n==0) {
						JPanel panel = new JPanel();
						JPanel panel1 = new JPanel();
						JPanel panel2 = new JPanel(new GridLayout(2,2));
						JLabel label = new JLabel("비밀번호 입력:");
						JLabel label1 = new JLabel("비밀번호 확인:");
						JPasswordField pass = new JPasswordField(10);
						JPasswordField pass_Check = new JPasswordField(10);
						panel.add(label);
						panel.add(pass);
						panel1.add(label1);
						panel1.add(pass_Check);
						panel2.add(panel);
						panel2.add(panel1);
						n = JOptionPane.showConfirmDialog(null, panel2, "비밀번호 변경",JOptionPane.YES_NO_OPTION);
						String pw = "";
						String pw1 = "";
						char [] c = pass.getPassword();
						char [] c1 = pass_Check.getPassword();
						for(int i = 0; i < c.length; i++)
							pw+=c[i];
						for(int i = 0; i < c1.length; i++)
							pw1+=c1[i];
						//비밀번호 입력 후 확인클릭 시
						if(n==0 && !pw.equals("") && pw.equals(pw1) && !pw1.equals("")) {
							//DB에서 비밀번호 업데이트 메소드
							if(sdb.updatePW(join, pw)) {
								JOptionPane.showMessageDialog(null, "성공적으로 비밀번호변경이 완료되었습니다.!!");	
								//창닫기
								dispose();
							}
							else
								JOptionPane.showMessageDialog(null, "비밀번호 변경에 실패했습니다.!!");
							
						}
						else if(n==1) dispose();
						else JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.!!");
						
					}
					//no 클릭 시
					else {
						//창닫기
						dispose();
					}
				}
				//join이 null일 경우 (에러)
				else JOptionPane.showMessageDialog(null, "비밀번호를 찾을 수 없습니다.!!");	
			}
		});
		//컨테이너에 추가
		ct.add(Title_Label);
		ct.add(ID_Panel);
		ct.add(set_Panel);
		ct.add(get_ID);

		setTitle("PW 찾기");
		setSize(300, 300);
		setVisible(true);
		// 창 듸울 때 가운데로 위치
		setLocationRelativeTo(null);
	}

}
