package com.koreait.VO;

public class CalendarMemoVO {
	String strDate="";
	String memo;
	
	public CalendarMemoVO() {}
	public CalendarMemoVO(String strDate, String memo) {
		this.strDate = strDate;
		this.memo = memo;
	}
	
	
	public String getStrDate() {return strDate;}
	public void setStrDate(String strDate) {this.strDate = strDate;}
	public String getMemo() {return memo;}
	public void setMemo(String memo) {this.memo = memo;}
}
