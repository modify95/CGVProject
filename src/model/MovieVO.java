package model;

public class MovieVO {

	private int m_Num;
	private String m_Title;
	private String m_Genre;
	private String m_OpenDate;
	private String m_CloseDate;
	private int m_RunTime;
	private String m_Nation;
	private String m_Grade;
	private String m_Director;
	private String m_Story;
	private String m_Poster;

	public MovieVO() {
		super();
	}

	public MovieVO(String m_Title, String m_OpenDate, String m_CloseDate) {
		super();
		this.m_Title = m_Title;
		this.m_OpenDate = m_OpenDate;
		this.m_CloseDate = m_CloseDate;
	}

	public MovieVO(String m_Title, String m_OpenDate, String m_CloseDate, String m_Nation) {
		super();
		this.m_Title = m_Title;
		this.m_OpenDate = m_OpenDate;
		this.m_CloseDate = m_CloseDate;
		this.m_Nation = m_Nation;
	}

	public MovieVO(int m_Num, String m_Title, String m_Poster, String m_Grade) {
		super();
		this.m_Num = m_Num;
		this.m_Title = m_Title;
		this.m_Poster = m_Poster;
		this.m_Grade = m_Grade;
	}

	public MovieVO(String m_Title, String m_Genre, String m_OpenDate, String m_CloseDate, int m_RunTime,
			String m_Nation, String m_Grade, String m_Director, String m_Story, String m_Poster) {
		super();
		this.m_Title = m_Title;
		this.m_Genre = m_Genre;
		this.m_OpenDate = m_OpenDate;
		this.m_CloseDate = m_CloseDate;
		this.m_RunTime = m_RunTime;
		this.m_Nation = m_Nation;
		this.m_Grade = m_Grade;
		this.m_Director = m_Director;
		this.m_Story = m_Story;
		this.m_Poster = m_Poster;
	}

	public MovieVO(int m_Num, String m_Title, String m_Genre, String m_OpenDate, String m_CloseDate, int m_RunTime,
			String m_Nation, String m_Grade, String m_Director, String m_Story, String m_Poster) {
		super();
		this.m_Num = m_Num;
		this.m_Title = m_Title;
		this.m_Genre = m_Genre;
		this.m_OpenDate = m_OpenDate;
		this.m_CloseDate = m_CloseDate;
		this.m_RunTime = m_RunTime;
		this.m_Nation = m_Nation;
		this.m_Grade = m_Grade;
		this.m_Director = m_Director;
		this.m_Story = m_Story;
		this.m_Poster = m_Poster;
	}

	public int getNum() {
		return m_Num;
	}

	public void setNum(int num) {
		this.m_Num = num;
	}

	public String getM_Title() {
		return m_Title;
	}

	public void setM_Title(String m_Title) {
		this.m_Title = m_Title;
	}

	public String getM_Genre() {
		return m_Genre;
	}

	public void setM_Genre(String m_Genre) {
		this.m_Genre = m_Genre;
	}

	public String getM_OpenDate() {
		return m_OpenDate;
	}

	public void setM_OpenDate(String m_OpenDate) {
		this.m_OpenDate = m_OpenDate;
	}

	public int getM_RunTime() {
		return m_RunTime;
	}

	public void setM_RunTime(int m_RunTime) {
		this.m_RunTime = m_RunTime;
	}

	public String getM_Nation() {
		return m_Nation;
	}

	public void setM_Nation(String m_Nation) {
		this.m_Nation = m_Nation;
	}

	public String getM_Grade() {
		return m_Grade;
	}

	public void setM_Grade(String m_Grade) {
		this.m_Grade = m_Grade;
	}

	public String getM_Director() {
		return m_Director;
	}

	public void setM_Director(String m_Director) {
		this.m_Director = m_Director;
	}

	public String getM_Story() {
		return m_Story;
	}

	public void setM_Story(String m_Story) {
		this.m_Story = m_Story;
	}

	public String getM_Poster() {
		return m_Poster;
	}

	public void setM_Poster(String m_Poster) {
		this.m_Poster = m_Poster;
	}

	public int getM_Num() {
		return m_Num;
	}

	public void setM_Num(int m_Num) {
		this.m_Num = m_Num;
	}

	public String getM_CloseDate() {
		return m_CloseDate;
	}

	public void setM_CloseDate(String m_CloseDate) {
		this.m_CloseDate = m_CloseDate;
	}

}
