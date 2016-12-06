package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HostDAO{
	private String hostNo;
	private String hostName;
	private String hostPhone;
	private String hostId;
	private String hostPw;
	
	public String getHostNo() {
		return hostNo;
	}
	public void setHostNo(String hostNo) {
		this.hostNo = hostNo;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostPhone() {
		return hostPhone;
	}
	public void setHostPhone(String hostPhone) {
		this.hostPhone = hostPhone;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}
	public String getHostPw() {
		return hostPw;
	}
	public void setHostPw(String hostPw) {
		this.hostPw = hostPw;
	}
	
	public HostDAO() {
		super();
	}
	public HostDAO(String hostId, String hostPw) {
		super();
		this.hostId = hostId;
		this.hostPw = hostPw;
	}
	public HostDAO(String hostName, String hostPhone, String hostId, String hostPw) {
		super();
		this.hostName = hostName;
		this.hostPhone = hostPhone;
		this.hostId = hostId;
		this.hostPw = hostPw;
	}
	public HostDAO(String hostNo, String hostName, String hostPhone, String hostId, String hostPw) {
		super();
		this.hostNo = hostNo;
		this.hostName = hostName;
		this.hostPhone = hostPhone;
		this.hostId = hostId;
		this.hostPw = hostPw;
	}
	public boolean join(){
		DAO dao = new DAO();
		ResultSet rs=null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from HOST where HOSTID='"+this.hostId+"'");
			try {
				if(rs.next()!=true){
					dao.insert(dao.getConn(), "INSERT INTO HOST (HOSTNO, HOSTNAME, HOSTPHONE, HOSTID, HOSTPW) VALUES (hostSeq.nextval,'"+this.hostName+"','"+this.hostPhone+"','"+this.hostId+"','"+this.hostPw+"')");
					return true;
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
			rs = dao.select(dao.getConn(), "select * from HOST where HOSTID='"+this.hostId+"'");
			try {
				if(rs.next() == true){
					if(rs.getString("HOSTPW").equals(this.hostPw))
						return "success"+":"+rs.getString("HOSTNO");
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
	public boolean updateMemberInformation(String memberNo,String modifyPhone){
		DAO dao = new DAO();
		if(dao.createConn()){
			try {
				dao.update(dao.getConn(), "UPDATE HOST SET HOSTPHONE ='"+modifyPhone+"' WHERE HOSTNO = '"+memberNo+"'");
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
	public void select(List<HostDAO> list){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from HOST");
			try {
				while(rs.next()){
					list.add(new HostDAO(rs.getString("HOSTNO"),rs.getString("HOSTNAME"),rs.getString("HOSTPHONE"),rs.getString("HOSTID"),rs.getString("HOSTPW")));
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
