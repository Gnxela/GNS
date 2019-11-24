package me.alexng.gns.lexer;

public abstract class Token {

	private int startIndex, endIndex;
	
	public Token(int startIndex, int endIndex) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}
	
	@Override
	public abstract String toString();
}
