import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class game_Screen extends JFrame implements ActionListener {
	private Join join = new Join();
	
	//버튼 디자인 설정 클래스
	public class RoundedButton extends JButton {
	      public RoundedButton() { super(); decorate(); } 
	      public RoundedButton(String text) { super(text); decorate(); } 
	      public RoundedButton(Action action) { super(action); decorate(); } 
	      public RoundedButton(Icon icon) { super(icon); decorate(); } 
	      public RoundedButton(String text, Icon icon) { super(text, icon); decorate(); } 
	      protected void decorate() { setBorderPainted(false); setOpaque(false); }
	     //색상 및 버튼 모양 설정
	      @Override 
	      protected void paintComponent(Graphics g) {
	         Color c=new Color(102,102,102); //배경색 결정
	         Color o=new Color(255,255,255); //글자색 결정
	         int width = getWidth(); 
	         int height = getHeight(); 
	         Graphics2D graphics = (Graphics2D) g; 
	         graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
	         if (getModel().isArmed()) { graphics.setColor(c.darker()); } 
	         else if (getModel().isRollover()) { graphics.setColor(c.brighter()); } 
	         else { graphics.setColor(c); } 
	         graphics.fillRoundRect(0, 0, width, height, 10, 10); 
	         FontMetrics fontMetrics = graphics.getFontMetrics(); 
	         Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
	         int textX = (width - stringBounds.width) / 2; 
	         int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	         graphics.setColor(o); 
	         graphics.setFont(getFont()); 
	         graphics.drawString(getText(), textX, textY); 
	         graphics.dispose(); 
	         super.paintComponent(g); 
	         }
	      }

	//컨테이너 생성
	private Container ct = getContentPane();
	//환영인사 라벨 생성
	private JLabel greet = new JLabel();
	//기록 텍스트에어리어
	private JTextArea record = new JTextArea(2,10);
	//초, 중, 상, 랭킹보기 버튼
	private RoundedButton easy = new RoundedButton(" 초 급 ");
	private RoundedButton normal = new RoundedButton(" 중 급 ");
	private RoundedButton hard = new RoundedButton(" 상 급 ");
	private RoundedButton show_rank = new RoundedButton("랭킹 보기");
	//로그아웃버튼
	private JButton Exit = new JButton("로그아웃");
	//패널 생성 및 레이아웃 설정
	private JPanel Exit_Panel = new JPanel(new GridLayout());
	private JPanel Label_Panel = new JPanel(new GridLayout(1,2,20,2));
	private JPanel Button_Panel = new JPanel(new GridLayout(4,1,0,5));
	private JPanel Text_Panel = new JPanel(new GridLayout(1,1,0,2));
	//game에 들어가기 전 랭킹보기와 난이도 선택 화면
	public game_Screen(Join join, ArrayList<Rank> al) {
		//join 넘겨받기
		this.join = join;
		//s에 닉네임 저장
		String s = join.getName();
		//컨테이너 레이아웃 설정
		ct.setLayout(new BorderLayout());
		//환영인사 (닉네임 추가)
		greet.setText("안녕하세요! " + s +"님 환영합니다.");
		//StringBuffer 생성
		StringBuffer sb = new StringBuffer();
		//ArrayList 값 전달
		ArrayList<Rank> datas = al;
		//datas가 null이 아니면 (기록이 있을 때
		if(!datas.equals("")) {
			record.setText("          기록\n");
			//for each문으로 sb에 append
			for(Rank r : datas) {
				sb.append(r.getLevel() +"\t");
				sb.append(r.getTimer() +"\n");
			}
			record.append(sb.toString());			
		}
		//기록이 없을 때
		else
			record.append("기록이 없습니다.");
		//수정불가설정
		record.setEditable(false);
		//가운데정렬
		greet.setHorizontalAlignment(JLabel.CENTER);
		//설정허용
		greet.setOpaque(true);
		//배경색설정
		greet.setBackground(Color.WHITE);
		//폰트 글꼴 설정
		greet.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		easy.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		normal.setFont(new Font("맑은 고딕B", Font.BOLD, 40));
		hard.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		show_rank.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		record.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		//패널에 추가
		Label_Panel.add(greet);
		Exit_Panel.add(Exit);
		Button_Panel.add(easy);
		Button_Panel.add(normal);
		Button_Panel.add(hard);
		Button_Panel.add(show_rank);
		Text_Panel.add(record);
		//이벤트 액션리스너 추가
		Exit.addActionListener(this);
		easy.addActionListener(this);
		normal.addActionListener(this);
		hard.addActionListener(this);
		show_rank.addActionListener(this);
		//컨테이너에 패널 추가 위치지정
		ct.add(Label_Panel, BorderLayout.NORTH);
		ct.add(Exit_Panel, BorderLayout.SOUTH);
		ct.add(Button_Panel, BorderLayout.CENTER);
		ct.add(Text_Panel, BorderLayout.EAST);
		
		setTitle("게임 준비 화면");
		setSize(800, 500);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	//이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		//초급 버튼 클릭 시
		if(obj == easy) {
			dispose();
			new play_Screen("초급", join);
		}
		//중급 버튼 클릭 시
		else if(obj == normal) {
			dispose();
			new play_Screen("중급", join);
		}
		//상급 버튼 클릭 시
		else if(obj == hard) {
			dispose();
			new play_Screen("상급", join);
		}
		//랭킹 보기 버튼 클릭 시
		else if(obj == show_rank) {
			dispose();
			new rank_Screen(join);
		}
		//로그아웃 버튼 클릭 시
		else if(obj ==Exit) {
			dispose();
			new login_Screen();
		}
	}
	
}