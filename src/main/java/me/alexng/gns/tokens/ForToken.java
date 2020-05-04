package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.BooleanValue;
import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.tokens.value.Value;

public class ForToken extends Token {

	private ArgumentsToken argumentsToken;
	private BlockToken blockToken;

	public ForToken(ArgumentsToken argumentsToken, BlockToken blockToken, FileIndex fileIndex) {
		super(fileIndex);
		this.argumentsToken = argumentsToken;
		this.blockToken = blockToken;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Scope loopScope = scope.createChildScope();
		Token[] arguments = argumentsToken.getArguments();
		Token initialize = arguments[0], condition = arguments[1], increment = arguments[2];
		initialize.execute(loopScope);
		while (condition.execute(loopScope).equals(BooleanValue.INTERNAL_TRUE)) {
			blockToken.executeBlock(loopScope);
			increment.execute(loopScope);
		}
		return NullValue.INTERNAL;
	}

	@Override
	public String toString() {
		return "<For " + argumentsToken.toString() + ">";
	}
}
