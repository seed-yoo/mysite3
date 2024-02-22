package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	// 필드
	private static final long serialVersionUID = 1L;

	// 생성자 - 없음

	// 메소드 - getter/setter

	// 메소드 - 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("UserController");

		String action = request.getParameter("action");
		System.out.println(action);

		if ("joinform".equals(action)) {
			System.out.println("user>joinform");

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {
			System.out.println("user>join");

			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			// vo로 묶기
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo.toString());

			// db관련 업무
			UserDao userDao = new UserDao();

			// db에 저장
			userDao.insertUser(userVo);

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginform".equals(action)) {
			System.out.println("user>loginform");

			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) {
			System.out.println("user>login");

			String id = request.getParameter("id");
			String password = request.getParameter("pw");

			UserVo userVo = new UserVo(id, password);

			UserDao userDao = new UserDao();
			UserVo authUser = userDao.selectUserByIdPw(userVo); // id pw
			// no name

			if (authUser != null) { // 로그인 성공
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);

				WebUtil.redirect(request, response, "/mysite3/main");
			} else { // 로그인 실패
				// System.out.println("로그인 실패");
				WebUtil.redirect(request, response, "/mysite3/user?action=loginform");

			}

		} else if ("modifyform".equals(action)) {
			System.out.println("user>modifyform");

			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

		} else if ("logout".equals(action)) {
			System.out.println("user>logout");
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			
			WebUtil.redirect(request, response, "/mysite3/main");
			
			
		}else if ("update".equals(action)) {
				System.out.println("user>update");
				
				int no = Integer.parseInt(request.getParameter("no"));
				String id = request.getParameter("id");
				String password = request.getParameter("pw");
				String name = request.getParameter("name");
				String gender = request.getParameter("gender");

				// vo로 묶기
				UserVo userVo = new UserVo(no, id, password, name, gender);
				System.out.println(userVo.toString());

				// db관련 업무
				UserDao userDao = new UserDao();

				// db에 저장
				userDao.updateUser(userVo);

				WebUtil.redirect(request, response, "/mysite3/user?action=logout");


		} else {
			System.out.println("action값 확인하세여");
		}
//			else if ("delete".equals(action)) {
//			System.out.println("delete:삭제");
//
//			int no = Integer.parseInt(request.getParameter("no"));
//			System.out.println(no);
//
//			// db관련 업무
//			PhoneDao phoneDao = new PhoneDao();
//
//			// 삭제
//			phoneDao.personDelete(no);
//			/*
//			 * response.sendRedirect("/phonebook3/pbc?action=list");
//			 */
//			
//			WebUtil.redirect(request, response, "/phonebook3/pbc?action=list");
//			
//		} else if ("mform".equals(action)) {
//			System.out.println("mform:수정폼");
//			int no = Integer.parseInt(request.getParameter("no"));
//			System.out.println(no);
//
//			// db사용
//			PhoneDao phoneDao = new PhoneDao();
//
//			// 리스트 가져오기
//			List<PersonVo> personList = phoneDao.personSelect();
////						System.out.println(personList);
//
//			// 데이터담기 forward
//			request.setAttribute("personList", personList);
///*
//			// jsp 한테 html 그리기 응답해라 ==> forward
//			RequestDispatcher rd = request.getRequestDispatcher("/updateForm.jsp");
//			rd.forward(request, response);
//*/
//			WebUtil.forward(request, response, "/WEB-INF/updateForm.jsp");
//			
//		} else if ("modify".equals(action)) {
//			System.out.println("modify:수정");
//
//			int no = Integer.parseInt(request.getParameter("no"));
//			String name = request.getParameter("name");
//			String hp = request.getParameter("hp");
//			String company = request.getParameter("company");
//
//			// vo로 묶기
//			PersonVo personVo = new PersonVo(no, name, hp, company);
//			System.out.println(personVo.toString());
//
//			// db관련 업무
//			PhoneDao phoneDao = new PhoneDao();
//
//			// db에 저장
//			phoneDao.personUpdate(personVo);
///*
//			response.sendRedirect("/phonebook3/pbc?action=list");
//			*/
//			WebUtil.redirect(request, response, "/phonebook3/pbc?action=list");
//
//		} else {
//			System.out.println("list:리스트");
//
//			// db사용
//			PhoneDao phoneDao = new PhoneDao();
//
//			// 리스트 가져오기
//			List<PersonVo> personList = phoneDao.personSelect();
//
//			// 데이터담기 forward
//			request.setAttribute("personList", personList);
//			/*
//			 * RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
//			 * rd.forward(request, response);
//			 */
//			WebUtil.forward(request, response, "/WEB-INF/list.jsp");
//		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
