package model;

public class ScheduelVO {

	private int s_Num;
	private String s_StartTime;
	private String s_FinishTime;
	private int s_AdultPay;
	private int s_ChildPay;
	private int c_Num;
	private int m_Num;
	private String s_StartTime2;
	private String s_FinishTime2;

	public ScheduelVO() {
		super();
	}

	public ScheduelVO(String s_StartTime) {
		super();
		this.s_StartTime = s_StartTime;
	}

	public ScheduelVO(String s_StartTime, String s_FinishTime, int s_AdultPay, int s_ChildPay, int c_Num, int m_Num) {
		super();
		this.s_StartTime = s_StartTime;
		this.s_FinishTime = s_FinishTime;
		this.s_AdultPay = s_AdultPay;
		this.s_ChildPay = s_ChildPay;
		this.c_Num = c_Num;
		this.m_Num = m_Num;
	}

	public ScheduelVO(int s_Num, String s_StartTime, String s_FinishTime, int s_AdultPay, int s_ChildPay, int c_Num,
			int m_Num) {
		super();
		this.s_Num = s_Num;
		this.s_StartTime = s_StartTime;
		this.s_FinishTime = s_FinishTime;
		this.s_AdultPay = s_AdultPay;
		this.s_ChildPay = s_ChildPay;
		this.c_Num = c_Num;
		this.m_Num = m_Num;
	}

	public ScheduelVO(int s_Num, String s_StartTime, String s_FinishTime, int s_AdultPay, int s_ChildPay, int c_Num,
			int m_Num, String s_StartTime2, String s_FinishTime2) {
		super();
		this.s_Num = s_Num;
		this.s_StartTime = s_StartTime;
		this.s_FinishTime = s_FinishTime;
		this.s_AdultPay = s_AdultPay;
		this.s_ChildPay = s_ChildPay;
		this.c_Num = c_Num;
		this.m_Num = m_Num;
		this.s_StartTime2 = s_StartTime2;
		this.s_FinishTime2 = s_FinishTime2;
	}

	public int getS_Num() {
		return s_Num;
	}

	public void setS_Num(int s_Num) {
		this.s_Num = s_Num;
	}

	public String getS_StartTime() {
		return s_StartTime;
	}

	public void setS_StartTime(String s_StartTime) {
		this.s_StartTime = s_StartTime;
	}

	public String getS_FinishTime() {
		return s_FinishTime;
	}

	public void setS_FinishTime(String s_FinishTime) {
		this.s_FinishTime = s_FinishTime;
	}

	public int getC_Num() {
		return c_Num;
	}

	public void setC_Num(int c_Num) {
		this.c_Num = c_Num;
	}

	public int getM_Num() {
		return m_Num;
	}

	public void setM_Num(int m_Num) {
		this.m_Num = m_Num;
	}

	public int getS_AdultPay() {
		return s_AdultPay;
	}

	public void setS_AdultPay(int s_AdultPay) {
		this.s_AdultPay = s_AdultPay;
	}

	public int getS_ChildPay() {
		return s_ChildPay;
	}

	public void setS_ChildPay(int s_ChildPay) {
		this.s_ChildPay = s_ChildPay;
	}

	public String getS_StartTime2() {
		return s_StartTime2;
	}

	public void setS_StartTime2(String s_StartTime2) {
		this.s_StartTime2 = s_StartTime2;
	}

	public String getS_FinishTime2() {
		return s_FinishTime2;
	}

	public void setS_FinishTime2(String s_FinishTime2) {
		this.s_FinishTime2 = s_FinishTime2;
	}

}
