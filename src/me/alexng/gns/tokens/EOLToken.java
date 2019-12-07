package me.alexng.gns.tokens;

public class EOLToken extends Token {

	public EOLToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

	@Override
	public String toString() {
		return "<EOL >";
	}
}
