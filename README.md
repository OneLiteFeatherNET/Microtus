# Microtus
[Microtus](https://en.wikipedia.org/wiki/Microtus) is a fork of the original minestom but patch based.

Our projects get names of animals but in latin.

Discord for discussion: [OneLiteFeather.net](https://discord.onelitefeather.net)

## Our goals
- Include patches from open pull requests on minestom repository
- Fixes issues from original minestom repository
- Make useful decisions
  - Include new features
  - Try to stay updated with original minecraft cycle
- Release cycle of 2 weeks

## Project contribution:

For details how you can contribute to the project please read our [Contributing](CONTRIBUTING.md).

## Usage of microtus
Since 23.07.2023 we are now official on the maven central for releases and snapshots.  
To use Microtus in your projects you need: 
<details>
  <summary>Snapshot</summary>

Please replace the `<version>` with the right one. You can find the versions here: [Central](https://central.sonatype.com/search?q=microtus)  
For example: `1.1.0-SNAPSHOT+9284d26`

### Repositories Section
```kt
repositories {
  mavenCentral()
  maven("https://oss.sonatype.org/content/repositories/snapshots")
}
```

### Dependency Section
```kt
dependencies {
    implementation("net.onelitefeather.microtus:Minestom:1.1.2-SNAPSHOT")
    testImplementation("net.onelitefeather.microtus.testing:testing:1.1.2-SNAPSHOT")
}
```
</details>

<details>
  <summary>Release</summary>

Please replace the `<version>` with the right one. You can find the versions here: [Central](https://central.sonatype.com/search?q=microtus)  
For example: `1.1.0`
### Dependency Section
```kt
dependencies {
    implementation("net.onelitefeather.microtus:Minestom:1.1.2")
    testImplementation("net.onelitefeather.microtus.testing:testing:1.1.2")
}
```
</details>