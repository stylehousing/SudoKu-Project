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
	// �����̳� ����
	private Container ct = getContentPane();
	//�г� ���� �� ���̾ƿ� ����
	private JPanel Button_Panel = new JPanel(new GridLayout(9, 9));
	private JPanel Click_Panel = new JPanel(new GridLayout(11, 1));
	private JPanel Time_Panel = new JPanel(new GridLayout(3, 2));
	// ������ �� 9*9
	private JButton[][] button;
	// ���� �޾ƿ� 2�����迭 ��ư
	private JButton[][] button1;
	// ���� �Է� �� ��ư
	private JButton[] click;
	// ���ӽ��� ��ư
	private JButton startButton = new JButton("���ӽ���");
	// �̿��� �������� ������ ���� ArrayList ����
	private ArrayList<Rank> datas = new ArrayList<Rank>();
	// � ��ư�� Ŭ���ؼ� ���� ���������� ���� ����
	private static String value;
	// Ʋ�� Ƚ��
	private int sum = 0;
	// ���ӽ��۹�ư�� ���ȴ��� �ƴ���
	private boolean started = false;
	// sudokuŬ������ ���� �Ű����� N�� ��� ���� ��, K�� �� ������ ��
	private int N, K;
	// �� ����
	private JLabel timeLabel = new JLabel();
	// �ð�
	private int elapsedTime = 0;
	private int seconds = 0;
	private int minutes = 0;
	private int hours = 0;
	//ó�� �� ������ ������ �ְ� ���� ������ ���ҽ��� �������� �� ä������ ��� �������� �ϱ����� ���� ����
	private int aa = 0;
	// Join, Rank Ŭ���� ����
	private Join join = new Join();
	private Rank rank = new Rank();
	private Sudoku sudoku;
	// ����ȯint���� String������ ����
	private String seconds_string = String.format("%02d", seconds);
	private String minutes_string = String.format("%02d", minutes);
	private String hours_string = String.format("%02d", hours);
	// DBŬ���� ����
	private Sudoku_DB sdb = new Sudoku_DB();
	// Ÿ�̸� �̺�Ʈó��
	private Timer timer = new Timer(1000, new ActionListener() {

		public void actionPerformed(ActionEvent e) {

			elapsedTime = elapsedTime + 1000;
			// �ð����
			hours = (elapsedTime / 3600000);
			// �� ���
			minutes = (elapsedTime / 60000) % 60;
			// �� ���
			seconds = (elapsedTime / 1000) % 60;
			// ���ڿ��� ����ȯ
			seconds_string = String.format("%02d", seconds);
			minutes_string = String.format("%02d", minutes);
			hours_string = String.format("%02d", hours);
			// �󺧿� �ؽ�Ʈset
			timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);

		}

	});

	// ������
	public play_Screen(String s, Join join) {
		this.join = join;
		//�����̳� ���̾ƿ� ����
		ct.setLayout(new BorderLayout(20, 20));
		//�迭 ũ�⼳��
		button = new JButton[9][9];
		click = new JButton[11];

		//���� ���̵� ������ ���ڿ��� �޾ƿͼ� ���̵��� K�� ����
		if (s.equals("�ʱ�")) {
			N = 9;
			K = 10;
		} else if (s.equals("�߱�")) {
			N = 9;
			K = 20;
		} else if (s.equals("���")) {
			N = 9;
			K = 30;
		}
		//Sudoku���α׷� ��ü���� �Ű����� N,K
		sudoku = new Sudoku(N, K);
		//SudokuŬ������ fillValuees(������ ������) ȣ��
		sudoku.fillValues();
		//���� �޾ƿ��� 2�����迭
		int array[][] = sudoku.pirntSudoku();
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//2���� �迭 button�� �켱 �������� ����.
				button[i][j] = new JButton("");
				//�гο� ��ư �߰�
				Button_Panel.add(button[i][j]);
				button[i][j].setEnabled(false);
				//��ư �̺�Ʈ ó��
				button[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//JButton���·�
						JButton jb = (JButton) e.getSource();
						//���� ���ڰ� �ƴ� X��ư�̳� ?��ư Ŭ�� �� ��ư Ŭ����
						if (value.equals("") || value.equals("?")) {
							//���� ���
							jb.setBackground(Color.WHITE);
							//value�� ����
							jb.setText(value);
						}
						//���ڹ�ư Ŭ�� �� ��ư Ŭ�� ��
						else {
							//Ŭ�� �� ���� �ε����� �˱� ���� 2���� �迭�� ã��
							for (int i = 0; i < 9; i++) {
								for (int j = 0; j < 9; j++) {
									if ((i * 54) == jb.getY() && (j * 65 + 7) == jb.getX()) {
										//������ ����Ǿ� �ִ� array 2�����迭�� value���� �ٸ��ٸ� ���� X
										if (array[i][j] != Integer.parseInt(value)) {
											//���� ���������� ����
											jb.setBackground(Color.red);
											//Ʋ��Ƚ�� +1
											sum++;
										}
										//������ ���
										else {
											//���� ���
											jb.setBackground(Color.WHITE);
											//�����̹Ƿ� �����Ұ� ����
											jb.setEnabled(false);
											//�������Ƿ� aa -1
											aa--;
										}
										//���ǹ� ���� value���� ��ư�� set
										jb.setText(value);

									}
								}
							}
						}
						//Ʋ��Ƚ���� 3ȸ �ʰ��� ��� ��������
						if (sum > 3)
							gameExit();
						//aa�� 0�� �Ǹ� �������� ��� ��ĭ�� ä�������Ƿ� ����Ŭ����
						if (aa == 0) {
							jb.setText(value);
							JOptionPane.showMessageDialog(null, "����Ŭ����.!!");
							//Rank Ŭ������ �ð��� Ʋ��Ƚ��, ���̵� �������̵� ����
							rank.setTimer(hours_string + ":" + minutes_string + ":" + seconds_string);
							rank.setCount(String.valueOf(sum));
							rank.setLevel(s);
							rank.setUserID(join.getID());
							sdb.insertUserRate(rank);
							//datas�� �ǽð����� game_Screen���� ����� ��������
							datas = sdb.selectgameset(join.getID());
							//â �ݰ� ���̵� ���� �� ��ŷ���� ȭ������
							dispose();
							new game_Screen(join, datas);
						}
					}
				});
			}
		}
		//���� ��ư ����
		for (int i = 0; i < 9; i++) {
			click[i] = new JButton("" + (i + 1));
			//�гο� �߰�
			Click_Panel.add(click[i]);
			//�̺�Ʈ ó��
			click[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JButton jb = (JButton) e.getSource();
					//���⿬���ڷ� ��ư Ŭ�� �� isEnabled() == �׻� true �̹Ƿ� ��ư�� ������ value�� ����
					value = jb.isEnabled() ? jb.getText() : "0";
				}
			});
		}
		// ����� ��ư ����
		click[9] = new JButton("X");
		//�гο� �߰�
		Click_Panel.add(click[9]);
		//�̺�Ʈ ó��
		click[9].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton jb = (JButton) e.getSource();
				//���⿬���ڷ� ��ư Ŭ�� �� isEnabled() == �׻� true �̹Ƿ� ������ value�� ����
				value = jb.isEnabled() ? "" : "0";
			}
		});
		// �ָ��� ���� üũ�ϱ� ���� ? ��ư ����
		click[10] = new JButton("?");
		//�гο� �߰�
		Click_Panel.add(click[10]);
		//�̺�Ʈ ó��
		click[10].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton jb = (JButton) e.getSource();
				//���⿬���ڷ� ��ư Ŭ�� �� isEnabled() == �׻� true �̹Ƿ� ?�� value�� ����
				value = jb.isEnabled() ? "?" : "0";
			}
		});
		//�ð� �� ������ ����
		timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
		timeLabel.setBounds(100, 100, 200, 100);
		timeLabel.setFont(new Font("���� ���", Font.PLAIN, 35));
		timeLabel.setBorder(BorderFactory.createBevelBorder(1));
		timeLabel.setOpaque(true);
		timeLabel.setHorizontalAlignment(JTextField.CENTER);
		//���ӽ��۹�ư ������ ����
		startButton.setBounds(100, 200, 100, 50);
		startButton.setFont(new Font("���� ���", Font.PLAIN, 20));
		startButton.setFocusable(false);
		startButton.addActionListener(this);
		//�ð��гο� ���ӽ��۹�ư�� �� �߰�
		Time_Panel.add(timeLabel);
		Time_Panel.add(startButton);
		//�г� ���� ����
		Button_Panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		Click_Panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		//�����̳ʿ� �߰�
		ct.add(Time_Panel, BorderLayout.BEFORE_FIRST_LINE);
		ct.add(Button_Panel, BorderLayout.CENTER);
		ct.add(Click_Panel, BorderLayout.EAST);

		setTitle("�α��� ȭ��");
		setSize(700, 700);
		setVisible(true);
		setLocationRelativeTo(null);

	}
	//���۹�ư ������ �� �ð��� �帣�Բ� �ϴ� �޼ҵ�
	void start() {
		timer.start();
	}
	//���ӽ��� �� �������� �� �� �ð� ������Ű�� �޼ҵ�
	void stop() {
		timer.stop();
	}
	//�̺�Ʈ
	@Override
	public void actionPerformed(ActionEvent e) {
		//���ӽ��� ��ư Ŭ�� �� 
		if (e.getSource() == startButton) {
			//started �ʱⰪ false �̰�, ���۹�ư ���� �� true��
			if (started == false) {
				started = true;
				//2���� �迭�� ���� ��������
				int a[][] = sudoku.quiz_printSudoku();
				//��ư�� set
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						//������ ������ ��ư �ϳ��ϳ��� set
						button[i][j].setText("" + a[i][j]);
						//����ߵǴ� �κ��� 0���� �Ǿ������Ƿ� button�� �ְ� ���� Text�� 0�ΰ�쿡 �������� ����
						if (button[i][j].getText().equals("" + 0)) {
							button[i][j].setText("");
							//����ߵǴ� ������ �� ����
							aa++;
							button[i][j].setEnabled(true);
						} 
						//�־����� �� �����Ұ� ����
						else
							button[i][j].setEnabled(false);

					}
				}
				//���ӽ����� ���� �� �ش� ��ư�� ���������� �ؽ�Ʈ ���̰� ����
				startButton.setText("��������");
				//Ÿ�̸� ��ŸƮ
				start();
			} 
			//�������� ��ư Ŭ������ ���
			else if (started) {
				started = false;
				//���� �������� ���� n =0 �ϰ�� ��, 1�ϰ�� �ƴϿ�
				int n = JOptionPane.showConfirmDialog(this, "���� ������ �����Ͻðڽ��ϱ�?", "�������� ����", JOptionPane.YES_NO_OPTION);

				if (n == 1) {
					//����X �� ��� �ð� �ٽ� ����
					start();
					return;
				} else if (n == 0) { // ���� 5��
					//�������� ��� â�ݰ� ���̵� ���� �� ��ŷ���� ȭ������ ����. (datas�� �ǽð����� game_Screen���� ����� ��������.
					dispose();
					datas = sdb.selectgameset(join.getID());
					new game_Screen(join, datas);
				}
				//�ð� ����
				stop();
			}

		}
	}
	//�������� �޼ҵ�
	void gameExit() { // ���� 2��
		JOptionPane.showMessageDialog(this, "Ʋ��Ƚ���� 3�� �ʰ��ϼż� ������ ����˴ϴ�.", "��������", JOptionPane.YES_OPTION);
		//������������ ��� â�ݰ� ���̵� ���� �� ��ŷ���� ȭ������ ����. (datas�� �ǽð����� game_Screen���� ����� ��������.
		datas = sdb.selectgameset(join.getID());
		dispose();
		new game_Screen(join, datas);
	}
}
