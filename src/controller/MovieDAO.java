package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.MovieVO;

public class MovieDAO {

	public boolean deleteMovie(MovieVO mvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();
		sql.append("delete movie where m_num = ?");

		boolean success = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, mvo.getM_Num());
			int i = pstmt.executeUpdate();

			if (i == 1) {
				success = true;
			}
		} catch (SQLException e) {
			success = false;
			if (e.toString().contains("자식 레코드가 발견되었습니다")) {
				Alert alert= new Alert(AlertType.INFORMATION);
				alert.setTitle("영화 삭제");
				alert.setHeaderText("영화 삭제 실패");
				alert.setContentText("이미 상영중인 영화가 있습니다.\n상영 일정을 확인 후 다시시도해 주세요.");
				alert.showAndWait();
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
		return success;
	}

	public boolean editMovie(MovieVO mvo, int m_Num) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String openDate = mvo.getM_OpenDate();
		String closeDate = mvo.getM_CloseDate();

		StringBuffer sql = new StringBuffer();
		sql.append("update movie set m_title = ?,m_genre = ?,m_opendate = to_date('" + openDate
				+ "','YYYY-MM-DD'),m_closedate = to_date('" + closeDate
				+ "','YYYY-MM-DD'),m_runtime = ?,m_nation = ?,m_grade = ?,m_director = ?,m_story = ?,m_poster = ? where m_num = ?");

		boolean success = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, mvo.getM_Title());
			pstmt.setString(2, mvo.getM_Genre());
			pstmt.setInt(3, mvo.getM_RunTime());
			pstmt.setString(4, mvo.getM_Nation());
			pstmt.setString(5, mvo.getM_Grade());
			pstmt.setString(6, mvo.getM_Director());
			pstmt.setString(7, mvo.getM_Story());
			pstmt.setString(8, mvo.getM_Poster());
			pstmt.setInt(9, m_Num);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				success = true;
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
		return success;
	}

	public ArrayList<MovieVO> loadMovie() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select m_num,m_title,m_genre,to_char(m_opendate,'YYYY.MM.DD'),to_char(m_closedate,'YYYY.MM.DD'),m_runtime,m_nation,m_grade,m_director,m_story,m_poster from movie");

		ArrayList<MovieVO> list = new ArrayList<>();
		MovieVO mVo;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mVo = new MovieVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11));
				list.add(mVo);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return list;
	}

	public boolean AddMovie(MovieVO mvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		boolean success = false;

		String openDate = mvo.getM_OpenDate();
		String closeDate = mvo.getM_CloseDate();

		StringBuffer sql = new StringBuffer();
		sql.append(
				"insert into movie values (movie_seq.nextval,?,?,'" + openDate + "','" + closeDate + "',?,?,?,?,?,?)");

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, mvo.getM_Title());
			pstmt.setString(2, mvo.getM_Genre());
			pstmt.setInt(3, mvo.getM_RunTime());
			pstmt.setString(4, mvo.getM_Nation());
			pstmt.setString(5, mvo.getM_Grade());
			pstmt.setString(6, mvo.getM_Director());
			pstmt.setString(7, mvo.getM_Story());
			pstmt.setString(8, mvo.getM_Poster());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				success = true;
			}

		} catch (SQLException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
		return success;
	}

}
