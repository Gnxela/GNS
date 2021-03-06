package me.alexng.gns;

import me.alexng.gns.tokens.Token;

import java.util.LinkedList;

public class FileIndex {

	public static final FileIndex NULL_INDEX = new FileIndex("<NULL>", 0, 0);
	public static final FileIndex INTERNAL_INDEX = new FileIndex("<INTERNAL>", 0, 0);

	private String sourceFile;
	private int startIndex, endIndex;

	public FileIndex(String sourceFile, int startIndex, int endIndex) {
		this.sourceFile = sourceFile;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public static FileIndex unindexedFile(String file) {
		return new FileIndex(file, 0, 0);
	}

	public static FileIndex wrap(LinkedList<? extends Token> tokens) throws ParsingException {
		String sourceFile = tokens.getFirst().getFileIndex().sourceFile;
		if (tokens.stream().anyMatch(token -> !token.getFileIndex().getSourceFile().equals(sourceFile))) {
			throw new ParsingException(tokens.getFirst(), "Can not merge FileIndex's from different files");
		}
		return openClose(tokens.getFirst(), tokens.getLast());
	}

	public static FileIndex openClose(Token open, Token close) throws ParsingException {
		if (!open.getFileIndex().getSourceFile().equals(close.getFileIndex().getSourceFile())) {
			throw new ParsingException(open, "Can not merge FileIndex's from different files");
		}
		return new FileIndex(open.getFileIndex().getSourceFile(), open.getFileIndex().getStartIndex(), close.getFileIndex().getEndIndex());
	}

	public String substring(String source) {
		return source.substring(startIndex, endIndex);
	}

	public FileIndex morph(int startDelta, int endDelta) {
		return new FileIndex(sourceFile, startIndex + startDelta, endIndex + endDelta);
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
