import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//DB���� �� sql
public class Sudoku_DB {
	// ����̹� ����
	String jdbcDriver = "com.mysql.cj.jdbc.Driver";

	// �����ͺ��̽� URL ����
	String url = "jdbc:mysql://localhost:3306/sudoku?";
	Connection conn;

	String id = "root"; // DB�� �α����� ID
	String pw = "1234"; // MYSQL ���� �� �Է��� PASSWORD

	PreparedStatement pstmt;
	ResultSet rs;

	String sql;

	// DB����
	public void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// db���� ����
	public void closeDB() {
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ȸ������ �� DB�� insert��Ű�� �޼ҵ�
	public boolean insertmember(Join join) {
		// DB����
		connectDB();

		sql = "insert into member values(?,?,?,?)";
		try {
			// sql ���� ���� => �Ű����� join�� ����Ǿ��ִ� ���� DB��
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, join.getID());
			pstmt.setString(2, join.getPW());
			pstmt.setString(3, join.getName());
			pstmt.setString(4, join.getEmail());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
		// true false�� ȸ�����Կ� �����Ͽ����� ������ �߻��ϸ� false�� ���������ν� ���и� �˸�
		return true;
	}

	// �Ű����� id�� �ش��ϴ� ���� �������� �޼ҵ�
	public Join selectmember(String id) {
		Join join = new Join();
		// DB����
		connectDB();

		sql = "select * from member where ID=?";
		try {
			// sql�� ���� �� �ش��ϴ� id�� ���� ���� joinŬ������ set
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			join.setID(rs.getString("ID"));
			join.setPW(rs.getString("PW"));
			join.setName(rs.getString("name"));
			join.setEmail(rs.getString("email"));

		} catch (SQLException e) {
			return null;
		} finally {
			closeDB();
		}
		// join, null�� �ش��ϴ� id�� ������ ���������� �������� ����Ǿ��ִ� join����, ���� �߻� �� �ش��ϴ� id X �̹Ƿ� null
		// ����
		return join;
	}

	// �ߺ�Ȯ�� �޼ҵ�
	public boolean overlap(String id, String str) {
		Join join = null;
		// DB����
		connectDB();
		// �Ű����� id�� �޾� member ���̺��� ã�´�.
		sql = "select * from member where " + str + "=?";
		try {
			// joinŬ������ set�� ���ش�.
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			join = new Join();
			join.setID(rs.getString("ID"));
			join.setPW(rs.getString("PW"));
			join.setName(rs.getString("name"));
			join.setEmail(rs.getString("email"));

		} catch (SQLException e) {
			// ������ �� ��� �ߺ��� �ƴϹǷ� true ���Ͻ��������ν� ���̵� ��밡��
			return true;
		} finally {
			closeDB();
		}
		// ������ �� �� ��� �ߺ��̹Ƿ� false ����
		return false;
	}

	// ���� Ŭ���� �� DB�� ��� ���� �޼ҵ�
	public boolean insertUserRate(Rank rank) { // �÷��� �ϰ� ��
		connectDB();
		sql = "insert into gameset(level, count, timer, userID) values(?,?,?,?)";
		try {
			// sql�� ���� �� rank�� ����Ǿ� �ִ� ���� ��� DB��
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rank.getLevel());
			pstmt.setString(2, rank.getCount());
			pstmt.setString(3, rank.getTimer());
			pstmt.setString(4, rank.getUserID());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
		// ���������� DB�� ���� �� true ���� ���� �� false ����
		return true;
	}

	// ��Ͽ� ������ �ش��ϴ� id�� ��� ��������
	public ArrayList<Rank> selectgameset(String id) {
		// DB����
		connectDB();

		sql = "select * from gameset where userID=?";

		ArrayList<Rank> datas = new ArrayList<Rank>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Rank r = new Rank();
				r.setLevel(rs.getString("level"));
				r.setCount(rs.getString("count"));
				r.setTimer(rs.getString("timer"));
				r.setUserID(rs.getString("userID"));
				datas.add(r);
			}
		} catch (SQLException e) {
			return null;
		} finally {
			closeDB();
		}
		// ���������� DB���� rankŬ������ ���� �� ArrayList ���� ���н� null ����
		return datas;

	}

	// ��ŷ���� Ŭ������ DB���� �������� ���� �� ��������
	public String DeptSortStudent(String s) {
		// ���������� ���ĵ� �� ������ ����
		String a = "";
		// ����
		int n = 1;
		// DB����
		connectDB();
		// �������� ���� sql
		sql = "select * from gameset where level=? order by timer;";

		try {
			// sql�� ���� �� �ش��ϴ� ���̵��� ���� ��� ��������
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// ������ ������ �ð��� �������̵� ����
				a += n++ + "\t";
				String timer = rs.getString("timer") + "\t";
				String userID = rs.getString("userID") + "\n";
				a += timer + userID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			closeDB();
		}
		// ���������� ��� �޾ƿ� �� ��ŷ ����Ǿ��ִ� ���� a ����, ���� �� ���� ����
		return a;
	}

	// ���̵� ã��
	public Join searchID(String email) {
		Join join = null;
		connectDB();
		// email�� �Ű������� �޾� idã��
		sql = "select * from member where email=?";
		try {
			// join�� ���� set
			join = new Join();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			rs.next();
			join.setID(rs.getString("ID"));
			join.setPW(rs.getString("PW"));
			join.setName(rs.getString("name"));
			join.setEmail(rs.getString("email"));

		} catch (SQLException e) {
			// ���� �߻� �� null ����
			return null;
		} finally {
			closeDB();
		}
		// join�� set�� �� �� ����
		return join;
	}

	// ���ã��
	public Join searchPW(String email, String id) {
		Join join = null;
		connectDB();
		// email�� id�� �Ű������� �޾� pwã��
		sql = "select * from member where email=? and ID=?";
		try {
			// join�� ���� set
			join = new Join();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			rs = pstmt.executeQuery();
			rs.next();
			join.setID(rs.getString("ID"));
			join.setPW(rs.getString("PW"));
			join.setName(rs.getString("name"));
			join.setEmail(rs.getString("email"));

		} catch (SQLException e) {
			return null;
		} finally {
			closeDB();
		}
		return join;
	}

	// �������
	public boolean updatePW(Join join, String pw) {
		// db����
		connectDB();
		sql = "update member set PW=? where ID=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pw);
			pstmt.setString(2, join.getID());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDB();
		}
		// ���� ������ false���� ����
		return true;
	}
}
