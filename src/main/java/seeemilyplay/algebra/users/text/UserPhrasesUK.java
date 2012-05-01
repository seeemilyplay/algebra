package seeemilyplay.algebra.users.text;

final class UserPhrasesUK implements UserPhrases {

	public UserPhrasesUK() {
		super();
	}

	public String getEnterYourNameWarning() {
		return "Enter your name to play";
	}

	public String getNameUsedWarning() {
		return "Sorry, name already used";
	}

	public String getWhatsYourName() {
		return "What's your name?";
	}

	public String getAreYou(String name) {
		return "Are you " + name + "?";
	}

	public String getYesIAm() {
		return "Yes I am";
	}

	public String getNoIAmSomeoneElse() {
		return "No, I'm someone else";
	}

	public String getWhoAreYou() {
		return "Who are you?";
	}

	public String getImSomeoneNew() {
		return "I'm someone new";
	}
}
