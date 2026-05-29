package com.siv.plugins.docker

import com.siv.plugins.docker.tasks.DockerBuildPushTask
import com.siv.plugins.docker.tasks.DockerBuildTask
import com.siv.plugins.docker.tasks.DockerPushTask
import com.siv.plugins.docker.tasks.DockerRemoveTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlin.collections.iterator

class DockerPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with (project.tasks) {
            for (task in mapOf(
                "dockerBuild" to DockerBuildTask::class,
                "dockerPush" to DockerPushTask::class,
                "dockerImageRemove" to DockerRemoveTask::class,
                "dockerBuildPush" to DockerBuildPushTask::class,
            )) register(task.key, task.value.java) {}
        }

        project.extensions.create("docker", DockerExtension::class.java, project)

        project.afterEvaluate {

        }
    }
}