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

//���̵� ��� ã�� Ŭ����
public class search extends JFrame {
	Join join = new Join();
	Container ct = getContentPane();
	//���̵� ã�� �޼ҵ�
	public void idSearch() {
		//���̾ƿ� ����
		ct.setLayout(new GridLayout(3, 1));
		//�� ����
		JLabel Title_Label = new JLabel("���̵� ã��");
		JLabel Text_Label = new JLabel("�̸���");
		JLabel jl = new JLabel("@");
		//�ؽ�Ʈ �ʵ� ����
		JTextField email = new JTextField(10);
		JTextField ID_out = new JTextField();
		//�޺��ڽ� ����
		JComboBox com = new JComboBox();
		//�г� ����
		JPanel set_Panel = new JPanel();
		//��ư ����
		JButton get_ID = new JButton("Ȯ��");
		//�޺��ڽ� ������ �߰�
		com.addItem("naver.com");
		com.addItem("nate.com");
		com.addItem("gmail.com");
		//�� ��ġ ��Ʈ ����
		Title_Label.setHorizontalAlignment(JLabel.CENTER);
		Title_Label.setFont(new Font("���� ���", Font.PLAIN, 30));
		//�гο� �߰�
		set_Panel.add(Text_Label);
		set_Panel.add(email);
		set_Panel.add(jl);
		set_Panel.add(com);
		//�̺�Ʈ ó��
		get_ID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//email �ּ� String s�� ����
				String s = email.getText() + jl.getText() + com.getSelectedItem();
				//DB����
				Sudoku_DB sdb = new Sudoku_DB();
				//DB�� �̸��� �� ������ ���̵� ã��
				join = sdb.searchID(s);
				//join�� null�� �ƴϸ�
				if (join != null)
					JOptionPane.showMessageDialog(null, "����� ���̵�� " + join.getID() + "�Դϴ�.!!");
				else JOptionPane.showMessageDialog(null, "�ش��ϴ� �̸��Ͽ� ���̵� ���������ʽ��ϴ�.");
			}
		});
		//�����̳ʿ� �߰�
		ct.add(Title_Label);
		ct.add(set_Panel);
		ct.add(get_ID);
		
		setTitle("ID ã��");
		setSize(300, 300);
		setVisible(true);
		// â ��� �� ����� ��ġ
		setLocationRelativeTo(null);
	}
	//���ã�� �޼ҵ�
	public void pwSearch() {
		//���̾ƿ� ����
		ct.setLayout(new GridLayout(4, 1));
		//�� ����
		JLabel Title_Label = new JLabel("��й�ȣ ã��");
		JLabel Text_Label = new JLabel("�̸���");
		JLabel ID_Label = new JLabel("���̵�");
		JLabel jl = new JLabel("@");
		//�ؽ�Ʈ�ʵ� ����
		JTextField email = new JTextField(10);
		JTextField id = new JTextField(10);
		JTextField ID_out = new JTextField();
		//�޺��ڽ� ����
		JComboBox com = new JComboBox();
		//�г� �ΰ� ����
		JPanel ID_Panel = new JPanel();
		JPanel set_Panel = new JPanel();
		//��ư ����
		JButton get_ID = new JButton("Ȯ��");
		//�޺��ڽ� ������ �߰�
		com.addItem("naver.com");
		com.addItem("nate.com");
		com.addItem("gmail.com");
		//Ÿ��Ʋ �� ��� ��ġ, ��Ʈ ����
		Title_Label.setHorizontalAlignment(JLabel.CENTER);
		Title_Label.setFont(new Font("���� ���", Font.PLAIN, 30));
		//�гο� �߰�
		ID_Panel.add(ID_Label);
		ID_Panel.add(id);
		//�гο� �߰�
		set_Panel.add(Text_Label);
		set_Panel.add(email);
		set_Panel.add(jl);
		set_Panel.add(com);
		//�̺�Ʈ ó��
		get_ID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//���ڿ� s�� email �ּ� ����
				String s = email.getText() + jl.getText() + com.getSelectedItem();
				//DB Ŭ���� ����
				Sudoku_DB sdb = new Sudoku_DB();
				//DB�� searchPW�޼ҵ忡 �̸����ּҿ� �Է��� id�� �Ű�����
				join = sdb.searchPW(s, id.getText());
				//DB���� �޾ƿ� joinŬ������ null�� �ƴϸ�
				if (join != null) {
					int n = JOptionPane.showConfirmDialog(null, "����� ��й�ȣ�� " + join.getPW() + "�Դϴ�.!!\n���� �Ͻðڽ��ϱ�?", "��й�ȣ ���濩��", JOptionPane.YES_NO_OPTION);
					//yes Ŭ�� �� ��й�ȣ ����
					if(n==0) {
						JPanel panel = new JPanel();
						JPanel panel1 = new JPanel();
						JPanel panel2 = new JPanel(new GridLayout(2,2));
						JLabel label = new JLabel("��й�ȣ �Է�:");
						JLabel label1 = new JLabel("��й�ȣ Ȯ��:");
						JPasswordField pass = new JPasswordField(10);
						JPasswordField pass_Check = new JPasswordField(10);
						panel.add(label);
						panel.add(pass);
						panel1.add(label1);
						panel1.add(pass_Check);
						panel2.add(panel);
						panel2.add(panel1);
						n = JOptionPane.showConfirmDialog(null, panel2, "��й�ȣ ����",JOptionPane.YES_NO_OPTION);
						String pw = "";
						String pw1 = "";
						char [] c = pass.getPassword();
						char [] c1 = pass_Check.getPassword();
						for(int i = 0; i < c.length; i++)
							pw+=c[i];
						for(int i = 0; i < c1.length; i++)
							pw1+=c1[i];
						//��й�ȣ �Է� �� Ȯ��Ŭ�� ��
						if(n==0 && !pw.equals("") && pw.equals(pw1) && !pw1.equals("")) {
							//DB���� ��й�ȣ ������Ʈ �޼ҵ�
							if(sdb.updatePW(join, pw)) {
								JOptionPane.showMessageDialog(null, "���������� ��й�ȣ������ �Ϸ�Ǿ����ϴ�.!!");	
								//â�ݱ�
								dispose();
							}
							else
								JOptionPane.showMessageDialog(null, "��й�ȣ ���濡 �����߽��ϴ�.!!");
							
						}
						else if(n==1) dispose();
						else JOptionPane.showMessageDialog(null, "��й�ȣ�� �ٸ��ϴ�.!!");
						
					}
					//no Ŭ�� ��
					else {
						//â�ݱ�
						dispose();
					}
				}
				//join�� null�� ��� (����)
				else JOptionPane.showMessageDialog(null, "��й�ȣ�� ã�� �� �����ϴ�.!!");	
			}
		});
		//�����̳ʿ� �߰�
		ct.add(Title_Label);
		ct.add(ID_Panel);
		ct.add(set_Panel);
		ct.add(get_ID);

		setTitle("PW ã��");
		setSize(300, 300);
		setVisible(true);
		// â ��� �� ����� ��ġ
		setLocationRelativeTo(null);
	}

}
