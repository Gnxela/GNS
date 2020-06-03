package me.alexng.gns.util;

import me.alexng.gns.tokens.FunctionToken;
import me.alexng.gns.tokens.Token;
import me.alexng.gns.tokens.value.ObjectValue;
import me.alexng.gns.tokens.value.Value;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ObjectToStringCrawler implements Crawler<String> {

	private static final char SPACE = ' ';
	private static final char COLON = ':';
	private static final char NEWLINE = '\n';
	private static final char COMMA = ',';
	private static final char INDENT = '\t';

	private StringBuilder stringBuilder = new StringBuilder();
	private int currentIndentation = 1;

	public ObjectToStringCrawler() {
		stringBuilder.append("{\n");
	}

	@Override
	public void crawl(Collection<Map.Entry<String, Token>> entries) {
		Iterator<Map.Entry<String, Token>> entryIterator = entries.iterator();
		while (entryIterator.hasNext()) {
			Map.Entry<String, Token> entry = entryIterator.next();
			String key = entry.getKey();
			Token value = entry.getValue();
			indent(stringBuilder);
			stringBuilder.append(key);
			stringBuilder.append(COLON);
			stringBuilder.append(SPACE);
			if (value instanceof Value) {
				if (value instanceof ObjectValue) {
					ObjectValue objectValue = (ObjectValue) value;
					stringBuilder.append("{\n");
					currentIndentation++;
					objectValue.getObjectScope().crawl(this);
					currentIndentation--;
					indent(stringBuilder);
					stringBuilder.append("}");
				} else {
					stringBuilder.append(((Value) value).getJavaValue().toString());
				}
			} else if (value instanceof FunctionToken) {
				FunctionToken functionToken = (FunctionToken) value;
				stringBuilder.append("func(");
				stringBuilder.append(functionToken.getParameterNamesCommaSeparated());
				stringBuilder.append(")");
			}
			if (entryIterator.hasNext()) {
				stringBuilder.append(COMMA);
			}
			stringBuilder.append(NEWLINE);
		}
	}

	private void indent(StringBuilder stringBuilder) {
		for (int i = 0; i < currentIndentation; i++) {
			stringBuilder.append(INDENT);
		}
	}

	@Override
	public String destroy() {
		stringBuilder.append("}");
		return stringBuilder.toString();
	}
}
