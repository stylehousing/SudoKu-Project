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
//ȸ������ Ŭ����
class join_Screen extends JFrame implements ActionListener {
	//�����̳� ����
	private Container ct = getContentPane();
	//JoinŬ���� ����
	private Join join = new Join();
	//�� ����
	private JLabel Title_Label = new JLabel("ȸ �� �� ��");
	private JLabel ID_Label = new JLabel("���̵�:");
	private JLabel NickName_Label = new JLabel("�г���:");
	private JLabel PW_Label = new JLabel("��й�ȣ:");
	private JLabel CheckPW_Label = new JLabel("��й�ȣ ��Ȯ��:");
	private JLabel Birth_Label = new JLabel("�̸���:");
	private JLabel jl = new JLabel("@");
//	JLabel jl6 = new JLabel("����Ͻ� �г����� �Է����ּ���.");
//	JLabel jl7 = new JLabel("��й�ȣ�� �Է����ּ���.");
//	JLabel jl8 = new JLabel("��й�ȣ�� �ٽ� �ѹ� �Է����ּ���.");
	//���̵�, �г��� �ߺ�Ȯ�� ��ư ���� ���� Ȯ��
	private boolean name;
	private boolean id;
	//�г� ���� �� ���̾ƿ� ����
	private JPanel Label_Panel = new JPanel(new BorderLayout());
	private JPanel Text_Panel = new JPanel(new GridLayout(5,1,0,15));
	private JPanel Input_Panel = new JPanel(new GridLayout(5,1,0,15));
	private JPanel Check_Panel = new JPanel(new GridLayout(5,1,0,15));
	private JPanel Button_Panel = new JPanel(new GridLayout(1,2,20,20));	
	private JPanel Email_Panel = new JPanel(new GridLayout(1,3,0,0));
	private JPanel Back_Panel = new JPanel();
	//�ؽ�Ʈ�ʵ� ����
	private JTextField ID_In = new JTextField();
	private JTextField NickName_In = new JTextField();
	private JTextField email = new JTextField(20);
	//�н����� �ʵ� ����
	private JPasswordField PW_In = new JPasswordField();
	private JPasswordField CheckPW_In = new JPasswordField();
	
	//�޺��ڽ� ����
	private JComboBox com = new JComboBox();
	//��ư ����
	private JButton ID_Overlap =  new JButton("�ߺ�Ȯ��");
	private JButton NickName_Overlap =  new JButton("�ߺ�Ȯ��");
	private JButton Back = new JButton("�ڷ� ����");
	private JButton Check = new JButton("Ȯ��");
	private JButton New_In = new JButton("���� �Է�");
	//������
	public join_Screen() {
		//�����̳� ���̾ƿ� ����
		ct.setLayout(new BorderLayout(20,20));
		//�޺��ڽ� ������ �߰�
		com.addItem("naver.com");
		com.addItem("nate.com");
		com.addItem("gmail.com");
		//Ÿ��Ʋ ��Ʈ ���� �� ��� ����
		Title_Label.setFont(new Font("���� ���", Font.BOLD, 20));
		Title_Label.setHorizontalAlignment(JLabel.CENTER);
		//�ڷΰ��� ��ư ��ġ ����
		Back.setHorizontalAlignment(JButton.NORTH_EAST);
		//�г� ���� ����
		Label_Panel.setBorder(BorderFactory.createEmptyBorder(10, 90, 20, 0));
		Text_Panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 10));
		Button_Panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 20));
		Check_Panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 5));
		//�н����� �Է� �� ��ȣȭ�ϱ� ���� '*'�� ����
		CheckPW_In.setEchoChar('*');
		PW_In.setEchoChar('*');
		//Label�гο� �߰�
		Label_Panel.add(Title_Label, BorderLayout.CENTER);
		Label_Panel.add(Back, BorderLayout.EAST);
		//�ؽ�Ʈ �гο� ���߰�
		Text_Panel.add(ID_Label);
		Text_Panel.add(NickName_Label);
		Text_Panel.add(PW_Label);
		Text_Panel.add(CheckPW_Label);
		Text_Panel.add(Birth_Label);
		//Input �гο� �ʵ� �߰�
		Input_Panel.add(ID_In);
		Input_Panel.add(NickName_In);
		Input_Panel.add(PW_In);
		Input_Panel.add(CheckPW_In);
		//�̸��� �гο� �̸��ϰ��� �߰�
		Email_Panel.add(email);
		Email_Panel.add(jl);
		Email_Panel.add(com);
		//Input�гο� �̸����г� �߰�
		Input_Panel.add(Email_Panel);
		//�ߺ�Ȯ�� �гο� �߰�
		Check_Panel.add(ID_Overlap);
		Check_Panel.add(NickName_Overlap);
		//��ư �гο� �߰�
		Button_Panel.add(Check);
		Button_Panel.add(New_In);
		//�̺�Ʈ �׼Ǹ�����
		ID_Overlap.addActionListener(this);
		NickName_Overlap.addActionListener(this);
		Check.addActionListener(this);
		New_In.addActionListener(this);
		Back.addActionListener(this);
		//�����̳ʿ� �г� �߰� �� ��ġ ����
		ct.add(Label_Panel, BorderLayout.NORTH);
		ct.add(Text_Panel, BorderLayout.WEST);
		ct.add(Input_Panel, BorderLayout.CENTER);
		ct.add(Check_Panel, BorderLayout.EAST);
		ct.add(Button_Panel, BorderLayout.SOUTH);
		
		setTitle("ȸ������ ȭ��");
		setSize(500, 500);
		setVisible(true);
		setLocationRelativeTo(null);

	}
	//�̺�Ʈ
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		int n;
		//�̹� ���̵� �г����� ������쿡
		//showInputDialog() �̿��ؼ� ���Է¹ޱ�
		Sudoku_DB sdb = new Sudoku_DB();
		//���̵� �ߺ�Ȯ�� ��ư
		if (obj == ID_Overlap) {
			id = false;
			//DB�� �Է¹��� ���̵� �ߺ��Ǵ��� �ȵǴ��� Ȯ��
			if(sdb.overlap(ID_In.getText(), "ID")) {
				
				//������ 0, ��� 1 ��ȯ
				n = JOptionPane.showConfirmDialog(this, "��� ������ ���̵��Դϴ�.\n����Ͻðڽ��ϱ�?", "���̵� �ߺ�����", JOptionPane.YES_NO_OPTION);
				//�� ������ ���
				if(n == 0) {
					id = true;
					join.setID(ID_In.getText());
				}
			}
			//DB�� ����� ���̵� �� �ߺ����� ���
			else JOptionPane.showMessageDialog(this, "�̹� ������� ���̵��Դϴ�.!!");
			
		}
		//�г��� �ߺ�Ȯ�� ��ư
		else if (obj == NickName_Overlap) {
			//DB�� �Է¹��� �г����� �ߺ��Ǵ��� �ȵǴ��� Ȯ��
			if(sdb.overlap(NickName_In.getText(), "name")) {
				name = false;
				//������ 0, ��� 1 ��ȯ
				n = JOptionPane.showConfirmDialog(this, "��� ������ �г����Դϴ�.\n����Ͻðڽ��ϱ�?", "�г��� �ߺ�����", JOptionPane.YES_NO_OPTION);
				//�� ������ ���
				if(n == 0) {
					join.setName(NickName_In.getText());
					name = true;
				}
			}
			//DB�� ����� �г��� �� �ߺ����� ���
			else JOptionPane.showMessageDialog(this, "�̹� ������� �г����Դϴ�.!!");
			
		}
		
		//Ȯ�� ��ư
		else if (obj == Check) {
			//�Է��� �̸��� ���ڿ� s�� ����
			String s = email.getText()+ jl.getText() + com.getSelectedItem();
			//�̸��� �ߺ����� Ȯ��
			if(!sdb.overlap(s, "email")) {
				JOptionPane.showMessageDialog(this, "�̹� ������� �̸����Դϴ�.!!");
			}
			//name�� id �ߺ�Ȯ���� �ߴ��� üũ, ����� ������ üũ
			else if (name && id && !ID_In.getText().equals("") && !PW_In.getPassword().equals("") && !NickName_In.getText().equals("") && !email.getText().equals("")) {	
				//�н����� �ʵ� �� char���̱⿡ String���� ��ȯ����
				String pw = "";
				char [] c = PW_In.getPassword();
				for(int i = 0; i < c.length; i++)
					pw+=c[i];
				//joinŬ������ set
				join.setPW(pw);
				join.setEmail(s);
				//DB�� insert ���� ��
				if(sdb.insertmember(join)) {
					JOptionPane.showMessageDialog(this, "ȸ�����Կ� �����ϼ̽��ϴ�.!!");
					dispose();
					new login_Screen();
				}
				//���� ��
				else JOptionPane.showMessageDialog(this, "ȸ�����Կ� �����ϼ̽��ϴ�.!!");
				
			}
			//���̵� �Է� �Ǵ� ��� �Է� �Ǵ� �г��� �Է��� ������ ���
			else if(ID_In.getText().equals("") || PW_In.getPassword().equals("") || CheckPW_In.getPassword().equals("") || NickName_In.getText().equals("") || email.getText().equals(""))
				JOptionPane.showMessageDialog(this, "����׸��� �Է����ּ���");
			//id�ߺ�Ȯ�� ���������
			else if(!id && name) JOptionPane.showMessageDialog(this, "���̵� �ߺ�Ȯ���� ���ּ���");
			//�г��� �ߺ�Ȯ�� ������ ���
			else if(id && !name) JOptionPane.showMessageDialog(this, "�г��� �ߺ�Ȯ���� ���ּ���");
			else if(!id && !name) JOptionPane.showMessageDialog(this, "���̵�� �г��� �ߺ�Ȯ���� ���ּ���");
		}
		//���� �Է� ��ư
		else if (obj == New_In) {
			ID_In.setText("");
			NickName_In.setText("");
			PW_In.setText("");
			CheckPW_In.setText("");
			email.setText("");
		}
		//�ڷΰ��� ��ư Ŭ�� ��
		else if (obj == Back) {
			dispose();
			new login_Screen();
		}
	}
}