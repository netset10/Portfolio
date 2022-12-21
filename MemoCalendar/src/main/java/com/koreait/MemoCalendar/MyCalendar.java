package com.koreait.MemoCalendar;

public class MyCalendar {
	
	// 윤년일 때 true 반환
	public static boolean isLeapYear(int year) {
		return year%4==0 && year%100!=0 || year%400==0;
	}
	
	// 해당 년, 월의 마지막 날 (=일 수)
	public static int lastDay(int year, int month) {
		int[] ld = {0,31,28,31,30,31,30,31,31,30,31,30,31};
		ld[2] += (isLeapYear(year)? 1 : 0);
		return ld[month];
	}
	
	// 1년 1월 1일부터 몇번째 날인지 계산 후 반환
	public static int totalDay(int year, int month, int date) {
		int year_1=year-1;
		int total= 365*year_1+year_1/4-year_1/100+year_1/400;
		for(int i=1; i<month; i++)
			total+=lastDay(year, i);
		total+=date;
		
		return total;
	}
	
	// 일:0 ~ 토:6
	public static int weekDay(int year, int month, int date) {
		return totalDay(year, month, date)%7;
	}
	
	
	// 달력에 표시되는 첫 날짜 반환. 8글자 문자열 형태.
	public static String firstDayOnCalendar(int year, int month) {
		int y=year, m=month, d=1;
		int weekday=weekDay(y, m, 1);
		
		if(weekday!=0) {
			m--;
			if(m==0) {y--; m=12;}
			d=lastDay(y,m)-weekday+1;
		}
		return String.format("%04d%02d%02d", y, m, d);
	}	
	// 달력에 표시되는 끝 날짜 반환. 8글자 문자열 형태.
	public static String lastDayOnCalendar(int year, int month) {
		int y=year, m=month, d=lastDay(year, month);
		int weekday=weekDay(y, m, d);
		
		if(weekday!=6) {
			m++;
			if(m==13) {y++; m=1;}
			d=6-weekday;
		}
		return String.format("%04d%02d%02d", y, m, d);
	}	
	
	// 다음 날 반환. 8글자 문자열 형태.
	public static String nextDay(String today) {
		int y=Integer.parseInt(today.substring(0, 4));
		int m=Integer.parseInt(today.substring(4, 6));
		int d=Integer.parseInt(today.substring(6, 8));

		d++;
		if(lastDay(y, m)<d) {
			m++; d=1;
			if(m>12) {
				y++; m=1;
			}
		}
		return String.format("%04d%02d%02d", y, m, d);
	}
}
