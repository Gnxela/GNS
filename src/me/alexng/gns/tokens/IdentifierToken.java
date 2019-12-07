package me.alexng.gns.tokens;

public class IdentifierToken extends Token {

	private String name;

	public IdentifierToken(String name, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "<Identifier " + name + ">";
	}
}