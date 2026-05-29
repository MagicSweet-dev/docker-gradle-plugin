package com.siv.plugins.docker.tasks

import org.gradle.internal.extensions.core.serviceOf
import org.gradle.process.ExecOperations

open class DockerRemoveTask : DockerTask(
    "Remove Docker Image",
) {

    override fun doInitialize() {

    }

    override fun run() {
        for (image in ext.images) {
            println("> Removing Docker Image ${image.fullImageName}...")
            project.serviceOf<ExecOperations>().exec {
                it.executable = "docker"
                it.args(
                    "image", "rm", image.fullImageName
                )
            }
        }

    }

}