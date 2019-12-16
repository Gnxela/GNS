package me.alexng.gns;

import me.alexng.gns.tokens.Token;

public class RuntimeException extends GNSException {

	public RuntimeException(FileIndex fileIndex, String message) {
		super(fileIndex, fileIndex.getSourceFile() + ": Runtime error at index " + fileIndex.getStartIndex() + ". Message: " + message);
	}

	public RuntimeException(Token token, String message) {
		this(token.getFileIndex(), message);
	}
}
