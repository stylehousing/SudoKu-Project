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
	
	//��ư ������ ���� Ŭ����
	public class RoundedButton extends JButton {
	      public RoundedButton() { super(); decorate(); } 
	      public RoundedButton(String text) { super(text); decorate(); } 
	      public RoundedButton(Action action) { super(action); decorate(); } 
	      public RoundedButton(Icon icon) { super(icon); decorate(); } 
	      public RoundedButton(String text, Icon icon) { super(text, icon); decorate(); } 
	      protected void decorate() { setBorderPainted(false); setOpaque(false); }
	     //���� �� ��ư ��� ����
	      @Override 
	      protected void paintComponent(Graphics g) {
	         Color c=new Color(102,102,102); //���� ����
	         Color o=new Color(255,255,255); //���ڻ� ����
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

	//�����̳� ����
	private Container ct = getContentPane();
	//ȯ���λ� �� ����
	private JLabel greet = new JLabel();
	//��� �ؽ�Ʈ�����
	private JTextArea record = new JTextArea(2,10);
	//��, ��, ��, ��ŷ���� ��ư
	private RoundedButton easy = new RoundedButton(" �� �� ");
	private RoundedButton normal = new RoundedButton(" �� �� ");
	private RoundedButton hard = new RoundedButton(" �� �� ");
	private RoundedButton show_rank = new RoundedButton("��ŷ ����");
	//�α׾ƿ���ư
	private JButton Exit = new JButton("�α׾ƿ�");
	//�г� ���� �� ���̾ƿ� ����
	private JPanel Exit_Panel = new JPanel(new GridLayout());
	private JPanel Label_Panel = new JPanel(new GridLayout(1,2,20,2));
	private JPanel Button_Panel = new JPanel(new GridLayout(4,1,0,5));
	private JPanel Text_Panel = new JPanel(new GridLayout(1,1,0,2));
	//game�� ���� �� ��ŷ����� ���̵� ���� ȭ��
	public game_Screen(Join join, ArrayList<Rank> al) {
		//join �Ѱܹޱ�
		this.join = join;
		//s�� �г��� ����
		String s = join.getName();
		//�����̳� ���̾ƿ� ����
		ct.setLayout(new BorderLayout());
		//ȯ���λ� (�г��� �߰�)
		greet.setText("�ȳ��ϼ���! " + s +"�� ȯ���մϴ�.");
		//StringBuffer ����
		StringBuffer sb = new StringBuffer();
		//ArrayList �� ����
		ArrayList<Rank> datas = al;
		//datas�� null�� �ƴϸ� (����� ���� ��
		if(!datas.equals("")) {
			record.setText("          ���\n");
			//for each������ sb�� append
			for(Rank r : datas) {
				sb.append(r.getLevel() +"\t");
				sb.append(r.getTimer() +"\n");
			}
			record.append(sb.toString());			
		}
		//����� ���� ��
		else
			record.append("����� �����ϴ�.");
		//�����Ұ�����
		record.setEditable(false);
		//�������
		greet.setHorizontalAlignment(JLabel.CENTER);
		//�������
		greet.setOpaque(true);
		//��������
		greet.setBackground(Color.WHITE);
		//��Ʈ �۲� ����
		greet.setFont(new Font("���� ���", Font.PLAIN, 30));
		easy.setFont(new Font("���� ���", Font.BOLD, 40));
		normal.setFont(new Font("���� ���B", Font.BOLD, 40));
		hard.setFont(new Font("���� ���", Font.BOLD, 40));
		show_rank.setFont(new Font("���� ���", Font.BOLD, 40));
		record.setFont(new Font("���� ���", Font.BOLD, 15));
		//�гο� �߰�
		Label_Panel.add(greet);
		Exit_Panel.add(Exit);
		Button_Panel.add(easy);
		Button_Panel.add(normal);
		Button_Panel.add(hard);
		Button_Panel.add(show_rank);
		Text_Panel.add(record);
		//�̺�Ʈ �׼Ǹ����� �߰�
		Exit.addActionListener(this);
		easy.addActionListener(this);
		normal.addActionListener(this);
		hard.addActionListener(this);
		show_rank.addActionListener(this);
		//�����̳ʿ� �г� �߰� ��ġ����
		ct.add(Label_Panel, BorderLayout.NORTH);
		ct.add(Exit_Panel, BorderLayout.SOUTH);
		ct.add(Button_Panel, BorderLayout.CENTER);
		ct.add(Text_Panel, BorderLayout.EAST);
		
		setTitle("���� �غ� ȭ��");
		setSize(800, 500);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	//�̺�Ʈ
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		//�ʱ� ��ư Ŭ�� ��
		if(obj == easy) {
			dispose();
			new play_Screen("�ʱ�", join);
		}
		//�߱� ��ư Ŭ�� ��
		else if(obj == normal) {
			dispose();
			new play_Screen("�߱�", join);
		}
		//��� ��ư Ŭ�� ��
		else if(obj == hard) {
			dispose();
			new play_Screen("���", join);
		}
		//��ŷ ���� ��ư Ŭ�� ��
		else if(obj == show_rank) {
			dispose();
			new rank_Screen(join);
		}
		//�α׾ƿ� ��ư Ŭ�� ��
		else if(obj ==Exit) {
			dispose();
			new login_Screen();
		}
	}
	
}