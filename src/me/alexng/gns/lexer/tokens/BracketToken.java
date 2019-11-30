package me.alexng.gns.lexer.tokens;

import me.alexng.gns.GNSException;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.lexer.TokenGenerator;

public class BracketToken extends Token {

	public static final BracketToken ROUND_OPEN = new BracketToken(Bracket.ROUND, Type.OPEN, 0, 0);
	public static final BracketToken ROUND_CLOSED = new BracketToken(Bracket.ROUND, Type.CLOSED, 0, 0);
	public static final BracketToken CURLEY_OPEN = new BracketToken(Bracket.CURLY, Type.OPEN, 0, 0);
	public static final BracketToken CURLEY_CLOSED = new BracketToken(Bracket.CURLY, Type.CLOSED, 0, 0);
	public static final BracketToken SQUARE_OPEN = new BracketToken(Bracket.SQUARE, Type.OPEN, 0, 0);
	public static final BracketToken SQUARE_CLOSED = new BracketToken(Bracket.SQUARE, Type.CLOSED, 0, 0);

	private Bracket bracket;
	private Type type;

	private BracketToken(Bracket bracket, Type type, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.bracket = bracket;
		this.type = type;
	}

	@Override
	public boolean matches(Token token) {
		if (token instanceof BracketToken) {
			BracketToken b = (BracketToken) token;
			return getType() == b.getType() && getBracket() == b.getBracket();
		}
		return super.equals(token);
	}

	private Bracket getBracket() {
		return bracket;
	}

	private Type getType() {
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
		public Token generate(String input, int startIndex, int endIndex) throws GNSException {
			switch (input.charAt(startIndex)) {
				case '(':
					return new BracketToken(Bracket.ROUND, Type.OPEN, startIndex, endIndex);
				case ')':
					return new BracketToken(Bracket.ROUND, Type.CLOSED, startIndex, endIndex);
				case '[':
					return new BracketToken(Bracket.SQUARE, Type.OPEN, startIndex, endIndex);
				case ']':
					return new BracketToken(Bracket.SQUARE, Type.CLOSED, startIndex, endIndex);
				case '{':
					return new BracketToken(Bracket.CURLY, Type.OPEN, startIndex, endIndex);
				case '}':
					return new BracketToken(Bracket.CURLY, Type.CLOSED, startIndex, endIndex);
				default:
					// TODO: Better exception.
					throw new GNSException(0, "Invalid");
			}
		}
	}
}