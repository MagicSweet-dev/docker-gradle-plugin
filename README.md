# Docker Gradle Plugin
Simple Gradle plugin to use [Docker](https://www.docker.com) within your project.

## Features

The plugin allows to use following Docker commands by executing a respective Gradle task:
- `docker build`: `:dockerBuild` / `:dockerBuildPush`
- `docker push`: `:dockerPush`
- `docker image rm`: `:dockerImageRemove`

## Requirements

Only one. `docker`.

## Usage

1. Apply the plugin (from Gradle Plugin Portal):
   ```kotlin
   plugins {
       id("com.siv.docker") version "1.0.0"
   }
   ```
2. Define your docker build:
   ```kotlin
    docker {
        image {
            registry("docker.siverov.com") // default: docker.io/library
            tag(version.toString()) // default: latest
    
            build {
                dockerfile(file("project.Dockerfile")) // default: Dockerfile
                arg("JAR_FILE" to tasks.jar.get().outputs.files.first().path.removePrefix(projectDir.absolutePath))
            }
            /* or:
            build.dockerfile(file("Dockerfile"))
            build.arg("ARG_NAME", "ARG_VALUE")
            */
        }
    }
    ```
   *Note*: You can specify multiple `image { ... }` blocks to build multiple images.
4. Run a gradle task of your choice (see [Features](#features))
5. 🎉