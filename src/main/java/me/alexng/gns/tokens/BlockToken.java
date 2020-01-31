package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.ReturnedValue;
import me.alexng.gns.env.value.Value;
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
	 * @return a returned value class, wrapping the returned value. Or {@link Value#NULL} if no value is returned.
	 */
	public Value executeBlock(Scope parentScope) throws RuntimeException {
		return executeBlockWithScope(parentScope.createChildScope());
	}

	/**
	 * Executes the block with the given local scope.
	 *
	 * @param localScope the local scope that the block will be executed with
	 * @return a returned value class, wrapping the returned value. Or {@link Value#NULL} if no value is returned.
	 */
	public Value executeBlockWithScope(Scope localScope) throws RuntimeException {
		for (Token token : tokens) {
			if (token instanceof EOLToken) {
				continue;
			}
			Value value = token.execute(localScope);
			if (value instanceof ReturnedValue) {
				return value;
			}
		}
		return Value.NULL;
	}

	@Override
	public String toString() {
		return "<Block \n" + StringUtil.indent(StringUtil.unrollList(tokens)) + ">";
	}
}
