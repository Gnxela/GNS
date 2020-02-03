package me.alexng.gns;

public class Options {

	private final boolean usingStandardLib;

	private Options(boolean usingStandardLib) {
		this.usingStandardLib = usingStandardLib;
	}

	public boolean isUsingStandardLib() {
		return usingStandardLib;
	}

	public static class Builder {

		private boolean usingStandardLib = true;

		public Builder setUsingStandardLib(boolean usingStandardLib) {
			this.usingStandardLib = usingStandardLib;
			return this;
		}

		public Options build() {
			return new Options(usingStandardLib);
		}
	}
}
