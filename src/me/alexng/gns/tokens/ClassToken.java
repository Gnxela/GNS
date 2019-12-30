package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.NumberValue;
import me.alexng.gns.env.ObjectValue;
import me.alexng.gns.env.Scope;
import me.alexng.gns.env.Value;
import me.alexng.gns.util.StringUtil;

import static me.alexng.gns.env.Value.NULL;

public class ClassToken extends IdentifiedToken {

	private static final String OBJECT_ID_VARIABLE = "objectId";
	private static final String TYPE_VARIABLE = "type";
	private static final String CONSTRUCTOR_NAME = "init";

	private BlockToken block;

	public ClassToken(IdentifierToken identifier, BlockToken block, FileIndex fileIndex) {
		super(identifier, fileIndex);
		this.block = block;
	}

	@Override
	public Value execute(Scope scope) throws RuntimeException {
		scope.addClass(this);
		return NULL;
	}

	public ObjectValue createInstance(ObjectConstructionToken caller, Value[] values, Scope scope) throws RuntimeException {
		Scope objectScope = scope.createObjectScope();
		setObjectProperties(objectScope);
		block.executeBlockWithScope(objectScope);
		callConstructor(caller, values, objectScope);
		return new ObjectValue(this, objectScope);
	}

	private void setObjectProperties(Scope objectScope) {
		// TODO: Set these values as immutable.
		objectScope.setLocalVariable(OBJECT_ID_VARIABLE, new NumberValue(objectScope.getEnvironment().incrementObjectId()));
		// TODO: We want to use something other than the Java object hash code.
		objectScope.setLocalVariable(TYPE_VARIABLE, new NumberValue(this.hashCode()));
	}

	private void callConstructor(ObjectConstructionToken caller, Value[] values, Scope objectScope) throws RuntimeException {
		FunctionToken constructor = objectScope.getLocalFunction(CONSTRUCTOR_NAME);
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
