package me.alexng.gns.lexer.tokens;

import me.alexng.gns.GNSException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.Token;

public class ExpressionToken extends Token {

	private Token expression;

	public ExpressionToken(Token expression, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.expression = expression;
	}

	@Override
	public Value execute(Scope scope) throws GNSException {
		return expression.execute(scope);
	}

	@Override
	public String toString() {
		return "<Expression " + expression.toString() + ">";
	}
}
