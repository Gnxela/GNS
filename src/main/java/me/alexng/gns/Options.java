package me.alexng.gns;

import java.io.InputStream;
import java.io.OutputStream;

public class Options {

	private final OutputStream stdout;
	private final InputStream stdin;
	private final boolean usingSys;

	public Options(OutputStream stdout, InputStream stdin, boolean usingSys) {
		this.stdout = stdout;
		this.stdin = stdin;
		this.usingSys = usingSys;
	}

	public static final Options createDefault() {
		return new Options.Builder().build();
	}

	public OutputStream getStdout() {
		return stdout;
	}

	public InputStream getStdin() {
		return stdin;
	}

	public boolean isUsingSys() {
		return usingSys;
	}

	public static class Builder {

		private OutputStream stdout = System.out;
		private InputStream stdin = System.in;
		private boolean usingSys = true;

		public void setStdout(OutputStream stdout) {
			this.stdout = stdout;
		}

		public void setStdin(InputStream stdin) {
			this.stdin = stdin;
		}

		public Builder setUsingSys(boolean usingSys) {
			this.usingSys = usingSys;
			return this;
		}

		public Options build() {
			return new Options(stdout, stdin, usingSys);
		}
	}
}
