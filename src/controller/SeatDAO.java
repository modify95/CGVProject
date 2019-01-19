package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ScheduelVO;

public class SeatDAO {

	public ArrayList<Integer> checkLeftSeat(ScheduelVO svo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append("select t_seat from ticketing where s_num = ?");

		ArrayList<Integer> list = new ArrayList<>();

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, svo.getS_Num());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String[] seat = rs.getString(1).split(",");

				for (String cha : seat) {
					list.add(Integer.parseInt(cha));
				}
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

	public boolean checkGrade(ScheduelVO svo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append("select m.m_grade from movie m,scheduel s where m.m_num = s.m_num and s.m_num = ?");

		boolean success = true;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, svo.getM_Num());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equals("청소년관람불가")) {
					success = false;
				}
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
		return success;
	}

	public int leftSeat(ScheduelVO svo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select nvl((select c_count from cinema where c_num = ?) - (select sum(t_count) from ticketing where s_num = ?),(select c_count from cinema where c_num = ?)) from dual");

		int count = 0;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, svo.getC_Num());
			pstmt.setInt(2, svo.getS_Num());
			pstmt.setInt(3, svo.getC_Num());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}

}
