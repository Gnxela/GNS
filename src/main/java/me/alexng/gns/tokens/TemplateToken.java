package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.tokens.value.ObjectValue;
import me.alexng.gns.tokens.value.Value;
import me.alexng.gns.util.StringUtil;

public class TemplateToken extends IdentifiedToken {

	private static final IdentifierToken CONSTRUCTOR_NAME = new IdentifierToken("init", FileIndex.INTERNAL_INDEX);

	private BlockToken block;

	public TemplateToken(IdentifierToken identifier, BlockToken block, FileIndex fileIndex) {
		super(identifier, fileIndex);
		this.block = block;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		scope.set(this, this);
		return NullValue.INTERNAL;
	}

	public ObjectValue createInstance(TemplateConstructionToken caller, Value[] values, Scope callingScope) throws RuntimeException {
		Scope objectScope, parentScope;
		try {
			parentScope = callingScope.getObjectScope();
		} catch (RuntimeException ignored) {
			// Thrown when no object scope if found
			parentScope = callingScope.getGlobalScope();
		}
		objectScope = Scope.createObjectScope(parentScope);
		block.executeBlockWithScope(objectScope);
		callConstructor(caller, values, objectScope);
		return new ObjectValue(objectScope, caller.getFileIndex());
	}

	private void callConstructor(TemplateConstructionToken caller, Value[] values, Scope objectScope) throws RuntimeException {
		FunctionToken constructor = objectScope.getFunction(CONSTRUCTOR_NAME);
		if (constructor != null) {
			Value returnedValue = constructor.executeFunction(caller, objectScope, values);
			if (returnedValue != null && returnedValue.getType() != Value.Type.NULL) {
				throw new RuntimeException(constructor.getFileIndex(), "Invalid value return.");
			}
			return;
		}
		if (values.length != 0) {
			throw new RuntimeException(caller, "Unexpected constructor arguments");
		}
	}

	@Override
	public String toString() {
		return "<Class " + getIdentifier().getName()
				+ StringUtil.indent(block.toString())
				+ ">";
	}
}
