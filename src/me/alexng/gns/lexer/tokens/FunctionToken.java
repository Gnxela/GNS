package me.alexng.gns.lexer.tokens;

import me.alexng.gns.GNSException;
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
	public Value execute(Scope scope) throws GNSException {
		scope.addFunction(this);
		return Value.NULL;
	}

	@Override
	public String toString() {
		return "<Function " + identifier.getName() + ">";
	}
}
