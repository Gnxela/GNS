package me.alexng.gns.env.value;

import me.alexng.gns.RuntimeException;

public interface ValueDescriptor<T> {

	/**
	 * @return a string that identifies values of this type.
	 */
	String getTypeString();

	/**
	 * Casts {@code value} to {@link T}
	 *
	 * @throws RuntimeException when the value can not be cast
	 */
	T castTo(Value value) throws RuntimeException;


}
