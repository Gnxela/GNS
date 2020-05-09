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

	private StringBuilder stringBuilder = new StringBuilder();
	private String indentation = "\t";

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
			stringBuilder.append(indentation);
			stringBuilder.append(key);
			stringBuilder.append(COLON);
			stringBuilder.append(SPACE);
			if (value instanceof Value) {
				if (value instanceof ObjectValue) {
					ObjectValue objectValue = (ObjectValue) value;
					stringBuilder.append("{\n");
					indentation = indentation + "\t";
					objectValue.getObjectScope().crawl(this);
					indentation = indentation.substring(0, indentation.length() - 1);
					stringBuilder.append(indentation);
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

	@Override
	public String destroy() {
		stringBuilder.append("}");
		return stringBuilder.toString();
	}
}
