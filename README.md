# GNS
A scripting language written in Java.


### Usage

```Java
Environment scriptEnvironment = new Environment(new Options.Builder().build());
File scriptFile = new File(FILE_PATH);
scriptEnvironment.loadScript(new Script(scriptFile));
// Multiple scripts can be loaded, and are run in order.
scriptEnvironment.runScripts();
```

### Examples

For example scripts, see `scripts/`. 

### Contributing

Just send a pull request and try keep the general code style intact.
Testing is appreciated but currently not required.