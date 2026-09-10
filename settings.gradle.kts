rootProject.name = "docker-gradle-plugin"

pluginManagement {
    includeBuild("./plugin-docker")
    repositories {
        maven("https://maven.siverov.com/public")
        mavenCentral()
        gradlePluginPortal()
    }
}

include("test-project")