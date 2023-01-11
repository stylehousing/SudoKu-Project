import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class login_Screen extends JFrame implements ActionListener {
	//�����̳� ����
	Container ct = getContentPane();
	//�� ����
	JLabel Title_Label = new JLabel("SUDOKU");
	JLabel ID_Label = new JLabel("ID :   ");
	JLabel PW_Label = new JLabel("PW :   ");
	//������� ���� �� SUDOKU���� ���� �г�
	JPanel Title_Panel = new JPanel(new GridLayout(1,1,0,15));
	//ID :, PW : ���� ���� �г�
	JPanel Label_Panel = new JPanel(new GridLayout(3,1,0,30));
	//ID, PW �Է¹��� �г�
	JPanel Input_Panel = new JPanel(new GridLayout(3,1,0,30));
	//IDã��, PWã�� ��ư �г�
	JPanel Search_Panel = new JPanel(new GridLayout(3,1,0,30));
	//�α���, ȸ������ ��ư �г�
	JPanel Button_Panel = new JPanel(new GridLayout(1,2,30,15));
	//���̵� ��й�ȣ �Է¹��� �ؽ�Ʈ�ʵ�
	JTextField ID_In = new JTextField(15);
	JPasswordField PW_In = new JPasswordField();
	//������ ��ư
	JButton ID_Search = new JButton("ID ã��");
	JButton PW_Search = new JButton("PW ã��");
	JButton Login = new JButton("�α���");
	JButton Join = new JButton("ȸ������");	
	//�α��� ȭ�� �޼ҵ�
	public login_Screen() {
		//�����̳� ���̾ƿ� ����
		ct.setLayout(new BorderLayout());
		//�� ��Ʈ ����
		Title_Label.setFont(new Font("���� ���", Font.BOLD, 18));
		//�� ��� ��ġ
		Title_Label.setHorizontalAlignment(JLabel.CENTER);
		//�гε� ���� ����
		Title_Panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		Label_Panel.setBorder(BorderFactory.createEmptyBorder(0, 60, 0, 0));
		Input_Panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		Search_Panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		Button_Panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 40, 30));
		//Title�гο� �� �߰�
		Title_Panel.add(Title_Label);
		//�� �гο� ID, PW �� �߰�
		Label_Panel.add(ID_Label);
		Label_Panel.add(PW_Label);
		//�гο� ID, PW �Է��ϴ� �ؽ�Ʈ�ʵ� �߰�		
		Input_Panel.add(ID_In);
		Input_Panel.add(PW_In);
		//�гο� ���̵� ��� ã�� ��ư �߰�
		Search_Panel.add(ID_Search);
		Search_Panel.add(PW_Search);
		//�α��� ��ư, ȸ������ ��ư �гο� �߰�
		Button_Panel.add(Login);
		Button_Panel.add(Join);
		PW_In.setEchoChar('*');
		//�̺�Ʈ ó��
		ID_Search.addActionListener(this);
		PW_Search.addActionListener(this);
		Login.addActionListener(this);
		Join.addActionListener(this);
		//�����̳ʿ� �г� �߰� �� ��ġ ����
		ct.add(Title_Panel, BorderLayout.NORTH);
		ct.add(Label_Panel, BorderLayout.WEST);
		ct.add(Input_Panel, BorderLayout.CENTER);
		ct.add(Search_Panel, BorderLayout.EAST);
		ct.add(Button_Panel, BorderLayout.SOUTH);
		
		setTitle("�α��� ȭ��");
		setSize(400, 300);		
		setVisible(true);
		//â ��� �� ����� ��ġ
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		Join join = new Join();
		//���̵� ã�� ��ư�̶��
		if (obj == ID_Search) {
			//���̵� ã�� ȣ��
			new search().idSearch();;
		}
		//PWã�� ��ư�̶��
		else if (obj == PW_Search) {
			//���ã�� ȣ��
			new search().pwSearch();;
		}
		//�α��� ��ư�̶��
		else if (obj == Login) {
			//password�� char => String ��ȯ
			String s = "";
			char [] c = PW_In.getPassword();
			for(int i = 0; i < c.length; i++)
				s+=c[i];
			//DBŬ���� ��ü����
			Sudoku_DB sdb = new Sudoku_DB();
			//join�� �ش��ϴ� ID�� DB�� �ִ��� Ȯ�� �� null�̸� ���̵� �����Ƿ� Ȯ�ο�û
			join = sdb.selectmember(ID_In.getText());
			if (join == null || !s.equals(join.getPW())) JOptionPane.showMessageDialog(this, "���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ�����ּ���.!!");
			//���̵� ��� ���� �ϳ��� ���� �� ��� �Է¿�û
			else if(ID_In.getText().equals("") || s.equals("")) {
				JOptionPane.showMessageDialog(this, "���̵� �Ǵ� ��й�ȣ�� �Է����ּ���.!!");
			}
			//DB���� �޾ƿ� ����Ǿ��ִ� join�� ���̵�� ��й�ȣ�� ��ġ�Ѵٸ� �α��� ����
			else if(ID_In.getText().equals(join.getID()) && s.equals(join.getPW())) {
				//�ǽð����� ����� �������� datas
				ArrayList<Rank> datas = new ArrayList<Rank>();
				join.setID(ID_In.getText());
				datas = sdb.selectgameset(join.getID());
				//���� â ���� �� ���̵� ���� �� ��ŷ���� ȭ��
				dispose();
				new game_Screen(join, datas);				
			}

				
		}
		//ȸ������ ��ư ������ �� ȸ������ GUIâ ����
		else if (obj == Join) {
			dispose();
			new join_Screen();
		}
	}

}

public class Sudoku_GUI {

	public static void main(String[] args) {
		new login_Screen();
	}

}
