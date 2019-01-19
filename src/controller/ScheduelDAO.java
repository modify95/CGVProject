package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.CinemaVO;
import model.MovieVO;
import model.ScheduelVO;

public class ScheduelDAO {

	public boolean deleteTicketing(ScheduelVO svo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();
		sql.append("delete ticketing where s_num = ?");

		boolean success = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, svo.getS_Num());
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

	public boolean deleteScheduel(ScheduelVO svo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();
		sql.append("delete scheduel where s_num = ?");

		boolean success = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, svo.getS_Num());
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

	public boolean checkSchedeul(int c_Num, String startTime, String finishTime) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		boolean success = false;

		String checkTime = startTime.substring(0, 10);

		StringBuffer sql = new StringBuffer();
		sql.append("select c_num,s_starttime,s_finishtime from scheduel where c_num = ? and s_finishtime like to_date('"
				+ checkTime + "','YYYY-MM-DD') and s_finishtime > to_date('" + startTime
				+ "','YYYY-MM-DDHH24MI') and s_starttime < to_date('" + finishTime + "','YYYY-MM-DDHH24MI')");

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, c_Num);
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

	public ArrayList<MovieVO> loadMovie() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		MovieVO mvo = null;

		ArrayList<MovieVO> movieInfo = new ArrayList<>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from movie where m_closedate > sysdate");

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mvo = new MovieVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4).toString(),
						rs.getDate(5).toString(), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10), rs.getString(11));
				movieInfo.add(mvo);
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
		return movieInfo;
	}

	public ArrayList<CinemaVO> loadCinema() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		CinemaVO cVo = null;

		ArrayList<CinemaVO> cinemaList = new ArrayList<>();

		StringBuffer sql = new StringBuffer();
		sql.append("select * from cinema");

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cVo = new CinemaVO(rs.getInt(1), rs.getString(2));
				cinemaList.add(cVo);
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
		return cinemaList;
	}

	public ArrayList<ScheduelVO> loadScheduel() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select s.s_num,to_char(s.s_starttime,'YYYY.MM.DD HH24:MI') as starttime,to_char(s.s_finishtime,'YYYY.MM.DD HH24:MI'),s.s_adultpay,s.s_childpay,s.c_num,s.m_num,m.m_title,c.c_name from scheduel s, movie m, cinema c where s.m_num = m.m_num and c.c_num = s.c_num and s.s_starttime > sysdate order by m.m_title,c.c_name,starttime");

		ArrayList<ScheduelVO> scheduelList = new ArrayList<>();

		ScheduelVO sVo;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				sVo = new ScheduelVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9));
				scheduelList.add(sVo);
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
		return scheduelList;
	}

	public boolean addScheduel(ScheduelVO svo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();

		String startTime = svo.getS_StartTime();
		String finishTime = svo.getS_FinishTime();

		String sTimeHour = startTime.substring(10, 12);
		String fTimeHour = finishTime.substring(10, 12);
		String sTimeMin = startTime.substring(12, 14);
		String fTimeMin = finishTime.substring(12, 14);
		int sTimeHour2 = Integer.parseInt(startTime.substring(10, 12));
		int fTimeHour2 = Integer.parseInt(finishTime.substring(10, 12));
		int sTimeMin2 = Integer.parseInt(startTime.substring(12, 14));

		String strSTime = sTimeHour + ":" + sTimeMin;
		String strFTime = fTimeHour + ":" + fTimeMin;

		if (sTimeHour2 < 6) {
			sTimeHour2 += 24;
			fTimeHour2 += 24;
			strSTime = sTimeHour2 + ":" + sTimeMin + "(심야)";
			strFTime = fTimeHour2 + ":" + fTimeMin + "(심야)";
		} else if (sTimeHour2 >= 6 && sTimeHour2 < 10 || (sTimeHour2 == 10 && sTimeMin2 == 0)) {
			strSTime = sTimeHour + ":" + sTimeMin + "(조조)";
			strFTime = fTimeHour + ":" + fTimeMin + "(조조)";
		}

		sql.append("insert into scheduel values (scheduel_seq.nextval,to_date('" + startTime
				+ "','YYYY-MM-DDHH24MI'),to_date('" + finishTime + "','YYYY-MM-DDHH24MI'),?,?,?,?,?,?)");

		boolean success = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, svo.getS_AdultPay());
			pstmt.setInt(2, svo.getS_ChildPay());
			pstmt.setInt(3, svo.getC_Num());
			pstmt.setInt(4, svo.getM_Num());
			pstmt.setString(5, strSTime);
			pstmt.setString(6, strFTime);

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
