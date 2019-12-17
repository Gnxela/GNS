package me.alexng.gns.util;

import me.alexng.gns.tokens.IdentifiedToken;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

	public static String unrollArrayInline(Object[] array) {
		StringBuilder s = new StringBuilder();
		for (Object o : array) {
			s.append(o.toString()).append(", ");
		}
		s.delete(s.length() - 2, s.length());
		return s.toString();
	}

	public static String unrollIdentifiedListInline(Collection<? extends IdentifiedToken> collection) {
		StringBuilder sb = new StringBuilder();
		for (IdentifiedToken entry : collection) {
			sb.append(entry.getIdentifier().getName());
			sb.append(", ");
		}
		sb.delete(sb.length() - 2, sb.length());
		return sb.toString();
	}

	public static String unrollIdentifiedMapInline(Map<?, ? extends IdentifiedToken> map) {
		return unrollIdentifiedListInline(map.values());
	}
}
