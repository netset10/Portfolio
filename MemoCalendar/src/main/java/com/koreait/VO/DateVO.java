package com.koreait.VO;

public class DateVO {
	String strDate;
	int month;
	int date;
	boolean isThisMonth=true;
	String holiday="";
	String memo="";
	
	public DateVO() {}
	public DateVO(String strDate, int month, int date, boolean isThisMonth, String holiday, String memo) {
		this.strDate = strDate;
		this.month = month;
		this.date = date;
		this.isThisMonth = isThisMonth;
		this.holiday = holiday;
		this.memo = memo;
	}
	

	public String getStrDate() {return strDate;}
	public void setStrDate(String strDate) {this.strDate = strDate;}
	public int getMonth() {return month;}
	public void setMonth(int month) {this.month = month;}
	public int getDate() {return date;}
	public void setDate(int date) {this.date = date;}
	public boolean isIsThisMonth() {return isThisMonth;}
	public void setIsThisMonth(boolean isThisMonth) {this.isThisMonth = isThisMonth;}
	public String getHoliday() {return holiday;}
	public void setHoliday(String holiday) {this.holiday = holiday;}
	public String getMemo() {return memo;}
	public void setMemo(String memo) {this.memo = memo;}
	
	
	@Override
	public String toString() {
		return "DateVO [strDate=" + strDate + "]";
	}
}
