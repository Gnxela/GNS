package me.alexng.gns.bridge;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.RuntimeException;
import me.alexng.gns.env.Scope;
import me.alexng.gns.tokens.IdentifierToken;
import me.alexng.gns.tokens.ObjectConstructionToken;
import me.alexng.gns.tokens.TemplateToken;
import me.alexng.gns.tokens.value.ObjectValue;
import me.alexng.gns.tokens.value.Value;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class BridgeTemplateToken<T> extends TemplateToken {

	private Class<T> bridgeClass;
	Map<String, Field> variables;
	List<Method> functions;

	public BridgeTemplateToken(Class<T> bridgeClass) throws ParsingException {
		super(new IdentifierToken(bridgeClass.getSimpleName(), FileIndex.INTERNAL_INDEX), null, FileIndex.INTERNAL_INDEX);
		this.bridgeClass = bridgeClass;
	}

	@Override
	public ObjectValue createInstance(ObjectConstructionToken caller, Value[] values, Scope callingScope) throws RuntimeException {
		Object bridgeInstance = createBridgeInstance(values);
		Scope objectScope = BridgeScope.create(callingScope.getGlobalScope(), bridgeInstance, variables, functions);
		return new ObjectValue(this, objectScope, caller.getFileIndex());
	}

	/**
	 * Creates a Java Object of the {@link #bridgeClass}.
	 *
	 * @param values constructor values
	 * @return the created object
	 */
	T createBridgeInstance(Value[] values) throws RuntimeException {
		Class<?>[] valueTypes = new Class<?>[values.length];
		for (int i = 0; i < values.length; i++) {
			valueTypes[i] = values[i].getClass();
		}
		try {
			Constructor<T> constructor = bridgeClass.getConstructor(valueTypes);
			if (values.length > 0 && !constructor.isAnnotationPresent(Expose.class)) {
				throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Constructor is not exposed.");
			}
			return constructor.newInstance((Object[]) values);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(FileIndex.INTERNAL_INDEX, "Failed to create bridge instance:" + e.getClass() + ": " + e.getMessage());
		}
	}

	public Class<?> getBridgeClass() {
		return bridgeClass;
	}


	@Override
	public String toString() {
		return "<BridgeClass " + getIdentifier().getName() + ">";
	}
}
