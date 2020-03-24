package me.alexng.gns.env;

import me.alexng.gns.bridge.Expose;
import me.alexng.gns.env.value.NumberValue;
import me.alexng.gns.env.value.ObjectValue;
import me.alexng.gns.env.value.Value;

import java.io.IOException;
import java.io.OutputStream;

public class Sys {

	private static final String NEW_LINE = "\n";

	@Expose
	public NumberValue version = new NumberValue(3.2);

	@Expose
	public void print(Environment environment, Value value) {
		String output = null;
		switch (value.getType()) {
			case NULL:
				output = "null";
				break;
			case OBJECT:
				ObjectValue rawObject = (ObjectValue) value;
				output = rawObject.getObjectScope().nameProvider.getName() + "#" + rawObject.getObjectId();
				// TODO: Call a toString function in the object
				break;
			default:
				output = value.getJavaValue().toString();
				break;
		}
		rawPrint(environment.stdout, output.getBytes());
	}

	@Expose
	public void println(Environment environment, Value value) {
		print(environment, value);
		rawPrint(environment.stdout, NEW_LINE.getBytes());
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
