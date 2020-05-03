package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.value.BooleanValue;
import me.alexng.gns.env.value.Value;

public class WhileToken extends Token {

	private ExpressionToken conditionToken;
	private BlockToken blockToken;

	public WhileToken(ExpressionToken conditionToken, BlockToken blockToken, FileIndex fileIndex) {
		super(fileIndex);
		this.conditionToken = conditionToken;
		this.blockToken = blockToken;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Scope loopScope = scope.createChildScope();
		while (conditionToken.execute(loopScope) == BooleanValue.TRUE) {
			blockToken.executeBlock(loopScope);
		}
		return Value.NULL;
	}

	@Override
	public String toString() {
		return "<For " + conditionToken.toString() + ">";
	}
}
