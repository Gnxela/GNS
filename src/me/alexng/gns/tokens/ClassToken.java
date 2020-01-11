package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.scope.Scope;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.env.value.ObjectValue;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.util.StringUtil;

public class ClassToken extends IdentifiedToken {

	private static final IdentifierToken OBJECT_ID_VARIABLE = new IdentifierToken("objectId", FileIndex.INTERNAL_INDEX);
	private static final IdentifierToken TYPE_VARIABLE = new IdentifierToken("type", FileIndex.INTERNAL_INDEX);
	private static final IdentifierToken CONSTRUCTOR_NAME = new IdentifierToken("init", FileIndex.INTERNAL_INDEX);

	private BlockToken block;

	public ClassToken(IdentifierToken identifier, BlockToken block, FileIndex fileIndex) {
		super(identifier, fileIndex);
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
			objectScope = callingScope.createObjectScope(callingScope.getObjectScope());
		} else {
			objectScope = callingScope.createObjectScope();
		}
		setObjectProperties(objectScope);
		block.executeBlockWithScope(objectScope);
		callConstructor(caller, values, objectScope);
		return new ObjectValue(this, objectScope);
	}

	private void setObjectProperties(Scope objectScope) {
		// TODO: Set these values as immutable.
		objectScope.variableProvider.setLocal(OBJECT_ID_VARIABLE, new NumberValue(objectScope.getEnvironment().incrementObjectId()));
		// TODO: We want to use something other than the Java object hash code.
		objectScope.variableProvider.setLocal(TYPE_VARIABLE, new NumberValue(hashCode()));
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
