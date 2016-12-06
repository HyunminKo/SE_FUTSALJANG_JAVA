package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FutsalcenterDAO {
	private String hostNo;
	private String centerName;
	private String city;
	private String ku;
	private String detailAddress;
	private String charge;
	private String sectionNum;
	private String centerPhone;
	private String imgUrl;

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
	public FutsalcenterDAO() {
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
}
