import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//DB연결 및 sql
public class Sudoku_DB {
	// 드라이버 설정
	String jdbcDriver = "com.mysql.cj.jdbc.Driver";

	// 데이터베이스 URL 설정
	String url = "jdbc:mysql://localhost:3306/sudoku?";
	Connection conn;

	String id = "root"; // DB에 로그인할 ID
	String pw = "1234"; // MYSQL 설정 시 입력한 PASSWORD

	PreparedStatement pstmt;
	ResultSet rs;

	String sql;

	// DB연결
	public void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// db연결 종료
	public void closeDB() {
		try {
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 회원가입 후 DB에 insert시키는 메소드
	public boolean insertmember(Join join) {
		// DB연결
		connectDB();

		sql = "insert into member values(?,?,?,?)";
		try {
			// sql 문구 실행 => 매개변수 join에 저장되어있는 정보 DB로
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
		// true false로 회원가입에 성공하였는지 오류가 발생하면 false를 리턴함으로써 실패를 알림
		return true;
	}

	// 매개변수 id에 해당하는 정보 가져오는 메소드
	public Join selectmember(String id) {
		Join join = new Join();
		// DB연결
		connectDB();

		sql = "select * from member where ID=?";
		try {
			// sql문 실행 후 해당하는 id에 관한 정보 join클래스에 set
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
		// join, null로 해당하는 id의 정보를 성공적으로 가져오면 저장되어있는 join리턴, 에러 발생 시 해당하는 id X 이므로 null
		// 리턴
		return join;
	}

	// 중복확인 메소드
	public boolean overlap(String id, String str) {
		Join join = null;
		// DB연결
		connectDB();
		// 매개변수 id를 받아 member 테이블에서 찾는다.
		sql = "select * from member where " + str + "=?";
		try {
			// join클래스에 set을 해준다.
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
			// 에러가 뜰 경우 중복이 아니므로 true 리턴시켜줌으로써 아이디 사용가능
			return true;
		} finally {
			closeDB();
		}
		// 에러가 안 뜰 경우 중복이므로 false 리턴
		return false;
	}

	// 게임 클리어 후 DB에 결과 저장 메소드
	public boolean insertUserRate(Rank rank) { // 플레이 하고난 뒤
		connectDB();
		sql = "insert into gameset(level, count, timer, userID) values(?,?,?,?)";
		try {
			// sql문 실행 후 rank에 저장되어 있는 게임 결과 DB로
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
		// 성공적으로 DB로 저장 시 true 리턴 실패 시 false 리턴
		return true;
	}

	// 기록에 보여질 해당하는 id의 기록 가져오기
	public ArrayList<Rank> selectgameset(String id) {
		// DB연결
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
		// 성공적으로 DB에서 rank클래스로 저장 시 ArrayList 리턴 실패시 null 리턴
		return datas;

	}

	// 랭킹보기 클래스로 DB에서 오름차순 정렬 후 가져오기
	public String DeptSortStudent(String s) {
		// 최종적으로 정렬된 값 저장할 변수
		String a = "";
		// 순위
		int n = 1;
		// DB연결
		connectDB();
		// 오름차순 정렬 sql
		sql = "select * from gameset where level=? order by timer;";

		try {
			// sql문 실행 후 해당하는 난이도에 대한 기록 가져오기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 변수에 순위와 시간과 유저아이디 저장
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
		// 성공적으로 기록 받아올 시 랭킹 저장되어있는 변수 a 리턴, 실패 시 공백 리턴
		return a;
	}

	// 아이디 찾기
	public Join searchID(String email) {
		Join join = null;
		connectDB();
		// email을 매개변수로 받아 id찾기
		sql = "select * from member where email=?";
		try {
			// join에 정보 set
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
			// 에러 발생 시 null 리턴
			return null;
		} finally {
			closeDB();
		}
		// join에 set을 한 뒤 리턴
		return join;
	}

	// 비번찾기
	public Join searchPW(String email, String id) {
		Join join = null;
		connectDB();
		// email과 id를 매개변수로 받아 pw찾기
		sql = "select * from member where email=? and ID=?";
		try {
			// join에 정보 set
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

	// 비번변경
	public boolean updatePW(Join join, String pw) {
		// db연결
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
		// 성공 오류시 false리턴 실패
		return true;
	}
}
