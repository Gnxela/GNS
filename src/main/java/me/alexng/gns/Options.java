package me.alexng.gns;

import java.io.InputStream;
import java.io.OutputStream;

public class Options {

	private final OutputStream stdout;
	private final InputStream stdin;

	public Options(OutputStream stdout, InputStream stdin) {
		this.stdout = stdout;
		this.stdin = stdin;
	}

	public OutputStream getStdout() {
		return stdout;
	}

	public InputStream getStdin() {
		return stdin;
	}

	public static class Builder {

		private OutputStream stdout = System.out;
		private InputStream stdin = System.in;

		public void setStdout(OutputStream stdout) {
			this.stdout = stdout;
		}

		public void setStdin(InputStream stdin) {
			this.stdin = stdin;
		}

		public Options build() {
			return new Options(stdout, stdin);
		}
	}
}
