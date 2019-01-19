package model;

public class AccountVO {

	private int Num;
	private String id;
	private String pw;
	private String name;
	private String birth;
	private String phone;
	private int authority;

	public AccountVO() {
		super();
	}

	public AccountVO(String id, String pw) {
		super();
		this.id = id;
		this.pw = pw;
	}

	public AccountVO(int num, int authority) {
		super();
		this.Num = num;
		this.authority = authority;
	}

	public AccountVO(String id, String pw, String name, String birth, String phone) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.birth = birth;
		this.phone = phone;
	}

	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		this.Num = num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

}
