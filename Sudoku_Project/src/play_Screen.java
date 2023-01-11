import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class play_Screen extends JFrame implements ActionListener {
	// 컨테이너 생성
	private Container ct = getContentPane();
	//패널 생성 및 레이아웃 설정
	private JPanel Button_Panel = new JPanel(new GridLayout(9, 9));
	private JPanel Click_Panel = new JPanel(new GridLayout(11, 1));
	private JPanel Time_Panel = new JPanel(new GridLayout(3, 2));
	// 스도쿠 판 9*9
	private JButton[][] button;
	// 정답 받아올 2차원배열 버튼
	private JButton[][] button1;
	// 답을 입력 할 버튼
	private JButton[] click;
	// 게임시작 버튼
	private JButton startButton = new JButton("게임시작");
	// 이용자 게임정보 저장을 위한 ArrayList 생성
	private ArrayList<Rank> datas = new ArrayList<Rank>();
	// 어떤 버튼을 클릭해서 어디로 넣을건지에 대한 변수
	private static String value;
	// 틀린 횟수
	private int sum = 0;
	// 게임시작버튼을 눌렸는지 아닌지
	private boolean started = false;
	// sudoku클래스로 보낼 매개변수 N은 행과 열의 수, K는 빈 공간의 수
	private int N, K;
	// 라벨 생성
	private JLabel timeLabel = new JLabel();
	// 시간
	private int elapsedTime = 0;
	private int seconds = 0;
	private int minutes = 0;
	private int hours = 0;
	//처음 빈 공간의 개수를 주고 맞출 때마다 감소시켜 정답으로 다 채워졌을 경우 게임종료 하기위한 조건 변수
	private int aa = 0;
	// Join, Rank 클래스 생성
	private Join join = new Join();
	private Rank rank = new Rank();
	private Sudoku sudoku;
	// 형변환int형을 String형으로 포맷
	private String seconds_string = String.format("%02d", seconds);
	private String minutes_string = String.format("%02d", minutes);
	private String hours_string = String.format("%02d", hours);
	// DB클래스 생성
	private Sudoku_DB sdb = new Sudoku_DB();
	// 타이머 이벤트처리
	private Timer timer = new Timer(1000, new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			elapsedTime = elapsedTime + 1000;
			// 시간계산
			hours = (elapsedTime / 3600000);
			// 분 계산
			minutes = (elapsedTime / 60000) % 60;
			// 초 계산
			seconds = (elapsedTime / 1000) % 60;
			// 문자열로 형변환
			seconds_string = String.format("%02d", seconds);
			minutes_string = String.format("%02d", minutes);
			hours_string = String.format("%02d", hours);
			// 라벨에 텍스트set
			timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);

		}

	});

	// 생성자
	public play_Screen(String s, Join join) {
		this.join = join;
		//컨테이너 레이아웃 설정
		ct.setLayout(new BorderLayout(20, 20));
		//배열 크기설정
		button = new JButton[9][9];
		click = new JButton[11];

		//게임 난이도 선택한 문자열을 받아와서 난이도별 K값 변경
		if (s.equals("초급")) {
			N = 9;
			K = 10;
		} else if (s.equals("중급")) {
			N = 9;
			K = 20;
		} else if (s.equals("상급")) {
			N = 9;
			K = 30;
		}
		//Sudoku프로그램 객체생성 매개변수 N,K
		sudoku = new Sudoku(N, K);
		//Sudoku클래스에 fillValuees(스도쿠 생성기) 호출
		sudoku.fillValues();
		//정답 받아오기 2차원배열
		int array[][] = sudoku.pirntSudoku();
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//2차원 배열 button을 우선 공백으로 생성.
				button[i][j] = new JButton("");
				//패널에 버튼 추가
				Button_Panel.add(button[i][j]);
				button[i][j].setEnabled(false);
				//버튼 이벤트 처리
				button[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//JButton형태로
						JButton jb = (JButton) e.getSource();
						//만약 숫자가 아닌 X버튼이나 ?버튼 클릭 후 버튼 클릭시
						if (value.equals("") || value.equals("?")) {
							//배경색 흰색
							jb.setBackground(Color.WHITE);
							//value값 지정
							jb.setText(value);
						}
						//숫자버튼 클릭 후 버튼 클릭 시
						else {
							//클릭 한 곳의 인덱스를 알기 위해 2차원 배열로 찾기
							for (int i = 0; i < 9; i++) {
								for (int j = 0; j < 9; j++) {
									if ((i * 54) == jb.getY() && (j * 65 + 7) == jb.getX()) {
										//정답이 저장되어 있는 array 2차원배열과 value값이 다르다면 정답 X
										if (array[i][j] != Integer.parseInt(value)) {
											//배경색 빨간색으로 설정
											jb.setBackground(Color.red);
											//틀린횟수 +1
											sum++;
										}
										//정답일 경우
										else {
											//배경색 흰색
											jb.setBackground(Color.WHITE);
											//정답이므로 편집불가 설정
											jb.setEnabled(false);
											//맞췄으므로 aa -1
											aa--;
										}
										//조건문 이후 value값을 버튼에 set
										jb.setText(value);

									}
								}
							}
						}
						//틀린횟수가 3회 초과일 경우 게임종료
						if (sum > 3)
							gameExit();
						//aa가 0이 되면 정답으로 모든 빈칸이 채워졌으므로 게임클리어
						if (aa == 0) {
							jb.setText(value);
							JOptionPane.showMessageDialog(null, "게임클리어.!!");
							//Rank 클래스에 시간과 틀린횟수, 난이도 유저아이디 저장
							rank.setTimer(hours_string + ":" + minutes_string + ":" + seconds_string);
							rank.setCount(String.valueOf(sum));
							rank.setLevel(s);
							rank.setUserID(join.getID());
							sdb.insertUserRate(rank);
							//datas는 실시간으로 game_Screen에서 기록을 보기위함
							datas = sdb.selectgameset(join.getID());
							//창 닫고 난이도 선택 및 랭킹보기 화면으로
							dispose();
							new game_Screen(join, datas);
						}
					}
				});
			}
		}
		//숫자 버튼 생성
		for (int i = 0; i < 9; i++) {
			click[i] = new JButton("" + (i + 1));
			//패널에 추가
			Click_Panel.add(click[i]);
			//이벤트 처리
			click[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JButton jb = (JButton) e.getSource();
					//삼향연산자로 버튼 클릭 시 isEnabled() == 항상 true 이므로 버튼의 내용을 value에 저장
					value = jb.isEnabled() ? jb.getText() : "0";
				}
			});
		}
		// 지우기 버튼 생성
		click[9] = new JButton("X");
		//패널에 추가
		Click_Panel.add(click[9]);
		//이벤트 처리
		click[9].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton jb = (JButton) e.getSource();
				//삼향연산자로 버튼 클릭 시 isEnabled() == 항상 true 이므로 공백을 value에 저장
				value = jb.isEnabled() ? "" : "0";
			}
		});
		// 애매한 것을 체크하기 위한 ? 버튼 생성
		click[10] = new JButton("?");
		//패널에 추가
		Click_Panel.add(click[10]);
		//이벤트 처리
		click[10].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton jb = (JButton) e.getSource();
				//삼향연산자로 버튼 클릭 시 isEnabled() == 항상 true 이므로 ?를 value에 저장
				value = jb.isEnabled() ? "?" : "0";
			}
		});
		//시간 라벨 디자인 설정
		timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
		timeLabel.setBounds(100, 100, 200, 100);
		timeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 35));
		timeLabel.setBorder(BorderFactory.createBevelBorder(1));
		timeLabel.setOpaque(true);
		timeLabel.setHorizontalAlignment(JTextField.CENTER);
		//게임시작버튼 디자인 설정
		startButton.setBounds(100, 200, 100, 50);
		startButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		startButton.setFocusable(false);
		startButton.addActionListener(this);
		//시간패널에 게임시작버튼과 라벨 추가
		Time_Panel.add(timeLabel);
		Time_Panel.add(startButton);
		//패널 여백 설정
		Button_Panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		Click_Panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		//컨테이너에 추가
		ct.add(Time_Panel, BorderLayout.BEFORE_FIRST_LINE);
		ct.add(Button_Panel, BorderLayout.CENTER);
		ct.add(Click_Panel, BorderLayout.EAST);

		setTitle("로그인 화면");
		setSize(700, 700);
		setVisible(true);
		setLocationRelativeTo(null);

	}
	//시작버튼 눌렸을 시 시간이 흐르게끔 하는 메소드
	void start() {
		timer.start();
	}
	//게임시작 후 게임포기 할 때 시간 정지시키는 메소드
	void stop() {
		timer.stop();
	}
	//이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		//게임시작 버튼 클릭 시 
		if (e.getSource() == startButton) {
			//started 초기값 false 이고, 시작버튼 누를 시 true로
			if (started == false) {
				started = true;
				//2차원 배열에 문제 가져오기
				int a[][] = sudoku.quiz_printSudoku();
				//버튼에 set
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						//가져온 문제를 버튼 하나하나에 set
						button[i][j].setText("" + a[i][j]);
						//맞춰야되는 부분이 0으로 되어있으므로 button에 넣고 만약 Text가 0인경우에 공백으로 설정
						if (button[i][j].getText().equals("" + 0)) {
							button[i][j].setText("");
							//맞춰야되는 문제의 수 증가
							aa++;
							button[i][j].setEnabled(true);
						} 
						//주어지는 수 편집불가 설정
						else
							button[i][j].setEnabled(false);

					}
				}
				//게임시작을 누른 후 해당 버튼에 게임포기라는 텍스트 보이게 설정
				startButton.setText("게임포기");
				//타이머 스타트
				start();
			} 
			//게임포기 버튼 클릭했을 경우
			else if (started) {
				started = false;
				//게임 포기유무 묻기 n =0 일경우 예, 1일경우 아니오
				int n = JOptionPane.showConfirmDialog(this, "정말 게임을 포기하시겠습니까?", "게임포기 유무", JOptionPane.YES_NO_OPTION);

				if (n == 1) {
					//포기X 일 경우 시간 다시 시작
					start();
					return;
				} else if (n == 0) { // 수정 5번
					//포기했을 경우 창닫고 난이도 선택 및 랭킹보기 화면으로 가기. (datas는 실시간으로 game_Screen에서 기록을 보기위함.
					dispose();
					datas = sdb.selectgameset(join.getID());
					new game_Screen(join, datas);
				}
				//시간 정지
				stop();
			}

		}
	}
	//게임종료 메소드
	void gameExit() { // 수정 2번
		JOptionPane.showMessageDialog(this, "틀린횟수가 3번 초과하셔서 게임이 종료됩니다.", "게임종료", JOptionPane.YES_OPTION);
		//게임종료했을 경우 창닫고 난이도 선택 및 랭킹보기 화면으로 가기. (datas는 실시간으로 game_Screen에서 기록을 보기위함.
		datas = sdb.selectgameset(join.getID());
		dispose();
		new game_Screen(join, datas);
	}
}
