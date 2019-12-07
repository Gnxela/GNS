package me.alexng.gns.tokens;

public class BracketToken extends Token {

	public static final BracketToken ROUND_OPEN = new BracketToken(Bracket.ROUND, Type.OPEN, 0, 0);
	public static final BracketToken ROUND_CLOSED = new BracketToken(Bracket.ROUND, Type.CLOSED, 0, 0);
	public static final BracketToken CURLEY_OPEN = new BracketToken(Bracket.CURLY, Type.OPEN, 0, 0);
	public static final BracketToken CURLEY_CLOSED = new BracketToken(Bracket.CURLY, Type.CLOSED, 0, 0);
	public static final BracketToken SQUARE_OPEN = new BracketToken(Bracket.SQUARE, Type.OPEN, 0, 0);
	public static final BracketToken SQUARE_CLOSED = new BracketToken(Bracket.SQUARE, Type.CLOSED, 0, 0);

	private Bracket bracket;
	private Type type;

	public BracketToken(Bracket bracket, Type type, int startIndex, int endIndex) {
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
}