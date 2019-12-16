package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;

public abstract class BindableToken extends Token {

	private boolean bound = false;

	protected BindableToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	public void bind() {
		this.bound = true;
	}

	public boolean isBound() {
		return bound;
	}
}
