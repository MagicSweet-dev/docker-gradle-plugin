package com.siv.plugins.docker

import org.gradle.api.Project
import java.io.File

class DockerImageBuild(
    var dockerfile: File,
    internal var args: MutableMap<String, String> = mutableMapOf(),
    internal var platforms: Set<String> = setOf(),
) {
    internal var redactedArgs: MutableSet<String> = mutableSetOf()

    companion object {
        fun default(project: Project) = DockerImageBuild(
            File(project.projectDir.absolutePath, "Dockerfile")
        )
    }

    fun dockerfile(dockerfile: File) {
        this.dockerfile = dockerfile
    }

    fun platforms(platforms: Set<String>) {
        this.platforms = platforms
    }

    fun platforms(vararg platforms: String) {
        this.platforms = platforms.toSet()
    }

    fun arg(name: String, value: Any, redactFromLog: Boolean = false) {
        this.args[name] = value.toString()
        if (redactFromLog) redactedArgs.add(name)
    }

    fun arg(pair: Pair<String, Any>, redactFromLog: Boolean = false) {
        this.args[pair.first] = pair.second.toString()
        if (redactFromLog) redactedArgs.add(pair.first)
    }
}