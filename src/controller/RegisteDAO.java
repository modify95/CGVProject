package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AccountVO;

public class RegisteDAO {

	public boolean overlap(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append("select id from staff where id = ?");

		boolean success = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (!rs.next()) {
				success = true;
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

	public boolean staffRegister(AccountVO avo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		boolean success = false;

		String day = avo.getBirth();

		StringBuffer sql = new StringBuffer();
		sql.append("insert into staff values (staff_seq.nextval,?,?,?,to_date('" + day
				+ "','YYYY-MM-DD'),?,2)");

		try {

			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, avo.getId());
			pstmt.setString(2, avo.getPw());
			pstmt.setString(3, avo.getName());
			pstmt.setString(4, avo.getPhone());

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
