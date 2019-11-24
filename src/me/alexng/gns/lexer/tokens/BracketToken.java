package me.alexng.gns.lexer.tokens;

import me.alexng.gns.ParsingException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class BracketToken extends Token {

	public static final BracketToken ROUND_OPEN = new BracketToken(Bracket.ROUND, Type.OPEN);
	public static final BracketToken ROUND_CLOSED = new BracketToken(Bracket.ROUND, Type.CLOSED);
	public static final BracketToken CURLEY_OPEN = new BracketToken(Bracket.CURLY, Type.OPEN);
	public static final BracketToken CURLEY_CLOSED = new BracketToken(Bracket.CURLY, Type.CLOSED);
	public static final BracketToken SQUARE_OPEN = new BracketToken(Bracket.SQUARE, Type.OPEN);
	public static final BracketToken SQUARE_CLOSED = new BracketToken(Bracket.SQUARE, Type.CLOSED);

	private Bracket bracket;
	private Type type;

	BracketToken(Bracket bracket, Type type) {
		this.bracket = bracket;
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BracketToken) {
			BracketToken b = (BracketToken) obj;
			return getType() == b.getType() && getBracket() == b.getBracket();
		}
		return super.equals(obj);
	}

	public Bracket getBracket() {
		return bracket;
	}

	public Type getType() {
		return type;
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

	public static class Generator implements TokenGenerator {

		private static final Character[] BRACKETS = new Character[]{'(', ')', '{', '}', '[', ']'};

		@Override
		public int accepts(String input, int index) {
			char firstCharacter = input.charAt(index);
			for (Character bracket : BRACKETS) {
				if (firstCharacter == bracket) {
					return index + 1;
				}
			}
			return index;
		}

		@Override
		public Token generate(String input, int startIndex, int endIndex) throws ParsingException {
			switch (input.charAt(startIndex)) {
				case '(':
					return new BracketToken(Bracket.ROUND, Type.OPEN);
				case ')':
					return new BracketToken(Bracket.ROUND, Type.CLOSED);
				case '[':
					return new BracketToken(Bracket.SQUARE, Type.OPEN);
				case ']':
					return new BracketToken(Bracket.SQUARE, Type.CLOSED);
				case '{':
					return new BracketToken(Bracket.CURLY, Type.OPEN);
				case '}':
					return new BracketToken(Bracket.CURLY, Type.CLOSED);
				default:
					// TODO: Better exception.
					throw new ParsingException(0, "Invalid");
			}
		}
	}
}