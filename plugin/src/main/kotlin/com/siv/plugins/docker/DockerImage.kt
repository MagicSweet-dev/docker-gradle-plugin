package com.siv.plugins.docker

import org.gradle.api.Project
import org.gradle.api.tasks.Internal
import java.io.File

data class DockerImage(
    var registry: String,
    var image: String,
    var tag: String,
    var build: DockerImageBuild
) {

    companion object {
        fun default(project: Project) = DockerImage(
            "docker.io/library",
            if (project.rootProject == project) project.name
            else "${project.rootProject.name}/${project.name}",
            "latest",
            DockerImageBuild.default(project)
        )
    }

    @get:Internal
    val fullImageName: String get() = "$registry/$image:$tag"

    fun registry(registry: String) {
        this.registry = registry
    }

    fun image(image: String) {
        this.image = image
    }

    fun tag(tag: String) {
        this.tag = tag
    }

    fun build(func: DockerImageBuild.() -> Unit) {
        func(build)
    }
}
