# Extension Gradle Plugin

When you don't want to create a `extension.json` file for each extension, so you can use
our `extension` gradle plugin. This generates  automatically the specific file for you.
Before you include it in your `build.gradle.kts` file you need to add the correct maven repository to 
your plugin management and the plugin itself to `plugins` section

PluginManagement section:
```Kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.github.com/OneLiteFeatherNET/Microtus") {
            credentials {
              username = "Your username"
              password = "your github token"
            }
        }
    }
}
```

Plugin section:
```Kotlin
plugins {
    id("net.onelitefeather.microtus.extension") version "0.0.1"
}
```

> The gradle plugin requires the jackson dependencies as `extension library`.
> You need to add the following snippet:
> {style="note"}
 ```Kotlin
dependencies {
    extensionLibrary("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2") // Use the external dependencies function from minestom
} 
```

Now you should be able to define a `extension` block in your `build.gradle`
file without any issues. The syntax of configure the plugin follows the kotlin dsl script or groovy script definition.

### Configure extension generation

By default, the extension plugin configures certain fields with default values to prevent issues.
However, you can modify this behavior as needed or add additional properties. 
The available properties depend on the extension structure within Minestom itself.

These fields are available from the plugin:
- authors: List of strings
- entryPoint: String
- version: String
- name: String
- dependencies: List of dependencies
- externalDependencies:
  - Are handled via `extensionLibrary` from gradle

A functionally example looks like this:
```Kotlin
extension {
  authors = listOf("TheMeinerLP")
  entrypoint = "net.onelitefeather.microtus.extension.ProjectEntry"
  // dependencies = listOf("LuckPerms") // To generate dependencies
  // version = "1.0.0" // Normally its use the project version
  // name = "Example" // Normally its use the project name
  // External dependencies are handled via `extensionLibrary("String")` from gradle
}
```