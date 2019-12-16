package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;

public class IdentifiedToken extends Token {

	private IdentifierToken identifier;

	public IdentifiedToken(IdentifierToken identifier, FileIndex fileIndex) {
		super(fileIndex);
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
