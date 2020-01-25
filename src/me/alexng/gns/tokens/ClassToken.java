package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.env.value.ObjectValue;
import me.alexng.gns.env.value.StringValue;
import me.alexng.gns.env.value.Value;
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
		scope.classProvider.set(this);
		return Value.NULL;
	}

	public ObjectValue createInstance(ObjectConstructionToken caller, Value[] values, Scope callingScope) throws RuntimeException {
		Scope objectScope;
		if (callingScope.getObjectScope() != null && callingScope.getObjectScope().classProvider.getLocal(getIdentifier()) != null) {
			objectScope = callingScope.createObjectScope(getIdentifier().getName(), callingScope.getObjectScope());
		} else {
			objectScope = callingScope.createObjectScope(getIdentifier().getName());
		}
		int objectId = objectScope.getEnvironment().incrementObjectId();
		setObjectProperties(objectId, objectScope);
		block.executeBlockWithScope(objectScope);
		callConstructor(caller, values, objectScope);
		return new ObjectValue(objectId, this, objectScope);
	}

	private void setObjectProperties(int objectId, Scope objectScope) throws RuntimeException {
		// TODO: Set these values as immutable.
		objectScope.variableProvider.setLocal(OBJECT_ID_VARIABLE, new NumberValue(objectId));
		objectScope.variableProvider.setLocal(TYPE_VARIABLE, StringValue.createString(objectScope.nameProvider.getName(), objectScope));
	}

	private void callConstructor(ObjectConstructionToken caller, Value[] values, Scope objectScope) throws RuntimeException {
		FunctionToken constructor = objectScope.functionProvider.getLocal(CONSTRUCTOR_NAME);
		if (constructor != null) {
			Value returnedValue = constructor.executeFunction(caller, objectScope, values);
			if (returnedValue != null && returnedValue != Value.NULL) {
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
