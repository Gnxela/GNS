package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;

import java.util.Arrays;

public class ParametersToken extends Token {

	private IdentifierToken[] parameters;

	public ParametersToken(IdentifierToken[] parameters, FileIndex fileIndex) {
		super(fileIndex);
		this.parameters = parameters;
	}

	public IdentifierToken[] getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return "<ParametersToken " + Arrays.toString(parameters) + ">";
	}
}
