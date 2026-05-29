import org.gradle.kotlin.dsl.java

plugins {
    kotlin("jvm")
    java
    id("com.siv.docker")
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.siv.testproject.TestProjectKt"
        )
    }
}

docker {
    image {
        registry("docker.siverov.com")
        tag(version.toString())

        build.arg("JAR_FILE" to tasks.jar.get().outputs.files.first().path.removePrefix(project.projectDir.absolutePath))
    }
}
