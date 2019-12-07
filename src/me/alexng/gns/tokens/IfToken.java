package me.alexng.gns.tokens;

import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.lexer.Token;
import me.alexng.gns.util.StringUtil;

public class IfToken extends Token {

	private ExpressionToken condition;
	private BlockToken block;

	public IfToken(ExpressionToken condition, BlockToken block, int startIndex, int endIndex) {
		super(startIndex, endIndex);
		this.condition = condition;
		this.block = block;
	}

	@Override
    public Value execute(Scope scope) throws RuntimeException {
        Value conditionValue = condition.execute(scope);
        if (conditionValue.getType() != Value.Type.BOOLEAN) {
            throw new RuntimeException(this, "If condition expected to be of type BOOLEAN, was type" + conditionValue.getType().toString());
        }
        if ((Boolean) conditionValue.getValue()) {
            block.executeBlock(scope);
        }
        return Value.NULL;
    }

	@Override
	public String toString() {
		return "<If \n" + StringUtil.indent(condition.toString()) + ", \n" + StringUtil.indent(block.toString()) + ">";
	}
}
