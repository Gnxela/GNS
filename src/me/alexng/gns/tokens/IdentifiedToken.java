package me.alexng.gns.tokens;

public class IdentifiedToken extends Token {

	private IdentifierToken identifier;

	public IdentifiedToken(IdentifierToken identifier, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return "THIS SHOULD NOT HAPPEN " + identifier.toString();
	}

	public IdentifierToken getIdentifier() {
		return identifier;
	}
}
