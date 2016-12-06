package DAO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@168.188.128.130:1521:DB13";
	private static final String USER = "DBHM";
	private static final String PASS = "DBSERVER";
	
	private static Connection conn = null;
	private static DatabaseMetaData meta = null;
	
	public boolean createConn() {
		try {
			Class.forName(DRIVER);
			System.out.println("[*]	JDBC 드라이버 로드 완료.");
			conn = DriverManager.getConnection(URL, USER, PASS);
			System.out.println("[*]	데이터베이스 접속 완료.");
			return true;
		} catch(Exception e) {
			System.out.println("[*]	데이터베이스 접속 오류 발생: \n" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	
	public Connection getConn() {
		return conn;
	}
	public void closeConn(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean insert(Connection conn, String query) {
		try {
			Statement stmt = conn.createStatement();
			int rowCount = stmt.executeUpdate(query);
			if(rowCount == 0) {
				System.out.println("데이터 삽입 실패");
				return false;
			} else {
				System.out.println("데이터 삽입 성공");
			}
		} catch (Exception e) {
			System.out.println("[*]	INSERT 오류 발생: \n" + e.getMessage());
		}
		
		return true;
	}
	
	public ResultSet select(Connection conn, String query) {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsMeta = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rsMeta = rs.getMetaData();
			
			// 질의 결과 메타 정보 추출
			for(int col = 1; col <= rsMeta.getColumnCount(); col++) {
				int type = rsMeta.getColumnType(col);
				String typeName = rsMeta.getColumnTypeName(col);
				String name = rsMeta.getColumnName(col);
				System.out.println(col + "st column " + name + 
						" is JDBC type " + type + " which is called " + typeName);
			}
			
			// 질의 결과 반환
			return rs;
		} catch (Exception e) {
			System.out.println("[*]	SELECT 오류 발생: \n" + e.getMessage());
		}
		
		return rs;
	}
	
	public boolean update(Connection conn, String query) {
		try {
			Statement stmt = conn.createStatement();
			int rowCount = stmt.executeUpdate(query);
			if(rowCount == 0) {
				System.out.println("데이터 수정 실패");
			} else {
				System.out.println("데이터 수정 성공");
			}
		} catch (Exception e) {
			System.out.println("[*]	UPDATE 오류 발생: \n" + e.getMessage());
		}
		
		return true;
	}
	
	public boolean delete(Connection conn, String query) {
		try {
			Statement stmt = conn.createStatement();
			int rowCount = stmt.executeUpdate(query);
			if(rowCount == 0) {
				System.out.println("데이터 삭제 실패");
				return false;
			} else {
				System.out.println("데이터 삭제 성공");
			}
		} catch (Exception e) {
			System.out.println("[*]	DELETE 오류 발생: \n" + e.getMessage());
		}
		
		return true;
	}
	
	public DatabaseMetaData getDBMD(Connection conn) {
		try {
			meta = conn.getMetaData();
		} catch (Exception e) {
			System.out.println("[*]	DBMD 오류 발생: \n" + e.getMessage());
		}
		
		return meta;
	}
}
