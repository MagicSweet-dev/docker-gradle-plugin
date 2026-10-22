plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "2.3.0"
    id("maven-publish")
    id("com.gradle.plugin-publish") version "2.0.0"
}

group = "com.siverov"
version = "1.0.1"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-exec:1.4.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

gradlePlugin {
    website = "https://github.com/MagicSweet-dev/docker-gradle-plugin"
    vcsUrl = "https://github.com/MagicSweet-dev/docker-gradle-plugin"
    plugins {
        create("docker") {
            displayName = "Docker"
            description = "Simple Docker plugin for Gradle"
            group = project.group.toString()
            id = "com.siverov.docker"
            version = project.version.toString()
            tags = listOf(
                "docker"
            )
            implementationClass = "com.siv.plugins.docker.DockerPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
        maven {
            url = uri("https://maven.siverov.com/public/")
            credentials {
                username = property("mvn.user") as String
                password = property("mvn.password") as String
            }
        }
    }
}


java {
    withSourcesJar()
    withJavadocJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
