package com.koreait.VO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.koreait.MemoCalendar.MyCalendar;

public class PageVO {
	private int numRows=0;
	private ArrayList<DateVO> dates=new ArrayList<DateVO>();
	ArrayList<CalendarMemoVO> memolist=new ArrayList<CalendarMemoVO>();
	
	public PageVO() {}
	public PageVO(int year, int month, ArrayList<CalendarMemoVO> memolist) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String firstDate=MyCalendar.firstDayOnCalendar(year, month);
		String lastDate =MyCalendar.lastDayOnCalendar(year, month);
		
		numRows=(MyCalendar.weekDay(year, month, 1)+MyCalendar.lastDay(year, month)+6)/7;
		
		
		
		// 날짜별 공휴일, 대체공휴일, 메모 작성
		String today=firstDate;
		boolean substitute=false;
		int memoidx=0;
		int memocnt=memolist.size();
		while(true) {
			int y=Integer.parseInt(today.substring(0, 4));
			int m=Integer.parseInt(today.substring(4, 6));
			int d=Integer.parseInt(today.substring(6, 8));
			boolean isThisMonth= m==month;
			String holiday="";
			String memo="";
			
			
			// 공휴일 목록
			boolean sub_able=false;
			if     (m==1&&d==1)   {holiday="신정";}
			else if(m==3&&d==1)   {holiday="삼일절";  sub_able=true;}
			else if(m==5&&d==1)   {holiday="근로자의날";}
			else if(m==5&&d==5)   {holiday="어린이날"; sub_able=true;}
			else if(m==6&&d==6)   {holiday="현충일";}
			else if(m==8&&d==15)  {holiday="광복절";  sub_able=true;}
			else if(m==10&&d==3)  {holiday="개천절";  sub_able=true;}
			else if(m==10&&d==9)  {holiday="한글날";  sub_able=true;}
			else if(m==12&&d==25) {holiday="크리스마스";}
			
			// 대체공휴일은 특정 공휴일이 토, 일 혹은 다른 공휴일과 겹칠 때 다음 평일에 지정한다.
			// 단, 음력은 이 달력에서 구현하지 않는다. (즉 부처님오신날, 추석, 설날은 제외됨)
			if(holiday.length()>0 && MyCalendar.weekDay(year, m, d)%6==0 && sub_able) 
				substitute=true;
			if(holiday.length()==0 && MyCalendar.weekDay(year, m, d)%6!=0 && substitute) {
				holiday="대체공휴일";
				substitute=false;
			}
			
			// DB에서 꺼내온 메모 적용하기
			if(memoidx<memocnt && memolist.get(memoidx).getStrDate().equals(today)) {
				memo=memolist.get(memoidx).getMemo();
				memoidx++;
			}
			
			
			DateVO dateVO=new DateVO(today, m, d, isThisMonth, holiday, memo);
			this.dates.add(dateVO);
			
			if(today.equals(lastDate)) break;
			today=MyCalendar.nextDay(today);
		}
	}
	
	
	public int getNumRows() {return numRows;}
	public void setNumRows(int numRows) {this.numRows = numRows;}
	public ArrayList<DateVO> getDates() {return dates;}
	public void setDates(ArrayList<DateVO> dates) {this.dates = dates;}
}
