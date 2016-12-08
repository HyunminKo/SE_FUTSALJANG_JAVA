package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class UserDAO{
	private String userNo;
	private String userName;
	private String userPhone;
	private String userId;
	private String userPw;
	private String userGrade;
	private int userPoint;
	public UserDAO() {
		super();
	}
	
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}
	public int getUserPoint() {
		return userPoint;
	}
	public void setUserPoint(int userPoint) {
		this.userPoint = userPoint;
	}
	
	public UserDAO(String userId, String userPw) {
		super();
		this.userId = userId;
		this.userPw = userPw;
	}
	public UserDAO(String userName, String userPhone, String userId, String userPw, String userGrade,
			int userPoint) {
		super();
		this.userName = userName;
		this.userPhone = userPhone;
		this.userId = userId;
		this.userPw = userPw;
		this.userGrade = userGrade;
		this.userPoint = userPoint;
	}
	public UserDAO(String userNo, String userName, String userPhone, String userId, String userPw, String userGrade,
			int userPoint) {
		super();
		this.userNo  = userNo;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userId = userId;
		this.userPw = userPw;
		this.userGrade = userGrade;
		this.userPoint = userPoint;
	}

	public boolean join(){
		DAO dao = new DAO();
		ResultSet rs=null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from FUTSALUSER where USERID='"+this.userId+"'");
			try {
				if(rs.next()!=true){
					rs = dao.select(dao.getConn(), "select * from HOST where HOSTID='"+this.userId+"'");
					if(rs.next()!=true){
						dao.insert(dao.getConn(), "INSERT INTO FUTSALUSER (USERNO, USERNAME, USERPHONE, USERID, USERPW, USERGRADE, USERPOINT) VALUES (userSeq.nextval,'"+this.userName+"','"+this.userPhone+"','"+this.userId+"','"+this.userPw+"','"+this.userGrade+"','"+this.userPoint+"')");
						return true;
					}
				}
				else{
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				dao.closeConn();
			}
		}
		
		return false;
	}
	public String login(){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from FUTSALUSER where USERID='"+this.userId+"'");
			try {
				if(rs.next() == true){
					if(rs.getString("USERPW").equals(this.userPw))
						return "success"+":"+rs.getString("USERNO");
					else
						return "incorrect";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				dao.closeConn();
			}
		}
		return "fail";
	}
	public boolean updateMemberInformation(String memberNo,String memberGrade,String modifyPhone){
		DAO dao = new DAO();
		if(dao.createConn()){
			try {
				dao.update(dao.getConn(), "UPDATE FUTSALUSER SET USERGRADE ='"+memberGrade+"', USERPHONE= '"+modifyPhone+"' WHERE USERNO = '"+memberNo+"'");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				dao.closeConn();
			}
		}
		
		return false;
	}
	public void select(List<UserDAO> list){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from FUTSALUSER");
			try {
				while(rs.next()){
					list.add(new UserDAO(rs.getString("USERNO"),rs.getString("USERNAME"),rs.getString("USERPHONE"),rs.getString("USERID"),rs.getString("USERPW"),rs.getString("USERGRADE"),Integer.parseInt(rs.getString("USERPOINT"))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				dao.closeConn();
			}
		}
	}
	public static void selectRank(List<UserDAO> list){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from ( select * from FUTSALUSER WHERE NOT USERNO=0 ORDER BY USERPOINT DESC ) WHERE ROWNUM < 11");
			try {
				while(rs.next()){
					list.add(new UserDAO(rs.getString("USERNO"),rs.getString("USERNAME"),rs.getString("USERPHONE"),rs.getString("USERID"),rs.getString("USERPW"),rs.getString("USERGRADE"),Integer.parseInt(rs.getString("USERPOINT"))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				dao.closeConn();
			}
		}
	}
	public static String getDataNameNPhone(String userNo) {
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from FUTSALUSER where USERNO = '"+userNo+"'");
			try {
				if(rs.next()==true){
					return rs.getString("USERNAME") + ":" + rs.getString("USERPHONE");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static String getUserPoint(String userNo){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from FUTSALUSER where USERNO = '"+userNo+"'");
			try {
				if(rs.next()){
					return rs.getString("USERPOINT");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;		
	}
}