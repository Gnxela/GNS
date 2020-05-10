package me.alexng.gns.env;

import me.alexng.gns.FileIndex;
import me.alexng.gns.bridge.Expose;
import me.alexng.gns.tokens.value.NumberValue;
import me.alexng.gns.tokens.value.ObjectValue;
import me.alexng.gns.tokens.value.StringValue;
import me.alexng.gns.tokens.value.Value;
import me.alexng.gns.util.ObjectToStringCrawler;

import java.io.IOException;
import java.io.OutputStream;

public class Sys {

	private static final String NEW_LINE = "\n";
	private static final String NULL_STRING = "null";

	@Expose
	public NumberValue version = new NumberValue(3.2, FileIndex.INTERNAL_INDEX);

	private String valueToString(Value value) {
		switch (value.getType()) {
			case NULL:
				return NULL_STRING;
			case OBJECT:
				if (value instanceof StringValue) {
					return ((StringValue) value).getJavaValue();
				} else {
					// TODO: Optimize this; it slows down runtime considerably.
					ObjectValue rawObject = (ObjectValue) value;
					ObjectToStringCrawler crawler = new ObjectToStringCrawler();
					rawObject.getObjectScope().crawl(crawler);
					return crawler.destroy();
				}
			default:
				return value.getJavaValue().toString();
		}
	}

	@Expose
	public void print(Environment environment, Value value) {
		rawPrint(environment.stdout, valueToString(value).getBytes());
	}

	@Expose
	public void println(Environment environment, Value value) {
		rawPrint(environment.stdout, (valueToString(value) + NEW_LINE).getBytes());
	}

	private void rawPrint(OutputStream stdout, byte[] bytes) {
		try {
			stdout.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
