package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.util.StringUtil;

public class ClassToken extends IdentifiedToken {

	private BlockToken block;

	public ClassToken(IdentifierToken identifier, BlockToken block, FileIndex fileIndex) {
		super(identifier, fileIndex);
		this.block = block;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		// TODO: Add class to the current scope
		return Value.NULL;
	}

	@Override
	public String toString() {
		return "<Class " + getIdentifier().getName()
				+ StringUtil.indent(block.toString())
				+ ">";
	}
}
