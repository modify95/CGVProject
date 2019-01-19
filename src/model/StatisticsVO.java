package model;

public class StatisticsVO {

	private int rank;
	private String title;
	private String openDate;
	private String sales;
	private String attendance;

	public StatisticsVO() {
		super();
	}

	public StatisticsVO(int rank, String title, String openDate, String sales, String attendance) {
		super();
		this.rank = rank;
		this.title = title;
		this.openDate = openDate;
		this.sales = sales;
		this.attendance = attendance;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

}
