package me.alexng.gns;

public class ParsingException extends Exception {

	public ParsingException(int line, String message) {
		super("Parsing error at line " + line + ". Message: " + message);
	}
}
