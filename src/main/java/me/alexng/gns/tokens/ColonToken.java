package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;

public class ColonToken extends Token {

	public ColonToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	@Override
	public String toString() {
		return "<Colon >";
	}
}