package com.siv.plugins.docker.tasks

import com.siv.plugins.docker.DockerExtension
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File

const val DOCKER_TASK_GROUP = "docker"

abstract class DockerTask(
    @get:Internal
    val taskDescription: String
) : DefaultTask() {
    @get:Internal
    internal val dependencies = mutableListOf<Any>()

    @get:Internal
    internal val ext = project.extensions.getByType(DockerExtension::class.java)

    init {
        description = taskDescription
        group = DOCKER_TASK_GROUP

        doInitialize()

        project.subprojects
            .map { pr -> pr.name }
            .map { pr -> "$pr:$name" }
            .forEach { dependencies.add(it) }
        dependsOn(*dependencies.toTypedArray())

    }

    fun which(executable: String): String? {
        val path = System.getenv("PATH") ?: return null
        val pathSeparator = File.pathSeparator
        val isWindows = System.getProperty("os.name").startsWith("Windows", true)

        val extensions = if (isWindows) {
            val pathext = System.getenv("PATHEXT")
                ?.split(";")
                ?.filter { it.isNotBlank() }
                ?: listOf(".EXE", ".BAT", ".CMD", ".COM")
            if (File(executable).extension.isNotEmpty()) listOf("") else pathext
        } else {
            listOf("")
        }

        return path
            .split(pathSeparator)
            .asSequence()
            .flatMap { dir ->
                extensions.asSequence().map { ext -> File(dir, executable + ext) }
            }
            .firstOrNull { file ->
                file.isFile && if (isWindows) file.canRead() else file.canExecute()
            }
            ?.absolutePath
    }

    abstract fun doInitialize()

    @TaskAction
    abstract fun run()
}