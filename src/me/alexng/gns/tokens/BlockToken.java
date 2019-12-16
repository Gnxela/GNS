package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.util.StringUtil;

import java.util.List;

public class BlockToken extends Token {

	private List<Token> tokens;

	public BlockToken(List<Token> tokens, FileIndex fileIndex) {
		super(fileIndex);
		this.tokens = tokens;
	}

	/**
	 * Executes the block.
	 *
	 * @return The local scope of the block after it has finished executing.
	 */
	public Scope executeBlock(Scope parentScope) throws RuntimeException {
		Scope localScope = parentScope.createChildScope();
		executeBlockWithScope(localScope);
		return localScope;
	}

	/**
	 * Executes the block with the given local scope.
	 *
	 * @param localScope the local scope that the block will be executed with
	 */
	public void executeBlockWithScope(Scope localScope) throws RuntimeException {
		for (Token token : tokens) {
			if (token instanceof EOLToken) {
				continue;
			}
			token.execute(localScope);
		}
	}

	@Override
	public String toString() {
		return "<Block \n" + StringUtil.indent(StringUtil.unrollList(tokens)) + ">";
	}
}
