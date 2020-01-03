package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.tokens.Token;

public abstract class BinaryOperatorToken<L extends Token, R extends Token> extends OperatorToken {

	private L left;
	private R right;

	protected BinaryOperatorToken(FileIndex fileIndex) {
		super(fileIndex);
	}

	public abstract void checkOperands(Token left, Token right) throws ParsingException;

	public void bind(L left, R right) {
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
	public Token[] getOperands() {
		return new Token[]{left, right};
	}
}
