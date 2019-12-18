package me.alexng.gns.tokens;

import me.alexng.gns.FileIndex;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.ObjectValue;
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
		scope.addClass(this);
		return Value.NULL;
	}

	public ObjectValue createInstance(ObjectConstructionToken caller, Value[] values, Scope scope) throws RuntimeException {
        Scope objectScope = scope.createObjectScope();
        block.executeBlockWithScope(objectScope);
        callConstructor(caller, values, objectScope);
        return new ObjectValue(this, objectScope);
    }

	private void callConstructor(ObjectConstructionToken caller, Value[] values, Scope objectScope) throws RuntimeException {
		FunctionToken constructor = objectScope.getLocalFunction("construct");
		if (constructor != null) {
			constructor.executeFunction(caller, objectScope, values);
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
