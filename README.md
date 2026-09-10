# Docker Gradle Plugin
Simple Gradle plugin to use [Docker](https://www.docker.com) within your project.

This plugin pairs nicely with [The Application Plugin](https://docs.gradle.org/current/userguide/application_plugin.html) or [Spring Boot Plugin](https://docs.spring.io/spring-boot/gradle-plugin/index.html).<br>
Simply provide the built JAR (or WAR) file as a build argument (see [Usage](#usage) below for examples), then build and run your container.<br>
Publish it too, if you want.

However, `com.siverov.docker` is not limited to just those plugins - you can pair it with pretty much anything.

## Features

The plugin allows to use following Docker commands by executing a respective Gradle task:
- `docker buildx build ... --load`: `:dockerBuild` / `:dockerBuildPush`
- `docker push`: `:dockerPush`
- `docker image rm`: `:dockerImageRemove`

## Requirements
[Docker](https://www.docker.com), [Docker BuildKit](https://docs.docker.com/reference/cli/docker/buildx/).

## Usage

1. Apply the plugin (from [Gradle Plugin Portal](https://plugins.gradle.org/)):
   ```kotlin
   plugins {
       id("com.siverov.docker") version "1.0.0"
   }
   ```
   Alternatively, you can use a `-SNAPSHOT` version from https://maven.siverov.com/public.<br>
   (but be aware: SNAPSHOT is a development version)
2. Define your docker build:
   ```kotlin
    docker {
        image {
            registry("docker.siverov.com") // default: docker.io/library
            tag(version.toString()) // default: latest
    
            build {
                dockerfile(file("project.Dockerfile")) // default: Dockerfile
                arg("JAR_FILE" to tasks.jar.get().outputs.files.singleFile.relativeTo(projectDir))
            }
            /* or:
            build.dockerfile(file("Dockerfile"))
            build.arg("ARG_NAME", "ARG_VALUE")
            */
        }
    }
    ```
   *Note*: You can specify multiple `image { ... }` blocks to build multiple images.
3. Run a gradle task of your choice (see [Features](#features))
4. 🎉

## Contributing
If you want to fix something, or make/suggest a change or a feature - feel free, contributions are open (and welcome) to anyone.<br>

Create a fork, do whatever you want, and open a [Pull Request](https://github.com/MagicSweet-dev/docker-gradle-plugin/pulls). Or don't - just keep your changes to you since the [license](LICENSE) allows that, too.<br>
Or create and describe an [Issue](https://github.com/MagicSweet-dev/docker-gradle-plugin/issues) (but remember to do so in English or Russian).
