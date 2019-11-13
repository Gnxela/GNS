package me.alexng.gns.lexer.tokens;

import me.alexng.gns.AmbiguousParsingException;
import me.alexng.gns.Keyword;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class KeywordToken extends Token {

	private Keyword keyword;

	KeywordToken(Keyword keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "<Keyword " + keyword.getKeyword() + ">";
	}

	public static class KeywordGenerator implements TokenGenerator {
		@Override
		public int accepts(String input) {
			for (Keyword keyword : Keyword.values()) {
				if (input.startsWith(keyword.getKeyword())) {
					return keyword.getKeyword().length();
				}
			}
			return 0;
		}

		@Override
		public Token generate(String input) throws AmbiguousParsingException {
			for (Keyword keyword : Keyword.values()) {
				if (input.startsWith(keyword.getKeyword())) {
					return new KeywordToken(keyword);
				}
			}
			// TODO: Create a exception.
			throw new AmbiguousParsingException("Invalid");
		}
	}
}
