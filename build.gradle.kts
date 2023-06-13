import org.ajoberstar.grgit.Grgit
import java.util.*

plugins {
    id("net.minecraftforge.gitpatcher") version "0.10.+"
    id("org.ajoberstar.grgit") version "5.2.0"
}

if (!File("$rootDir/.git").exists()) {
    logger.lifecycle(
        """
    **************************************************************************************
    You need to fork and clone this repository! Don't download a .zip file.
    If you need assistance, consult the GitHub docs: https://docs.github.com/get-started/quickstart/fork-a-repo
    **************************************************************************************
    """.trimIndent()
    ).also { System.exit(1) }
}

var baseVersion by extra("1.0.0")
var versionExtension by extra("")
var snapshot by extra("-SNAPSHOT")

group = "net.onelitefeather"


ext {
    val git: Grgit = Grgit.open {
        dir = File("$rootDir/.git")
    }
    val revision = git.head().abbreviatedId
    versionExtension = "%s+%s".format(Locale.ROOT, snapshot, revision)
}



version = "%s%s".format(Locale.ROOT, baseVersion, versionExtension)


patches {
    submodule = "Minestom"
    patches = file("minestom-patches")
    target = file("patched-minestom")
    addAsSafeDirectory.set(false)
}

tasks {
    register("rebuildPatches") {
        dependsOn(makePatches)
    }
}