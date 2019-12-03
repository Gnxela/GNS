package me.alexng.gns;

import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.tokens.KeywordToken;

public enum Keyword {

	TRUE("true"),
	FALSE("false"),
	FUNC("func"),
	IF("if");

	private String keyword;

	Keyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public boolean matches(Token token) {
		return token instanceof KeywordToken && ((KeywordToken) token).getKeyword() == this;
	}
}
