package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;

public abstract class IdentifiedToken extends Token implements CharSequence {

	private IdentifierToken identifier;

	public IdentifiedToken(IdentifierToken identifier, FileIndex fileIndex) {
		super(fileIndex);
		this.identifier = identifier;
	}

	@Override
	public int length() {
		return identifier.getName().length();
	}

	@Override
	public char charAt(int i) {
		return identifier.getName().charAt(i);
	}

	@Override
	public CharSequence subSequence(int i, int i1) {
		return identifier.getName().subSequence(i, i1);
	}

	public IdentifierToken getIdentifier() {
		return identifier;
	}
}
