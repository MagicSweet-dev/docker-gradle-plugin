package com.siv.plugins.docker.tasks

open class DockerBuildPushTask : DockerTask(
    "Build & Push Docker Image",
) {

    override fun doInitialize() {
        dependencies.add("dockerBuild")
        dependencies.add("dockerPush")

    }

    override fun run() {

    }

}