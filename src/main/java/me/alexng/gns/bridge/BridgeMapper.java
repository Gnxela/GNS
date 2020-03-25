package me.alexng.gns.bridge;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.env.Environment;
import me.alexng.gns.env.value.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BridgeMapper {

	private static final String BASE_TYPE_STRING = "me.alexng.gns.env.value.Value";

	public static BridgeClassToken mapBridge(Class<?> bridgeClass) throws ParsingException {
		// TODO: Allow for bridges to define a name different from class name
		BridgeClassToken bridgeClassToken = new BridgeClassToken(bridgeClass);
		bridgeVariables(bridgeClassToken);
		bridgeFunctions(bridgeClassToken);
		return bridgeClassToken;
	}

	private static void bridgeFunctions(BridgeClassToken bridgeClassToken) throws ParsingException {
		LinkedList<Method> functions = new LinkedList<>();
		for (Method method : bridgeClassToken.getBridgeClass().getMethods()) {
			if (method.isAnnotationPresent(Expose.class)) {
				checkMethodTypes(method);
				functions.add(method);
			}
		}
		bridgeClassToken.setFunctions(functions);
	}

	private static void bridgeVariables(BridgeClassToken bridgeClassToken) throws ParsingException {
		Map<String, Field> fields = new HashMap<>();
		for (Field field : bridgeClassToken.getBridgeClass().getFields()) {
			if (field.isAnnotationPresent(Expose.class)) {
				checkField(field);
				fields.put(field.getName(), field);
			}
		}
		bridgeClassToken.setVariables(fields);
	}

	private static void checkMethodTypes(Method method) throws ParsingException {
		if (method.getReturnType() != Void.TYPE && !Value.class.isAssignableFrom(method.getReturnType())) {
			throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only methods with return type " + BASE_TYPE_STRING + " can be exposed: " + method.getName());
		}
		Class<?>[] types = method.getParameterTypes();
		for (int i = 0; i < types.length; i++) {
			Class<?> type = types[i];
			if (i == 0) {
				if (!Environment.class.isAssignableFrom(type)) {
					throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only methods with return type " + BASE_TYPE_STRING + " can be exposed: " + method.getName());
				}
			} else {
				if (!Value.class.isAssignableFrom(type)) {
					throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only methods with return type " + BASE_TYPE_STRING + " can be exposed: " + method.getName());
				}
			}
		}
	}

	private static void checkField(Field field) throws ParsingException {
		int modifiers = field.getModifiers();
		if (!Modifier.isPublic(modifiers) && !field.isAccessible()) {
			throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only accessible variables can be exposed: " + field.getName());
		}
		if (!Value.class.isAssignableFrom(field.getType())) {
			// TODO: Make it clear where this is being thrown.
			throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only variables with superclass " + BASE_TYPE_STRING + " can be exposed: " + field.getName());
		}
	}
}
