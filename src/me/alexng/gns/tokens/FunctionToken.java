package me.alexng.gns.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.Token;

public class FunctionToken extends Token {

	private IdentifierToken identifier;
	private ParametersToken parameters;
	private BlockToken block;

	public FunctionToken(IdentifierToken identifier, ParametersToken parametersToken, BlockToken block, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.identifier = identifier;
        this.parameters = parametersToken;
        this.block = block;
    }

    @Override
    public Value execute(Scope scope) throws RuntimeException {
        scope.addFunction(this);
        return Value.NULL;
    }

    public Value executeFunction(Value[] values) throws RuntimeException {
        IdentifierToken[] identifiers = parameters.getParameters();
        if (identifiers.length != values.length) {
            throw new RuntimeException(this, "Invalid number of arguments. Expected: " + identifiers.length + ". Got: " + values.length);
        }

        // TODO: We should pass the global scope here instead of null.
        Scope functionScope = new Scope(null);
        for (int i = 0; i < identifiers.length; i++) {
            functionScope.setVariable(identifiers[i], values[i]);
        }

        block.executeBlock(functionScope);
        // TODO: Function return values
        return Value.NULL;
    }

    public String getName() {
        return identifier.getName();
    }

    @Override
    public String toString() {
        return "<Function " + identifier.getName() + ">";
    }
}
