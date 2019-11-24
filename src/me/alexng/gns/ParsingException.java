package me.alexng.gns;

public class ParsingException extends Exception {

	public ParsingException(int index, String message) {
		super("Parsing error at index " + index + ". Message: " + message);
	}
}
