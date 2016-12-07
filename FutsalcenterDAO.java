package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FutsalcenterDAO {
	private String centerNo;
	private String hostNo;
	private String centerName;
	private String city;
	private String ku;
	private String detailAddress;
	private String charge;
	private String sectionNum;
	private String centerPhone;
	private String imgUrl;
	
	
	public String getCenterNo() {
		return centerNo;
	}
	public void setCenterNo(String centerNo) {
		this.centerNo = centerNo;
	}
	public String getHostNo() {
		return hostNo;
	}
	public void setHostNo(String hostNo) {
		this.hostNo = hostNo;
	}
	public String getCenterName() {
		return centerName;
	}
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getKu() {
		return ku;
	}
	public void setKu(String ku) {
		this.ku = ku;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getSectionNum() {
		return sectionNum;
	}
	public void setSectionNum(String sectionNum) {
		this.sectionNum = sectionNum;
	}
	public String getCenterPhone() {
		return centerPhone;
	}
	public void setCenterPhone(String centerPhone) {
		this.centerPhone = centerPhone;
	}
	
	public static String getNameNCharge(String centerNo){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from FUTSALCENTER where CENTERNO = '"+centerNo+"'");
			try {
				if(rs.next()==true){
					return rs.getString("CENTERNAME") + ":" + rs.getString("CHARGE");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public FutsalcenterDAO(String centerNo, String centerName, String detailAddress, String charge, String sectionNum,
			String centerPhone) {
		super();
		this.centerNo = centerNo;
		this.centerName = centerName;
		this.detailAddress = detailAddress;
		this.charge = charge;
		this.sectionNum = sectionNum;
		this.centerPhone = centerPhone;
	}
	public FutsalcenterDAO(String hostNo, String centerName, String city, String ku, String detailAddress,
			String charge, String sectionNum, String centerPhone, String imgUrl) {
		super();
		this.hostNo = hostNo;
		this.centerName = centerName;
		this.city = city;
		this.ku = ku;
		this.detailAddress = detailAddress;
		this.charge = charge;
		this.sectionNum = sectionNum;
		this.centerPhone = centerPhone;
		this.imgUrl = imgUrl;
	}
	
	public FutsalcenterDAO(String centerNo, String hostNo, String centerName, String city, String ku,
			String detailAddress, String charge, String sectionNum, String centerPhone, String imgUrl) {
		super();
		this.centerNo = centerNo;
		this.hostNo = hostNo;
		this.centerName = centerName;
		this.city = city;
		this.ku = ku;
		this.detailAddress = detailAddress;
		this.charge = charge;
		this.sectionNum = sectionNum;
		this.centerPhone = centerPhone;
		this.imgUrl = imgUrl;
	}
	
	public FutsalcenterDAO() {
		super();
	}
	public FutsalcenterDAO(String centerNo) {
		super();
		this.centerNo = centerNo;
	}
	public static void getMyCenter(List<FutsalcenterDAO> list,String hostNo){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from FUTSALCENTER where HOSTNO = '"+hostNo+"'");
			try {
				while(rs.next()){
					list.add(new FutsalcenterDAO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
							rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				dao.closeConn();
			}
		}
	}
	public boolean enroll(){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from FUTSALCENTER where centerName = '"+this.centerName+"'");
			try {
				if(rs.next()!=true){
					dao.insert(dao.getConn(), "INSERT INTO FUTSALCENTER (CENTERNO, HOSTNO, CENTERNAME, CITY, KU,DETAILADDRESS, CHARGE, SECTIONNUM, CENTERPHONE,IMGURL) VALUES (centerSeq.nextval,'"+this.hostNo+"','"+this.centerName+"','"+this.city+"','"+this.ku+"','"+this.detailAddress+"','"+this.charge+"','"+this.sectionNum+"','"+this.centerPhone+"','"+this.imgUrl+"')");
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
	public void select(List<FutsalcenterDAO> list){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from FUTSALCENTER");
			try {
				while(rs.next()){
					list.add(new FutsalcenterDAO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
							rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				dao.closeConn();
			}
		}
	}
	
	public boolean delete(String centerNo){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			dao.delete(dao.getConn(), "delete from FUTSALCENTER where CENTERNO = '"+centerNo+"'");
			return true;
		}
		return false;
	}
	
	public boolean update(String centerNo){
		DAO dao = new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			dao.update(dao.getConn(), "UPDATE FUTSALCENTER SET CENTERNAME ='"+this.centerName+"', CHARGE ='"+this.charge+"', SECTIONNUM ='"+this.sectionNum+"', CENTERPHONE='"+this.centerPhone+"', DETAILADDRESS='"+this.detailAddress+"' where CENTERNO ='"+centerNo+"'");
			return true;
		}
		return false;
	}
	
}

