package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MatchDAO {
	private String teamNoOne;
	private String teamNoTwo;
	private String matchDate;
	public String getTeamNoOne() {
		return teamNoOne;
	}
	public void setTeamNoOne(String teamNoOne) {
		this.teamNoOne = teamNoOne;
	}
	public String getTeamNoTwo() {
		return teamNoTwo;
	}
	public void setTeamNoTwo(String teamNoTwo) {
		this.teamNoTwo = teamNoTwo;
	}
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	public MatchDAO(String teamNoOne, String teamNoTwo, String matchDate) {
		super();
		this.teamNoOne = teamNoOne;
		this.teamNoTwo = teamNoTwo;
		this.matchDate = matchDate;
	}
	public MatchDAO() {
		super();
	}
	public void select(List<MatchDAO> list, String teamNo){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from MATCH");
			try {
				while(rs.next()){
					String one = rs.getString("TEAMNOONE");
					String two = rs.getString("TEAMNOTWO");
					String data[] = rs.getString("MATCHDATE").split(":");
					if(one.equals(teamNo) || two.equals(teamNo)){
						if(one.equals(teamNo))
							list.add(new MatchDAO(one,two,data[0]+"-"+data[1]+"-"+data[2]));							
						else
							list.add(new MatchDAO(two,one,data[0]+"-"+data[1]+"-"+data[2]));
					}
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
