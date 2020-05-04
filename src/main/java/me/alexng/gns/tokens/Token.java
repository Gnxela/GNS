package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.value.Value;

public abstract class Token {

	private FileIndex fileIndex;

	protected Token(FileIndex fileIndex) {
		this.fileIndex = fileIndex;
	}

	public Value execute(Scope scope) throws RuntimeException {
		throw new RuntimeException(this, "This token " + toString() + " can not be run");
	}

	public boolean matches(Token token) {
		return false;
	}

	public FileIndex getFileIndex() {
		return fileIndex;
	}

	public void setFileIndex(FileIndex fileIndex) {
		this.fileIndex = fileIndex;
	}

	@Override
	public abstract String toString();
}
