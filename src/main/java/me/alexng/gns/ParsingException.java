package me.alexng.gns;

import me.alexng.gns.tokens.Token;

public class ParsingException extends GNSException {

	public ParsingException(FileIndex fileIndex, String message) {
		super(fileIndex, fileIndex.getSourceFile() + ": Parsing error at index " + fileIndex.getStartIndex() + ". Message: " + message);
	}

	public ParsingException(Token token, String message) {
		this(token.getFileIndex(), message);
	}
}
