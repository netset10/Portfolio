package com.koreait.DAO;

import java.util.ArrayList;
import java.util.Map;

import com.koreait.VO.CalendarMemoVO;

public interface MyBatisDAO {
	
	void insertMemo(CalendarMemoVO calendarMemoVO);
	void updateMemo(CalendarMemoVO calendarMemoVO);
	void deleteMemo(String strDate);
	int selectMemoCount(String strDate);
	ArrayList<CalendarMemoVO> selectMemos(Map<String, Object> range);
	
}
