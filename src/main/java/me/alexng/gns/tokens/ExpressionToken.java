package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.Value;

public class ExpressionToken extends Token {

	private Token expression;

	public ExpressionToken(Token expression, FileIndex fileIndex) {
		super(fileIndex);
		this.expression = expression;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		return expression.execute(scope);
	}

	@Override
	public String toString() {
		return "<Expression " + expression.toString() + ">";
	}
}
