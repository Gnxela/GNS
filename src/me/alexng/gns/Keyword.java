package me.alexng.gns;

import me.alexng.gns.tokens.KeywordToken;
import me.alexng.gns.tokens.Token;

public enum Keyword {

    NULL("null"),
    TRUE("true"),
    FALSE("false"),
    FUNC("func"),
    RETURN("return"),
    IF("if"),
    CLASS("class"),
    NEW("new");

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
