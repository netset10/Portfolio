<%@page import="com.koreait.MemoCalendar.MyCalendar"%>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>메모 달력</title>
	<link rel="stylesheet" href="resources/css/calendar.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src='resources/script/calendar.js'></script>
</head>

<body>
	<div id="popup">
		<div id="panel">
			<textarea id="writeplace" rows=20 cols=50 style="resize: none;"
				placeholder="메모를 입력하세요."></textarea>
			<button id="write" type="button" style="height: 20%">기록하기</button>
		</div>
	</div>
	<div id="hidden" style="display: none;"></div>
	
	<c:set var="year" value="${year}"/>
	<c:set var="month" value="${month}"/>
	<table width="700" align="center" border="1" cellpadding="5" cellspacing="1">
		<tr>
			<!-- 달력의 첫 줄. 이전달 버튼, 현재 년월, 다음달 버튼 -->
			<th> <button type="button" 
				onclick="location.href='?year=${year}&month=${month-1}'">이전달</button></th>
			<th id="title" colspan="5">${year}년 ${month}월</th>
			<th> <button type="button" 
				onclick="location.href='?year=${year}&month=${month+1}'">다음달</button></th>
		</tr>
		
		<tr class="day">
			<td class="sunday"  >일</td>
			<td class="etcday"  >월</td>
			<td class="etcday"  >화</td>
			<td class="etcday"  >수</td>
			<td class="etcday"  >목</td>
			<td class="etcday"  >금</td>
			<td class="saturday">토</td>
		</tr>
		
		
		<c:forEach var="row" begin="0" end="${pageVO.numRows-1}">
			<tr class="date">
			    <c:forEach var="i" begin="0" end="6">
			    	<!-- 이번달인가 -->
			    	<c:if test="${pageVO.dates[i+7*row].isThisMonth==true}">
			    		<c:set var="cls1" value=""/>
			    		<c:set var="otherMonth" value=""/></c:if>
			    	<c:if test="${pageVO.dates[i+7*row].isThisMonth==false}">
			    		<c:set var="cls1" value="notThisMonth"/>
						<c:set var="otherMonth" value="${pageVO.dates[i+7*row].month}/"/>
			    	</c:if>
			    	
			    	<!-- 무슨 날인가 -->
			    	<c:if test="${i==0}">
			    		<c:set var="cls2" value="sun"/></c:if>
			    	<c:if test="${i==6}">
			    		<c:set var="cls2" value="sat"/></c:if>
			    	<c:if test="${i!=0 && i!=6}">
			    		<c:set var="cls2" value="etc"/></c:if>
			    	<c:set var="thisDate" value="${pageVO.dates[i+7*row].date}"/>
			    	
			    	<c:set var="hol" value="${pageVO.dates[i+7*row].holiday}"/>
			    	<c:if test="${fn:length(hol)>0}">
			    		<c:set var="cls2" value="sun"/>
			    	</c:if>
			    	
			    	<!-- 메모가 있는가 -->
			    	<c:set var="memo" value="${pageVO.dates[i+7*row].memo}"/>
			    	<c:set var="sign" value=""/>
			    	<c:if test="${fn:length(memo)>0}">
			    		<c:set var="sign" value="${memo}"/></c:if>
			    	<c:if test="${fn:length(memo)>7}">
			    		<c:set var="sign" value="..."/></c:if>

			    	
			    	<!-- 출력 -->
					<td class="${cls1} ${cls2}" id="${pageVO.dates[i+7*row].strDate}"
						onmouseover="over(this.id)" 
						onmouseout="out()"
						onclick="dateclick(id)">
						${otherMonth}${thisDate}<br>
						<span style="font-size: 8;">
							${hol}<br>
							<span style="color: orange;">${sign}</span>
						</span>
						<div style="display: none;">${memo}</div>
					</td>
				</c:forEach>
			</tr>
		</c:forEach>
		
		<!-- 메모 출력부 -->
		<tr>
			<td colspan="7">
				<div id="memodiv"></div>
			</td>
		</tr>
		
		
		<!-- 년,월 선택 후 [보기]를 클릭하면 특정 페이지로 한번에 넘어감 -->
		<tr>
			<td colspan="7">
				<form action="?" method="post">
					<!-- 년 option -->
					<select class="select" name="year">
						<c:forEach var="i" begin="1900" end="3000">
							<c:if test="${i == realyear}">
								<option selected='selected'>${i}</option></c:if>
							<c:if test="${i != realyear}">
								<option>${i}</option></c:if>						
						</c:forEach>
					</select>년
					
					<!-- 월 option -->
					<select class="select" name="month">
						<c:forEach var="i" begin="1" end="12">
							<c:if test="${i == realmonth}">
								<option selected='selected'>${i}</option></c:if>
							<c:if test="${i != realmonth}">
								<option>${i}</option></c:if>						
						</c:forEach>
					</select>월
					
					<!-- [보기] 버튼 -->
					<input class="select" type="submit" value="보기">
				</form>
			</td>
		</tr>
	</table>
</body>
</html>
