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
	// JoinŬ���� ��ü����
	Join join = new Join();
	// ArrayList����
	ArrayList<Rank> datas = new ArrayList<Rank>();
	// �����̳� ����
	Container ct = getContentPane();
	// �ʱ��߱޻�� �� ��ŷ ���� ���� Tab�̿�
	JTabbedPane jtp = new JTabbedPane();
	// ������ ��ư
	JButton Exit = new JButton("������");

	public rank_Screen(Join join) {
		// join �޾ƿ���
		this.join = join;
		// �����̳� ���̾ƿ� ����
		ct.setLayout(new BorderLayout());
		// ��ư �̺�Ʈó��
		Exit.addActionListener(this);
		// �ǿ� ���̵��� �޼ҵ� ����
		jtp.addTab("�ʱ�", new Easy());
		jtp.addTab("�߱�", new Normal());
		jtp.addTab("���", new Hard());
		// �����̳ʿ� �߰�
		ct.add(jtp, BorderLayout.NORTH);
		ct.add(Exit, BorderLayout.SOUTH);

		setTitle("��ŷ ����");
		setSize(300, 500);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	// �̺�Ʈ
	@Override
	public void actionPerformed(ActionEvent e) {
		// ������ ��ư�� ���
		if (e.getSource() == Exit) {
			// DBŬ���� ����
			Sudoku_DB sdb = new Sudoku_DB();
			// ���� â �ݱ�
			dispose();
			// datas�� �ǽð����� game_Screen���� ����� ��������
			datas = sdb.selectgameset(join.getID());
			// ���̵� ���� �� ��ŷ���� ȭ������
			new game_Screen(join, datas);
		}
	}

}

//�ʱ޳��̵�
class Easy extends JPanel {
	// TextArea �ʱⰪ ����, �ð�, �г���
	JTextArea jta = new JTextArea("����\t�ð�\t�г���\n");
	// DB Ŭ���� ��ü ����
	Sudoku_DB sdb = new Sudoku_DB();

	public Easy() {
		// ���̾ƿ� ����
		setLayout(new BorderLayout());
		// area ��Ʈ����
		jta.setFont(new Font("���� ���", Font.BOLD, 15));
		// DB���� �ð����� ���� �� TextArea�� �̾���̱�
		jta.append(sdb.DeptSortStudent("�ʱ�"));
		add(jta, BorderLayout.CENTER);

	}
}

//�߱޳��̵�
class Normal extends JPanel {
	// TextArea �ʱⰪ ����, �ð�, �г���
	JTextArea jta = new JTextArea("����\t�ð�\t�г���\n");
	// DB Ŭ���� ��ü ����
	Sudoku_DB sdb = new Sudoku_DB();

	public Normal() {
		// ���̾ƿ� ����
		setLayout(new BorderLayout());
		// area ��Ʈ����
		jta.setFont(new Font("���� ���", Font.BOLD, 15));
		// DB���� �ð����� ���� �� TextArea�� �̾���̱�
		jta.append(sdb.DeptSortStudent("�߱�"));
		add(jta, BorderLayout.CENTER);

	}
}

//��޳��̵�
class Hard extends JPanel {
	// TextArea �ʱⰪ ����, �ð�, �г���
	JTextArea jta = new JTextArea("����\t�ð�\t�г���\n");
	// DB Ŭ���� ��ü ����
	Sudoku_DB sdb = new Sudoku_DB();

	public Hard() {
		// ���̾ƿ� ����
		setLayout(new BorderLayout());
		// area ��Ʈ����
		jta.setFont(new Font("���� ���", Font.BOLD, 15));
		// DB���� �ð����� ���� �� TextArea�� �̾���̱�
		jta.append(sdb.DeptSortStudent("���"));
		add(jta, BorderLayout.CENTER);
	}
}