package com.koreait.MemoCalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.DAO.MyBatisDAO;
import com.koreait.VO.CalendarMemoVO;
import com.koreait.VO.PageVO;

@Controller
public class HomeController {
	@Autowired
	public SqlSession sqlSession;
	
	@RequestMapping("/")
	public String main(HttpServletRequest request, Model model) {
		MyBatisDAO mapper= sqlSession.getMapper(MyBatisDAO.class);
		
		AbstractApplicationContext ctx 
		= new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		Date date=ctx.getBean("date", Date.class);
		
		
		int year= date.getYear()+1900;
		int month=date.getMonth()+1;
		model.addAttribute("realyear", year);
		model.addAttribute("realmonth", month);
		
		try{// 넘어온 날짜 정보가 있으면 실행됨
			year= Integer.parseInt(request.getParameter("year"));
			month=Integer.parseInt(request.getParameter("month"));
		}catch(Exception e){}
		
		// 이상치 처리
		if    (month>=13) {year++; month=1;}
		else if(month<=0) {year--; month=12;}
		
		
		// 페이지에 들어갈 메모들 불러오기
		String startDate=MyCalendar.firstDayOnCalendar(year, month);
		String endDate  =MyCalendar.lastDayOnCalendar(year, month);
		Map<String, Object> range=new HashMap<String, Object>();
		range.put("startDate", startDate);
		range.put("endDate", endDate);
		ArrayList<CalendarMemoVO> memolist= mapper.selectMemos(range);
		
		
		PageVO pageVO=new PageVO(year, month, memolist);
		
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("pageVO", pageVO);
		return "calendar";
	}
	
	
	@RequestMapping("/insert")
	@ResponseBody
	public void ajax(HttpServletRequest request, Model model, CalendarMemoVO vo) {
		MyBatisDAO mapper= sqlSession.getMapper(MyBatisDAO.class);
		
		String strDate=vo.getStrDate();
		String memo=   vo.getMemo();
		
		int count=mapper.selectMemoCount(strDate);
		if     (count==0 && memo.length()>0)
			mapper.insertMemo(vo);
		else if(count==1 && memo.length()>0)
			mapper.updateMemo(vo);
		else if(count==1 && memo.length()==0)
			mapper.deleteMemo(strDate);
		
	}
	
}
