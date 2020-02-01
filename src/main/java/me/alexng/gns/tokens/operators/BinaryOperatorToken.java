package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.Token;

public abstract class BinaryOperatorToken<L extends Token, R extends Token> extends OperatorToken {

	private L left;
	private R right;

	protected BinaryOperatorToken(String operatorString, FileIndex fileIndex) {
		super(operatorString, fileIndex);
	}

	public abstract void checkOperands(Token left, Token right) throws ParsingException;

	public void bind(L left, R right) throws ParsingException {
		super.bind();
		this.left = left;
		this.right = right;
		this.setFileIndex(FileIndex.openClose(left, right));
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}

	@Override
	public Value[] getOperands(Scope scope) throws RuntimeException {
		return new Value[]{left.execute(scope), right.execute(scope)};
	}
}
