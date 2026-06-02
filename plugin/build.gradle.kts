plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "2.3.0"
    id("maven-publish")
}

group = "com.siv"
version = "1.0.0-SNAPSHOT"

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
    plugins {
        create("docker") {
            group = "com.siv"
            id = "com.siv.docker"
            version = project.version.toString()
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
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
