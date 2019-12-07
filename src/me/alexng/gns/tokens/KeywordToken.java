package me.alexng.gns.tokens;

import me.alexng.gns.Keyword;
import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class KeywordToken extends Token {

	private Keyword keyword;

	private KeywordToken(Keyword keyword, int startIndex, int endIndex) {
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

	public static class Generator implements TokenGenerator {
		@Override
		public int accepts(String input, int index) {
			String trimmedInput = input.substring(index);
			for (Keyword keyword : Keyword.values()) {
				if (trimmedInput.startsWith(keyword.getKeyword())) {
					return index + keyword.getKeyword().length();
				}
			}
			return 0;
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) throws ParsingException {
			String key = input.substring(startIndex, endIndex);
			for (Keyword keyword : Keyword.values()) {
				if (keyword.getKeyword().equals(key)) {
					return new KeywordToken(keyword, startIndex, endIndex);
				}
			}
			throw new ParsingException(startIndex, "Keyword unrecognised");
		}
	}
}
