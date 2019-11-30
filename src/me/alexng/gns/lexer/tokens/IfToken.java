package me.alexng.gns.lexer.tokens;

import me.alexng.gns.GNSException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.util.StringUtil;

public class IfToken extends Token {

	private Token condition;
	private BlockToken block;

	public IfToken(Token condition, BlockToken block, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.condition = condition;
		this.block = block;
	}

	@Override
	public Value execute(Scope scope) throws GNSException {
		Value conditionValue = condition.execute(scope);
		// TODO: Check condition value
		block.execute(scope);
		return Value.NULL;
	}

	@Override
	public String toString() {
		return "<If \n" + StringUtil.indent(condition.toString()) + ", \n" + StringUtil.indent(block.toString()) + ">";
	}
}
