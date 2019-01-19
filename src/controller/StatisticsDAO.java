package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.StatisticsVO;

public class StatisticsDAO {

	public ArrayList<String> loadOpenDate() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append("select distinct to_char(m_opendate,'YYYY.MM') from movie");

		ArrayList<String> list = new ArrayList<>();

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString(1));
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

	public ArrayList<String> loadCloseDate() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append("select distinct to_char(m_closedate,'YYYY.MM') from movie");

		ArrayList<String> list = new ArrayList<>();

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(rs.getString(1));
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

	public ArrayList<StatisticsVO> loadCurrent() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select m.m_title, to_char(m.m_opendate,'YYYY.MM.DD'), nvl(sum(t.t_total),0), nvl(sum(t.t_count),0) ");
		sql.append("from ticketing t, movie m ");
		sql.append(
				"where t.m_title(+) = m.m_title and t.t_date(+) like to_date(sysdate,'YYYY.MM.DD') and m.m_closedate >= sysdate ");
		sql.append("group by m.m_title,m.m_opendate");

		ArrayList<StatisticsVO> list = new ArrayList<>();
		StatisticsVO svo;
		int i = 1;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new StatisticsVO(i, rs.getString(1), rs.getString(2),
						StatisticsController.numFormat(Integer.parseInt(rs.getString(3))),
						StatisticsController.numFormat(Integer.parseInt(rs.getString(4))));
				list.add(svo);
				i++;
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

	public ArrayList<StatisticsVO> loadDay(LocalDate date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select m.m_title, to_char(m.m_opendate,'YYYY.MM.DD'), nvl(sum(t.t_total),0), nvl(sum(t.t_count),0) ");
		sql.append("from ticketing t, movie m ");
		sql.append(
				"where t.m_title(+) = m.m_title and to_char(t.t_date(+),'YYYY-MM-DD') like ? || '%' and to_char(m.m_closedate,'YYYY-MM-DD') >= ? and to_char(m.m_opendate,'YYYY-MM-DD') <= ? and t.t_date(+) <= sysdate - 1 ");
		sql.append("group by m.m_title,m.m_opendate");

		ArrayList<StatisticsVO> list = new ArrayList<>();
		StatisticsVO svo;
		int i = 1;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, date + "");
			pstmt.setString(2, date + "");
			pstmt.setString(3, date + "");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new StatisticsVO(i, rs.getString(1), rs.getString(2),
						StatisticsController.numFormat(Integer.parseInt(rs.getString(3))),
						StatisticsController.numFormat(Integer.parseInt(rs.getString(4))));
				list.add(svo);
				i++;
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

	public ArrayList<StatisticsVO> loadMonth(String date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select m.m_title, to_char(m.m_opendate,'YYYY.MM.DD'), nvl(sum(t.t_total),0), nvl(sum(t.t_count),0) ");
		sql.append("from ticketing t, movie m ");
		sql.append(
				"where t.m_title(+) = m.m_title and to_char(t.t_date(+),'YYYY.MM') like ? || '%' and to_char(m.m_closedate,'YYYY.MM') >= ? and to_char(m.m_opendate,'YYYY.MM') <= ? and t.t_date(+) <= sysdate - 1 ");
		sql.append("group by m.m_title,m.m_opendate");

		ArrayList<StatisticsVO> list = new ArrayList<>();
		StatisticsVO svo;
		int i = 1;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, date + "");
			pstmt.setString(2, date + "");
			pstmt.setString(3, date + "");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new StatisticsVO(i, rs.getString(1), rs.getString(2),
						StatisticsController.numFormat(Integer.parseInt(rs.getString(3))),
						StatisticsController.numFormat(Integer.parseInt(rs.getString(4))));
				list.add(svo);
				i++;
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

	public ArrayList<StatisticsVO> loadYear(String date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select m.m_title, to_char(m.m_opendate,'YYYY.MM.DD'), nvl(sum(t.t_total),0), nvl(sum(t.t_count),0) ");
		sql.append("from ticketing t, movie m ");
		sql.append(
				"where t.m_title(+) = m.m_title and to_char(t.t_date(+),'YYYY') like ? || '%' and to_char(m.m_closedate,'YYYY') >= ? and to_char(m.m_opendate,'YYYY') <= ? and t.t_date(+) <= sysdate - 1 ");
		sql.append("group by m.m_title,m.m_opendate");

		ArrayList<StatisticsVO> list = new ArrayList<>();
		StatisticsVO svo;
		int i = 1;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, date + "");
			pstmt.setString(2, date + "");
			pstmt.setString(3, date + "");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new StatisticsVO(i, rs.getString(1), rs.getString(2),
						StatisticsController.numFormat(Integer.parseInt(rs.getString(3))),
						StatisticsController.numFormat(Integer.parseInt(rs.getString(4))));
				list.add(svo);
				i++;
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

}
