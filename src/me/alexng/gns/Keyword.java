package me.alexng.gns;

public enum Keyword {

	FUNC("func"),
	IF("if");

	private String keyword;

	Keyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
}
