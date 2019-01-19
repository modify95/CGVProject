package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AccountVO;

public class LoginDAO {

	public AccountVO Login(AccountVO avo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select num,authority from staff where id = ? and pw = ?");

		AccountVO result = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, avo.getId());
			pstmt.setString(2, avo.getPw());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new AccountVO(rs.getInt(1), rs.getInt(2));
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
		return result;
	}

}
