package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.Keyword;

public class KeywordToken extends Token {

	private Keyword keyword;

	public KeywordToken(Keyword keyword, FileIndex fileIndex) {
		super(fileIndex);
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
