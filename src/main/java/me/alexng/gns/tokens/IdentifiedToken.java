package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;

public abstract class IdentifiedToken extends Token {

	private IdentifierToken identifier;

	public IdentifiedToken(IdentifierToken identifier, FileIndex fileIndex) {
		super(fileIndex);
		this.identifier = identifier;
	}

	public IdentifierToken getIdentifier() {
		return identifier;
	}

	public void setIdentifier(IdentifierToken identifier) {
		this.identifier = identifier;
	}
}
