package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	public ReservationDAO(String hoursOfUse){
		super();
		this.hoursOfUse = hoursOfUse;
	}
	
	public ReservationDAO(String userNo, String sectionNo, String centerNo, String paymentOption, String bookingDate,
			String hoursOfUse) {
		super();
		this.userNo = userNo;
		this.sectionNo = sectionNo;
		this.centerNo = centerNo;
		this.paymentOption = paymentOption;
		this.bookingDate = bookingDate;
		this.hoursOfUse = hoursOfUse;
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

	public ReservationDAO(String sectionNo, String bookingDate, String hoursOfUse) {
		super();
		this.sectionNo = sectionNo;
		this.bookingDate = bookingDate;
		this.hoursOfUse = hoursOfUse;
	}
	public boolean insert(String userNo) {
		DAO dao = new DAO();
		ResultSet rs = null;
		if (dao.createConn()) {
			rs = dao.select(dao.getConn(), "select * from RESERVATION_PAY where BOOKINGDATE ='"+this.bookingDate+"' AND HOURSOFUSE ='" + this.hoursOfUse+"'");
			try {
				if (rs.next() != true) {
					dao.insert(dao.getConn(),
							"INSERT INTO RESERVATION_PAY (USERNO, SECTIONNO, CENTERNO, PAYMENTOPTION, BOOKINGDATE, HOURSOFUSE) VALUES ('"+this.userNo+"', '"
									+ this.sectionNo + "','" + this.centerNo+ "','" + this.paymentOption + "','" + this.bookingDate + "','" + this.hoursOfUse
									+ "')");
					dao.insert(dao.getConn(), "update FUTSALUSER set USERPOINT=USERPOINT+1000 where USERNO = '"+userNo+"'");
					return true;
				} else {
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dao.closeConn();
			}
		}
		return false;
	}

	public void select(List<ReservationDAO> list,List<ReservationDAO> realList, String No) {
		DAO dao = new DAO();
		ResultSet rs = null;
		ResultSet rs2 = null;
		String data[] = new String[2];
		String charge = null;
		String centerName = null;
		if (No.startsWith("1")) {
			if (dao.createConn()) {
				rs = dao.select(dao.getConn(), "select * from RESERVATION_PAY where USERNO ='" + No + "'");
				try {
					while (rs.next()) {
						// 0: centerNo, 1: charge
						data = FutsalcenterDAO.getNameNCharge(rs.getString("CENTERNO")).split(":");
						char secTionNo = (char) ((rs.getInt(2)) + 65);
						list.add(new ReservationDAO(rs.getString("USERNO"), Character.toString(secTionNo), data[0],
								rs.getString("PAYMENTOPTION"), rs.getString("BOOKINGDATE").replace(":", "-"), rs.getString("HOURSOFUSE"),
								data[1]));
						realList.add(new ReservationDAO(rs.getString("USERNO"), rs.getString(2), rs.getString(3),
								rs.getString("PAYMENTOPTION"), rs.getString("BOOKINGDATE").replace(":", "-"), rs.getString("HOURSOFUSE"),
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
	
	public static boolean delete(String userNo, String centerNo, String sectionNo, String hours){
		DAO dao = new DAO();
		if(dao.createConn()){
			if(dao.delete(dao.getConn(), "delete from RESERVATION_PAY where USERNO = "+userNo+" AND CENTERNO='"+centerNo+"' AND SECTIONNO='"+sectionNo+"' AND HOURSOFUSE='"+hours+"'")){
				return true;
			}
			else 
				return false;
		}
		
		return false;
	}
	
	public static void selectRsvData(List<ReservationDAO> list, String centerNo){
		DAO dao= new DAO();
		ResultSet rs = null;
		if(dao.createConn()){
			rs = dao.select(dao.getConn(), "select * from RESERVATION_PAY where CENTERNO='"+centerNo+"'");
			try{
				while (rs.next()) {
						list.add(new ReservationDAO(rs.getString(2),rs.getString(5),rs.getString(6)));
				}
			} catch(SQLException e){
				e.printStackTrace();
			} finally {
				dao.closeConn();
			}
		}
		
	}
}