package com.siv.plugins.docker.tasks

import org.gradle.internal.extensions.core.serviceOf
import org.gradle.process.ExecOperations

open class DockerPushTask : DockerTask(
    "Push Docker Image",
) {

    override fun doInitialize() {
        mustRunAfter("dockerBuild")
    }

    override fun run() {
        for (image in ext.images) {
            println("> Pushing Docker Image ${image.fullImageName}...")
            val dockerPath = which("docker")

            project.serviceOf<ExecOperations>().exec {
                it.executable(dockerPath)
                it.args(
                    "push", image.fullImageName
                )
            }
        }

    }

}