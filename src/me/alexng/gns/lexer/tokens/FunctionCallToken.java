package me.alexng.gns.lexer.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.Token;

public class FunctionCallToken extends Token {

    private IdentifierToken functionIdentifier;
    private Token[] valueTokens;

    public FunctionCallToken(IdentifierToken functionIdentifier, Token[] valueTokens, int startIndex, int endIndex) {
        super(startIndex, endIndex);
        this.functionIdentifier = functionIdentifier;
        this.valueTokens = valueTokens;
    }

    @Override
    public Value execute(Scope scope) throws RuntimeException {
        Value[] values = grabValues(scope);
        return scope.getFunction(functionIdentifier).executeFunction(values);
    }

    private Value[] grabValues(Scope scope) throws RuntimeException {
        Value[] values = new Value[valueTokens.length];
        for (int i = 0; i < valueTokens.length; i++) {
            values[i] = valueTokens[i].execute(scope);
        }
        return values;
    }

    @Override
    public String toString() {
        return "<FunctionCall >";
    }
}
