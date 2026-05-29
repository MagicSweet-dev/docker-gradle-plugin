rootProject.name = "docker-gradle-plugin"

pluginManagement {
    includeBuild("./plugin")
    repositories {
        maven("https://maven.siverov.com/public")
        mavenCentral()
        gradlePluginPortal()
    }
}

include("test-project")