package hr.fer.rznu.jdbc.templates.userclasses;

public class User {

	private Integer id;
	private String username;
	private String passwordhash;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordhash() {
		return passwordhash;
	}

	public void setPasswordhash(String passwordhash) {
		this.passwordhash = passwordhash;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof User) {
			return id == ((User)o).id;
		} else {
			return false;
		}
	}

}
