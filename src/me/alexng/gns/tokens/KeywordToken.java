package me.alexng.gns.tokens;

import me.alexng.gns.Keyword;

public class KeywordToken extends Token {

	private Keyword keyword;

	public KeywordToken(Keyword keyword, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.keyword = keyword;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	@Override
	public String toString() {
		return "<Keyword " + keyword.getKeyword() + ">";
	}
}
