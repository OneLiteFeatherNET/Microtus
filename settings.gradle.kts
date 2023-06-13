rootProject.name = "Microtus"
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://files.minecraftforge.net/maven/")
    }
    
}
if (file("patched-minestom").exists()) {
    includeBuild("patched-minestom")
}


