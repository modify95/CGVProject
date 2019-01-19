package model;

public class TicketingVO {

	private String t_Num;
	private int t_Total;
	private String t_Date;
	private String t_Seat;
	private int Num;
	private int m_Num;
	private int s_Num;
	private int t_Count;
	private String m_Title;

	public TicketingVO() {
		super();
	}

	public TicketingVO(int t_Total, String t_Seat, int num, int m_Num, int s_Num, int t_Count, String m_Title) {
		super();
		this.t_Total = t_Total;
		this.t_Seat = t_Seat;
		Num = num;
		this.m_Num = m_Num;
		this.s_Num = s_Num;
		this.t_Count = t_Count;
		this.m_Title = m_Title;
	}

	public String getT_Num() {
		return t_Num;
	}

	public void setT_Num(String t_Num) {
		this.t_Num = t_Num;
	}

	public int getT_Total() {
		return t_Total;
	}

	public void setT_Total(int t_Total) {
		this.t_Total = t_Total;
	}

	public String getT_Date() {
		return t_Date;
	}

	public void setT_Date(String t_Date) {
		this.t_Date = t_Date;
	}

	public String getT_Seat() {
		return t_Seat;
	}

	public void setT_Seat(String t_Seat) {
		this.t_Seat = t_Seat;
	}

	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
	}

	public int getM_Num() {
		return m_Num;
	}

	public void setM_Num(int m_Num) {
		this.m_Num = m_Num;
	}

	public int getS_Num() {
		return s_Num;
	}

	public void setS_Num(int s_Num) {
		this.s_Num = s_Num;
	}

	public int getT_Count() {
		return t_Count;
	}

	public void setT_Count(int t_Count) {
		this.t_Count = t_Count;
	}

	public String getM_Title() {
		return m_Title;
	}

	public void setM_Title(String m_Title) {
		this.m_Title = m_Title;
	}

}
