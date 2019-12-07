package me.alexng.gns.tokens;

import me.alexng.gns.lexer.Token;

import java.util.Arrays;

public class ParametersToken extends Token {

    private IdentifierToken[] parameters;

    public ParametersToken(IdentifierToken[] parameters, int startIndex, int endIndex) {
        super(startIndex, endIndex);
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
