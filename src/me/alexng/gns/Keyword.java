package me.alexng.gns;

public enum Keyword {

	TEST_KEYWORD2("test_keyword2"),
	TEST_KEYWORD("test_keyword");

	private String keyword;

	Keyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}
}
