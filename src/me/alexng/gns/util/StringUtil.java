package me.alexng.gns.util;

import java.util.List;

public class StringUtil {

	public static String indent(String input) {
		return "\t" + input.replaceAll("\n", "\n\t");
	}

	public static String unrollList(List<?> list) {
		StringBuilder s = new StringBuilder();
		for (Object o : list) {
			s.append(o.toString()).append("\n");
		}
		return s.deleteCharAt(s.length() - 1).toString();
	}
}
