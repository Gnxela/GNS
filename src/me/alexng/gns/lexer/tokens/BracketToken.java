package me.alexng.gns.lexer.tokens;

import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class BracketToken extends Token {

	private Bracket bracket;
	private Type type;

	BracketToken(Bracket bracket, Type type) {
		this.bracket = bracket;
		this.type = type;
	}

	@Override
	public String toString() {
		return "<Bracket " + bracket + ":" + type + ">";
	}

	public enum Type {
		OPEN, CLOSED
	}

	public enum Bracket {
		ROUND, SQUARE, CURLY
	}

	public static class BracketGenerator implements TokenGenerator {

		private static final Character[] BRACKETS = new Character[]{'(', ')', '{', '}', '[', ']'};

		@Override
		public int accepts(String input) {
			char firstCharacter = input.charAt(0);
			for (Character bracket : BRACKETS) {
				if (firstCharacter == bracket) {
					return 1;
				}
			}
			return 0;
		}

		@Override
		public Token generate(String input) throws Exception {
			switch (input) {
				case "(":
					return new BracketToken(Bracket.ROUND, Type.OPEN);
				case ")":
					return new BracketToken(Bracket.ROUND, Type.CLOSED);
				case "[":
					return new BracketToken(Bracket.SQUARE, Type.OPEN);
				case "]":
					return new BracketToken(Bracket.SQUARE, Type.CLOSED);
				case "{":
					return new BracketToken(Bracket.CURLY, Type.OPEN);
				case "}":
					return new BracketToken(Bracket.CURLY, Type.CLOSED);
				default:
					// TODO: Create a exception.
					throw new Exception("Invalid");
			}
		}
	}
}