package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;

public class CommaToken extends Token {

	public CommaToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	@Override
	public String toString() {
		return "<Comma >";
	}
}