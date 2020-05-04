package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.value.NullValue;
import me.alexng.gns.tokens.value.ObjectValue;
import me.alexng.gns.tokens.value.Value;
import me.alexng.gns.util.ArrayUtil;
import me.alexng.gns.util.StringUtil;

public class ClassToken extends IdentifiedToken {

	// TODO: When we add modular values, load them here
	private static final String[] RESTRICTED_NAMES = new String[]{"number", "string", "boolean"};
	private static final IdentifierToken OBJECT_ID_VARIABLE = new IdentifierToken("objectId", FileIndex.INTERNAL_INDEX);
	private static final IdentifierToken CONSTRUCTOR_NAME = new IdentifierToken("init", FileIndex.INTERNAL_INDEX);
	public static final IdentifierToken TYPE_VARIABLE = new IdentifierToken("type", FileIndex.INTERNAL_INDEX);

	private BlockToken block;

	public ClassToken(IdentifierToken identifier, BlockToken block, FileIndex fileIndex) throws ParsingException {
		super(identifier, fileIndex);
		if (ArrayUtil.arrayContains(RESTRICTED_NAMES, identifier.getName())) {
			throw new ParsingException(this, "Restricted class name");
		}
		this.block = block;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		scope.set(this, this);
		return NullValue.INTERNAL;
	}

	public ObjectValue createInstance(ObjectConstructionToken caller, Value[] values, Scope callingScope) throws RuntimeException {
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
		return new ObjectValue(this, objectScope, caller.getFileIndex());
	}

	private void callConstructor(ObjectConstructionToken caller, Value[] values, Scope objectScope) throws RuntimeException {
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
