package me.alexng.gns.bridge;

import me.alexng.gns.FileIndex;
import me.alexng.gns.ParsingException;
import me.alexng.gns.env.value.Value;
import me.alexng.gns.tokens.ClassToken;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BridgeMapper {

	public static ClassToken mapBridge(Class<?> bridgeClass) throws ParsingException {
		// TODO: Allow for bridges to define a name different from class name
		BridgeClassToken bridgeClassToken = new BridgeClassToken(bridgeClass);
		bridgeVariables(bridgeClassToken);

		return bridgeClassToken;
	}

	private static void bridgeVariables(BridgeClassToken bridgeClassToken) throws ParsingException {
		Map<String, Field> fields = new HashMap<>();
		for (Field field : bridgeClassToken.getBridgeClass().getFields()) {
			if (field.isAnnotationPresent(Expose.class)) {
				checkFieldType(field);
				fields.put(field.getName(), field);
			}
		}
		bridgeClassToken.setVariables(fields);
	}

	private static void checkFieldType(Field field) throws ParsingException {
		if (Value.class.isAssignableFrom(field.getType())) {
			return;
		}
		// TODO: Make it clear where this is being thrown.
		throw new ParsingException(FileIndex.INTERNAL_INDEX, "Only variables with superclass me.alexng.gns.env.value.Value can be exposed");
	}
}
