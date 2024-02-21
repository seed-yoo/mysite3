package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;

@WebServlet("/guestbook")
public class GuestbookController extends HttpServlet {
	// 필드
	private static final long serialVersionUID = 1L;

	// 생성자 - 없음

	// 메소드 - getter/setter

	// 메소드 - 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("GuestbookController.goGet()");

		String action = request.getParameter("action");
		System.out.println(action);

		if ("addform".equals(action)) {
			System.out.println("addform:등록폼");

			System.out.println("list:리스트");

			// db사용
			GuestbookDao guestbookDao = new GuestbookDao();

			// 리스트 가져오기
			List<PersonVo> personList = guestbookDao.personSelect();
//			System.out.println(personList);

			// 데이터담기 forward
			request.setAttribute("personList", personList);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		} else if ("insert".equals(action)) {
			System.out.println("insert:등록");

			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			// vo로 묶기
			PersonVo personVo = new PersonVo(name, password, content);
			System.out.println(personVo.toString());

			// db관련 업무
			GuestbookDao phoneDao = new GuestbookDao();

			// db에 저장
			phoneDao.personInsert(personVo);

			WebUtil.redirect(request, response, "/mysite3/guestbook?action=addform");

			/*
			 * // db에서 전체 데이터 가져오기 List<PersonVo> personList = phoneDao.personSelect(); //
			 * System.out.println(personList);
			 * 
			 * // request에 담기 request.setAttribute("personList", personList);
			 * 
			 * // forward // jsp 한테 html 그리기 응답해라 ==> 포워드 RequestDispatcher rd =
			 * request.getRequestDispatcher("/list.jsp"); rd.forward(request, response);
			 */

		} else if ("delete".equals(action)) {
			System.out.println("delete:삭제");
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			System.out.println(no);
			System.out.println(password);
			
			// vo로 묶기
			PersonVo personVo = new PersonVo(no, password);
			System.out.println(personVo.toString());

			// db관련 업무
			GuestbookDao phoneDao = new GuestbookDao();

			// db에 저장
			phoneDao.personDelete(no, password);

			WebUtil.redirect(request, response, "/mysite3/guestbook?action=addform");

		} else if ("dform".equals(action)) {
			System.out.println("dform:삭제폼");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			System.out.println(no);

			// db사용
			GuestbookDao phoneDao = new GuestbookDao();

			// 리스트 가져오기
			List<PersonVo> personList = phoneDao.personSelect();
//						System.out.println(personList);

			// 데이터담기 forward
			request.setAttribute("personList", personList);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
