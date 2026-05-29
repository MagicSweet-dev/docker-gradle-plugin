package com.siv.plugins.docker

import org.gradle.api.Project
import java.io.File

class DockerImageBuild(
    var dockerfile: File,
    internal var args: MutableMap<String, String> = mutableMapOf(),
) {
    companion object {
        fun default(project: Project) = DockerImageBuild(
            File(project.projectDir.absolutePath, "Dockerfile")
        )
    }

    fun dockerfile(dockerfile: File) {
        this.dockerfile = dockerfile
    }

    fun arg(name: String, value: Any) {
        this.args[name] = value.toString()
    }

    fun arg(pair: Pair<String, Any>) {
        this.args[pair.first] = pair.second.toString()
    }
}