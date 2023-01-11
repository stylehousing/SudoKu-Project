import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

class rank_Screen extends JFrame implements ActionListener {
	// Join클래스 객체생성
	Join join = new Join();
	// ArrayList생성
	ArrayList<Rank> datas = new ArrayList<Rank>();
	// 컨테이너 생성
	Container ct = getContentPane();
	// 초급중급상급 별 랭킹 보기 위해 Tab이용
	JTabbedPane jtp = new JTabbedPane();
	// 나가기 버튼
	JButton Exit = new JButton("나가기");

	public rank_Screen(Join join) {
		// join 받아오기
		this.join = join;
		// 컨테이너 레이아웃 설정
		ct.setLayout(new BorderLayout());
		// 버튼 이벤트처리
		Exit.addActionListener(this);
		// 탭에 난이도별 메소드 내용
		jtp.addTab("초급", new Easy());
		jtp.addTab("중급", new Normal());
		jtp.addTab("상급", new Hard());
		// 컨테이너에 추가
		ct.add(jtp, BorderLayout.NORTH);
		ct.add(Exit, BorderLayout.SOUTH);

		setTitle("랭킹 보기");
		setSize(300, 500);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// 이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		// 나가기 버튼일 경우
		if (e.getSource() == Exit) {
			// DB클래스 생성
			Sudoku_DB sdb = new Sudoku_DB();
			// 현재 창 닫기
			dispose();
			// datas는 실시간으로 game_Screen에서 기록을 보기위함
			datas = sdb.selectgameset(join.getID());
			// 난이도 선택 및 랭킹보기 화면으로
			new game_Screen(join, datas);
		}
	}

}

//초급난이도
class Easy extends JPanel {
	// TextArea 초기값 순위, 시간, 닉네임
	JTextArea jta = new JTextArea("순위\t시간\t닉네임\n");
	// DB 클래스 객체 생성
	Sudoku_DB sdb = new Sudoku_DB();

	public Easy() {
		// 레이아웃 설정
		setLayout(new BorderLayout());
		// area 폰트설정
		jta.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		// DB에서 시간별로 정렬 후 TextArea에 이어붙이기
		jta.append(sdb.DeptSortStudent("초급"));
		add(jta, BorderLayout.CENTER);

	}
}

//중급난이도
class Normal extends JPanel {
	// TextArea 초기값 순위, 시간, 닉네임
	JTextArea jta = new JTextArea("순위\t시간\t닉네임\n");
	// DB 클래스 객체 생성
	Sudoku_DB sdb = new Sudoku_DB();

	public Normal() {
		// 레이아웃 설정
		setLayout(new BorderLayout());
		// area 폰트설정
		jta.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		// DB에서 시간별로 정렬 후 TextArea에 이어붙이기
		jta.append(sdb.DeptSortStudent("중급"));
		add(jta, BorderLayout.CENTER);

	}
}

//상급난이도
class Hard extends JPanel {
	// TextArea 초기값 순위, 시간, 닉네임
	JTextArea jta = new JTextArea("순위\t시간\t닉네임\n");
	// DB 클래스 객체 생성
	Sudoku_DB sdb = new Sudoku_DB();

	public Hard() {
		// 레이아웃 설정
		setLayout(new BorderLayout());
		// area 폰트설정
		jta.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		// DB에서 시간별로 정렬 후 TextArea에 이어붙이기
		jta.append(sdb.DeptSortStudent("상급"));
		add(jta, BorderLayout.CENTER);
	}
}