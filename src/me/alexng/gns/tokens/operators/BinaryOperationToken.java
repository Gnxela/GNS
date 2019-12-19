package me.alexng.gns.tokens.operators;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.tokens.BindableToken;
import me.alexng.gns.tokens.Token;

public abstract class BinaryOperationToken<L extends Token, R extends Token> extends BindableToken {

	private L left;
	private R right;

	protected BinaryOperationToken(FileIndex fileIndex) {
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
}
