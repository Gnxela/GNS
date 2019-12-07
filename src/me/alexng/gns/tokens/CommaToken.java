package me.alexng.gns.tokens;

public class CommaToken extends Token {

	public CommaToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

	@Override
	public String toString() {
		return "<Comma >";
	}
}