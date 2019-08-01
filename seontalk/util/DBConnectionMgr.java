package seontalk.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionMgr {
	static final String _DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String _URL 	= "jdbc:oracle:thin:@192.168.0.189:1521:orcl11";
	static final String _USER 	= "test";
	static final String _PW 	= "abcd1234";
	static DBConnectionMgr dbMgr = null;
	public static DBConnectionMgr getInstance() {
		if(dbMgr==null) {
			dbMgr = new DBConnectionMgr();
		}
		return dbMgr;
	}
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(_DRIVER);
			con = DriverManager.getConnection(_URL, _USER, _PW);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스를 찾을 수 없다.");
		} catch(Exception e) {
			System.out.println("오라클 서버 연결 실패");
		}
		return con;
	}
}
