package me.alexng.gns.tokens;

public abstract class BindableToken extends Token {

	private boolean bound = false;

	protected BindableToken(int startIndex, int endIndex) {
		super(startIndex, endIndex);
	}

	public void bind() {
		this.bound = true;
	}

	public boolean isBound() {
		return bound;
	}
}
