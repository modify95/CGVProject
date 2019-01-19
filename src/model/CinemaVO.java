package model;

public class CinemaVO {

	private int c_num;
	private String c_name;

	public CinemaVO() {
		super();
	}

	public CinemaVO(int c_num, String c_name) {
		super();
		this.c_num = c_num;
		this.c_name = c_name;
	}

	public int getC_num() {
		return c_num;
	}

	public void setC_num(int c_num) {
		this.c_num = c_num;
	}

	public String getC_name() {
		return c_name;
	}

	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

}
