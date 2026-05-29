package com.siv.plugins.docker

import groovy.lang.Closure
import groovy.lang.DelegatesTo
import org.gradle.api.Project
import java.io.File

open class DockerExtension(
    val project: Project,
) {
    var dir: File = project.projectDir
    internal var images: MutableSet<DockerImage> = mutableSetOf()

    fun image(func: DockerImage.() -> Unit) {
        val image = DockerImage.default(project)
        func(image)
        images += image
    }

    fun image(
        @DelegatesTo(
            value = DockerImage::class,
            strategy = Closure.DELEGATE_FIRST
        ) func: Closure<*>
    ) {
        val image = DockerImage.default(project)
        func.delegate = image
        func.call()
        images += image
    }
}