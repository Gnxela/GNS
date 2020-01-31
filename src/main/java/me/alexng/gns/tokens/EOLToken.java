package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;

public class EOLToken extends Token {

	public EOLToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	@Override
	public String toString() {
		return "<EOL >";
	}
}
