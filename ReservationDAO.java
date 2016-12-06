package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
	private String userNo;
	private String sectionNo;
	private String centerNo;
	private String paymentOption;
	private String bookingDate;
	private String hoursOfUse;
	private String charge;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getSectionNo() {
		return sectionNo;
	}
	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}
	public String getCenterNo() {
		return centerNo;
	}
	public void setCenterNo(String centerNo) {
		this.centerNo = centerNo;
	}
	public String getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getHoursOfUse() {
		return hoursOfUse;
	}
	public void setHoursOfUse(String hoursOfUse) {
		this.hoursOfUse = hoursOfUse;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public ReservationDAO(){
		super();
	}
	public ReservationDAO(String userNo, String sectionNo, String centerNo, String paymentOption, String bookingDate, String hoursOfUse, String charge){
		super();
		this.userNo = userNo;
		this.sectionNo = sectionNo;
		this.centerNo = centerNo;
		this.paymentOption = paymentOption;
		this.bookingDate = bookingDate;
		this.hoursOfUse = hoursOfUse;
		this.charge = charge;
	}
	
	public void select(List<ReservationDAO> list, String No) {
		DAO dao = new DAO();
		ResultSet rs = null;
		ResultSet rs2 = null;
		String data[] = new String[2];
		String charge = null;
		String centerName = null;
		if (No.charAt(0) == 1) {
			if (dao.createConn()) {
				rs = dao.select(dao.getConn(), "select * from RESERVATION_PAY where USERNO ='" + No + "'");
				try {
					while (rs.next()) {
						// 0: centerNo, 1: charge
						data = FutsalcenterDAO.getNameNCharge(rs.getString("CENTERNO")).split(":");
						char secTionNo = (char) ((rs.getInt(2)) + 65);
						list.add(new ReservationDAO(rs.getString("USERNO"), Character.toString(secTionNo), data[0],
								rs.getString("PAYMENTOPTION"), rs.getString("BOOKINGDATE"), rs.getString("HOURSOFUSE"),
								data[1]));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					dao.closeConn();
				}
			}
		}
		else{
			if(dao.createConn()){
				rs = dao.select(dao.getConn(), "select * from RESERVATION_PAY where CENTERNO IN (select CENTERNO from FUTSALCENTER where HOSTNO = '" + No + "')");	
				try{
					while (rs.next()) {
						rs2 = dao.select(dao.getConn(), "select CENTERNAME,CHARGE from FUTSALCENTER where CENTERNO = '" + rs.getString("CENTERNO") + "'");
						if(rs2.next()==true){
							centerName = rs2.getString("CENTERNAME");
							charge = rs2.getString("CHARGE");
							char secTionNo = (char) (rs.getInt(2) + 65);
							data = UserDAO.getDataNameNPhone(rs.getString("USERNO")).split(":");
							list.add(new ReservationDAO(data[0],Character.toString(secTionNo),centerName,data[1],rs.getString("BOOKINGDATE"),rs.getString("HOURSOFUSE"),charge));
						}else{
							
						}
						
					}
				} catch(SQLException e){
					e.printStackTrace();
				} finally {
					dao.closeConn();
				}
			}
		}
	}
}