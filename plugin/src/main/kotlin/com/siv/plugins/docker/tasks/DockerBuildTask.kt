package com.siv.plugins.docker.tasks

import org.gradle.api.UnknownTaskException
import org.gradle.internal.extensions.core.serviceOf
import org.gradle.process.ExecOperations

open class DockerBuildTask : DockerTask(
    "Build Docker Image",
) {

    override fun doInitialize() {
        dependencies.add(try {
            project.tasks.named("bootJar")
        } catch (_: UnknownTaskException) {
            project.tasks.named("build")
        })
    }

    override fun run() {
        for (image in ext.images) {

            val dockerPath = which("docker")

            println("""
                > Building Docker Image...
                + Image: ${image.fullImageName}
                + In: ${ext.dir.absolutePath}
                + Dockerfile: ${image.build.dockerfile.absolutePath}
                + Args: ${image.build.args.mapValues { 
                    if (image.build.redactedArgs.contains(it.key)) "***REDACTED***" else it.value
                }}
                + Executable: $dockerPath
            """.trimIndent())

            project.serviceOf<ExecOperations>().exec {
                /*
                * Gradle seems to be just ignoring PATH.
                * PATH to the executable 'docker' is present in environment, but both executable() and commandLine() cannot find it.
                * It was working perfectly fine with project.exec until they removed it in 9.0 🤷‍♂️
                */
                it.executable(dockerPath)
                it.args(*mutableListOf<String>(
                    "buildx",
                    "build",
                    "-t", image.fullImageName,
                    "-f", image.build.dockerfile.absolutePath,
                ).apply {
                    image.build.platforms.joinToString(",").let { platforms ->
                        if (platforms.isNotEmpty()) {
                            add("--platform")
                            add(platforms)
                        }
                    }
                    image.build.args.entries.forEach { e ->
                        add("--build-arg")
                        add("${e.key}=${e.value}")
                    }
                    add(ext.dir.absolutePath)

                    add("--load")
                }.toTypedArray())
            }
        }
    }

}