package me.alexng.gns.util;

public class ArrayUtil {

	public static <T> boolean arrayContains(T[] array, T needle) {
		for (T t : array) {
			if (needle.equals(t)) {
				return true;
			}
		}
		return false;
	}
}
