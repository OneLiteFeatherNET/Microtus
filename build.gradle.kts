plugins {
    id("net.minecraftforge.gitpatcher") version "0.10.+"
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