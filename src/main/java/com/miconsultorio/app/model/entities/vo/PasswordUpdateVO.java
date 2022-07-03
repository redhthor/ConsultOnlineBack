package com.miconsultorio.app.model.entities.vo;

public class PasswordUpdateVO {
	private String password;
	private String user;

	public PasswordUpdateVO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "PasswordUpdateVO [password=" + password + ", user=" + user + "]";
	}

}
