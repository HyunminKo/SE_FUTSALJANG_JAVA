package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BelongtoDAO {
	private String UserNo;
	private String TeamNo;
	public String getUserNo() {
		return UserNo;
	}
	public void setUserNo(String userNo) {
		UserNo = userNo;
	}
	public String getTeamNo() {
		return TeamNo;
	}
	public void setTeamNo(String teamNo) {
		TeamNo = teamNo;
	}
	public BelongtoDAO(String userNo, String teamNo) {
		super();
		UserNo = userNo;
		TeamNo = teamNo;
	}
	public BelongtoDAO() {
		super();
	}
	
	public static String getUserTeamNo(String userNo){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from BELONGTO WHERE='"+userNo+"'");
			try {
				if(rs!=null&&rs.next()){
					return rs.getString("TEAMNO");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dao.closeConn();
			}
		}
		return null;
	}
}
