package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Value;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.util.StringUtil;

public class IfToken extends Token {

	private ExpressionToken condition;
	private BlockToken block;

	public IfToken(ExpressionToken condition, BlockToken block, FileIndex fileIndex) {
		super(fileIndex);
		this.condition = condition;
		this.block = block;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		Value conditionValue = condition.execute(scope);
		if (conditionValue.getType() != Value.Type.BOOLEAN) {
			throw new RuntimeException(this, "If condition expected to be of type BOOLEAN, was type" + conditionValue.getType().toString());
		}
		if ((Boolean) conditionValue.getJavaValue()) {
			return block.executeBlock(scope);
		}
		return Value.NULL;
	}

	@Override
	public String toString() {
		return "<If \n" + StringUtil.indent(condition.toString()) + ", \n" + StringUtil.indent(block.toString()) + ">";
	}
}
