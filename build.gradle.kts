plugins {
    kotlin("jvm") version "2.3.0"
}

allprojects {
    group = "com.siv"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenLocal()
        mavenCentral()
    }

}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

