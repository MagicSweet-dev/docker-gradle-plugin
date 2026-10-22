package com.siv.plugins.docker.tasks

open class DockerImagePrintTask : DockerTask(
    "Print Docker Image",
) {

    override fun doInitialize() {}

    override fun run() {
        for (image in ext.images) println(image)
    }

}