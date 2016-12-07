package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeamDAO {
	private String teamNo;
	private String teamName;
	private String teamDescription;
	public String getTeamNo() {
		return teamNo;
	}
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamDescription() {
		return teamDescription;
	}
	public void setTeamDescription(String teamDescription) {
		this.teamDescription = teamDescription;
	}
	public TeamDAO() {
		super();
	}
	public TeamDAO(String teamName, String teamDescription){
		super();
		this.teamName = teamName;
		this.teamDescription = teamDescription;
	}
	
	public TeamDAO(String teamNo, String teamName, String teamDescription){
		super();
		this.teamNo = teamNo;
		this.teamName = teamName;
		this.teamDescription = teamDescription;
	}
	
	public static String getTeamName(String teamNoTwo){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from TEAM where TEAMNO = '"+teamNoTwo+"'");
			try {
				if(rs.next()==true){
					return rs.getString("TEAMNAME");
				}
				else{
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	public boolean enroll(){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from TEAM where teamName = '"+this.teamName+"'");
			try {
				if(rs.next()!=true){
					dao.insert(dao.getConn(), "INSERT INTO TEAM (TEAMNO, TEAMNAME, DESCRIPTION) VALUES (teamSeq.nextval,'"+this.teamName+"','"+this.teamDescription+"')");
					return true;
				}
				else
					return false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public void select(List<TeamDAO> list){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from TEAM");
			try {
				while(rs.next()){
					list.add(new TeamDAO(rs.getString(1),rs.getString(2),rs.getString(3)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				dao.closeConn();
			}
		}
	}
	
}
