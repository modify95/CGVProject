package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import model.MovieVO;
import model.ScheduelVO;
import model.TicketingVO;

public class TicketingDAO {

	public boolean addTicketing(TicketingVO tvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"insert into ticketing values (to_char(sysdate,'YYYYMMDD') || lpad(ticketing_seq.nextval,8,'0'),?,to_date(sysdate,'YYYY.MM.DD'),?,?,?,?,?,?)");

		boolean success = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, tvo.getT_Total());
			pstmt.setString(2, tvo.getT_Seat());
			pstmt.setInt(3, tvo.getNum());
			pstmt.setInt(4, tvo.getM_Num());
			pstmt.setInt(5, tvo.getS_Num());
			pstmt.setInt(6, tvo.getT_Count());
			pstmt.setString(7, tvo.getM_Title());
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

	public String TicketNumber(ScheduelVO svo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select t_num from (select t_num from ticketing where s_num = ? order by t_date desc) where rownum = 1");

		String tNum = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, svo.getS_Num());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				tNum = rs.getString(1);
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
		return tNum;
	}

	public boolean cancelTimeConfirm(String t_Num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();

		sql.append(
				"select * from scheduel s, ticketing t where s.s_num = t.s_num and t.t_num = ? and s.s_starttime > sysdate");

		boolean success = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, t_Num);
			rs = pstmt.executeQuery();

			if (rs.next()) {
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

	public boolean cancelTicketing(String t_Num) {
		Connection con = null;
		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();
		sql.append("delete ticketing where t_num = ?");

		boolean success = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, t_Num);
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

	public ArrayList<ScheduelVO> selectMovie(MovieVO mvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select distinct to_char(s.s_starttime,'YYYY.MM.DD') as sdate from scheduel s,movie m where s.m_num = m.m_num and m.m_title = ? and s.s_starttime > sysdate order by sdate");

		ArrayList<ScheduelVO> dateList = new ArrayList<>();

		ScheduelVO sVo = null;

		String yoil = "";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, mvo.getM_Title());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				yoil = getDayOfWeek(rs.getString(1));
				sVo = new ScheduelVO(rs.getString(1) + yoil);
				dateList.add(sVo);
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
		return dateList;
	}

	public ArrayList<MovieVO> selectDate(ScheduelVO svo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select distinct m.m_num,m.m_title,m.m_poster,m.m_grade from scheduel s,movie m where s.m_num = m.m_num and s.s_starttime like to_date(?,'YYYY.MM.DD') and s.s_starttime > sysdate");

		ArrayList<MovieVO> movieList = new ArrayList<>();

		MovieVO mVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, svo.getS_StartTime().substring(0, 10));
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mVo = new MovieVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				movieList.add(mVo);
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
		return movieList;
	}

	public ArrayList<MovieVO> screenMovie() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select distinct m.m_num,m.m_title,m.m_poster,m.m_grade from scheduel s, movie m where s.m_num = m.m_num and s.s_starttime > sysdate");

		ArrayList<MovieVO> movieList = new ArrayList<>();

		MovieVO mVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mVo = new MovieVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				movieList.add(mVo);
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
		return movieList;
	}

	public ArrayList<ScheduelVO> screenDate() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select distinct to_char(s_starttime,'YYYY.MM.DD') as sdate from scheduel where s_starttime > sysdate order by sdate");

		ArrayList<ScheduelVO> dateList = new ArrayList<>();

		ScheduelVO sVo = null;

		String yoil = "";

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				yoil = getDayOfWeek(rs.getString(1));
				sVo = new ScheduelVO(rs.getString(1) + yoil);
				dateList.add(sVo);
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
		return dateList;
	}

	public ArrayList<ScheduelVO> screenTime(String title, String date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		date = date.substring(0, 10);
		sql.append(
				"select s.* from scheduel s, movie m where s.m_num = m.m_num and m.m_title = ? and s.s_starttime like to_date(?,'YYYY.MM.DD') and s.s_starttime > sysdate order by c_num,s_starttime2");

		ScheduelVO svo;

		ArrayList<ScheduelVO> timeList = new ArrayList<>();

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, title);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new ScheduelVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9));
				timeList.add(svo);
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
		return timeList;
	}

	public HashMap<Integer, Integer> countTime(String title, String date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select c_num,count(s_starttime) from scheduel where m_num = (select m_num from movie where m_title = ?) and s_starttime like to_date(?,'YYYY.MM.DD') and s_starttime > sysdate group by c_num");

		date = date.substring(0, 10);
		HashMap<Integer, Integer> count = new HashMap<>();

		try {

			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, title);
			pstmt.setString(2, date);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				count.put(rs.getInt(1), rs.getInt(2));
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

	public String getDayOfWeek(String date) {
		String yoil = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
			Calendar cal = Calendar.getInstance();

			cal.setTime(df.parse(date));
			int week = cal.get(Calendar.DAY_OF_WEEK);
			switch (week) {
			case 1:
				yoil = "(일)";
				break;
			case 2:
				yoil = "(월)";
				break;
			case 3:
				yoil = "(화)";
				break;
			case 4:
				yoil = "(수)";
				break;
			case 5:
				yoil = "(목)";
				break;
			case 6:
				yoil = "(금)";
				break;
			case 7:
				yoil = "(토)";
				break;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return yoil;
	}

}
