package me.alexng.gns.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;

public class FunctionCallToken extends IdentifiedToken {

    private Token[] valueTokens;

    public FunctionCallToken(IdentifierToken identifier, Token[] valueTokens, int startIndex, int endIndex) {
        super(identifier, startIndex, endIndex);
        this.valueTokens = valueTokens;
    }

    @Override
    public Value execute(Scope scope) throws RuntimeException {
        Value[] values = grabValues(scope);
        return scope.getFunction(this).executeFunction(this, values);
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
