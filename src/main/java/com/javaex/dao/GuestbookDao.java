package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;

public class GuestbookDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/web_db";
	private String id = "web";
	private String pw = "web";

	// 메소드- 일반

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver); // 위에 생성자로 올려주고 변수명으로 넣어줌

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	} // getConnection()끝

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	} // getConnection()끝

	// 전체 가져오기
	public List<PersonVo> personSelect() {

		List<PersonVo> personList = new ArrayList<PersonVo>();

		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select no, name, password, content, reg_date ";
			query += " from guestbook ";

			// - 바인딩
			pstmt = conn.prepareStatement(query);

			// - 실행
			rs = pstmt.executeQuery();

			// 4.결과처리

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");

				PersonVo personVo = new PersonVo(no, name, password, content, reg_date);
				personList.add(personVo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();
		
		return personList;
	}

	// 등록
	public int personInsert(PersonVo personVo) {
		int count = -1;

		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " insert into guestbook ";
			query += " values(null, ?, ?, ?, now()) ";

			// - 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getPassword());
			pstmt.setString(3, personVo.getContent());

			// - 실행
			count = pstmt.executeUpdate();

			// 4. 결과처리
			System.out.println(count + "건 등록 되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();

		return count;
	}

	// 삭제
	public int personDelete(int no, String password) {
		int count = -1;

		this.getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";

			// - 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, password);

			// - 실행
			count = pstmt.executeUpdate();

			// 4. 결과처리
			System.out.println(count + "건 삭제 되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();

		return count;
	}

}
