package seeemilyplay.algebra.bulletin.notices;

import seeemilyplay.algebra.bulletin.Notice;

public final class NewUserNotice implements Notice {

	private final String user;
	
	public NewUserNotice(String user) {
		this.user = user;
	}
	
	public String getUser() {
		return user;
	}
}
