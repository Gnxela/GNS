package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.tokens.value.Value;

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
		Value condition = conditionToken.execute(loopScope);
		while (condition.getType() == Value.Type.BOOLEAN && (Boolean) condition.getJavaValue()) {
			blockToken.executeBlock(loopScope);
			condition = conditionToken.execute(loopScope);
		}
		return NullValue.INTERNAL;
	}

	@Override
	public String toString() {
		return "<For " + conditionToken.toString() + ">";
	}
}
