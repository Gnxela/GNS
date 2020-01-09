package me.alexng.gns;

public class Options {

	// TODO: Make Options buildable, and as such immutable once built.

	private boolean usingStandardLib = true;

	public boolean isUsingStandardLib() {
		return usingStandardLib;
	}

	public Options setUsingStandardLib(boolean usingStandardLib) {
		this.usingStandardLib = usingStandardLib;
		return this;
	}
}
