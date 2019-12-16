package me.alexng.gns;

import me.alexng.gns.tokens.Token;

import java.util.LinkedList;

public class FileIndex {

	public static final FileIndex NULL_INDEX = new FileIndex("<NULL>", 0, 0);

	private String sourceFile;
	private int startIndex, endIndex;

	public FileIndex(String sourceFile, int startIndex, int endIndex) {
		this.sourceFile = sourceFile;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public static final FileIndex wrap(LinkedList<? extends Token> tokens) {
		return openClose(tokens.getFirst(), tokens.getLast());
	}

	public static final FileIndex openClose(Token open, Token close) {
		return new FileIndex(open.getFileIndex().getSourceFile(), open.getFileIndex().getStartIndex(), close.getFileIndex().getEndIndex());
	}

	public String substring(String source) {
		return source.substring(startIndex, endIndex);
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public String getSourceFile() {
		return sourceFile;
	}
}
