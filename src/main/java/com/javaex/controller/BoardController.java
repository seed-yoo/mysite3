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

@WebServlet("/board")
public class BoardController extends HttpServlet {
	// 필드
	private static final long serialVersionUID = 1L;

	// 생성자 - 없음

	// 메소드 - getter/setter

	// 메소드 - 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("BoardController");

		String action = request.getParameter("action");
		System.out.println(action);

		if ("listform".equals(action)) {
			System.out.println("board>boardlistform");

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		} else if ("modifyform".equals(action)) {
			System.out.println("board>modifyform");

			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");

		} else if ("read".equals(action)) {
			System.out.println("board>readform");

			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

		} else if ("writeform".equals(action)) {
			System.out.println("board>writeform");

			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");

		} else {
			System.out.println("action값 확인해라");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
