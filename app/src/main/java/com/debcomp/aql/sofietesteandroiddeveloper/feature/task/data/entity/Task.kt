package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity
import com.google.gson.annotations.SerializedName

/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */

data class Task(
    @SerializedName("_id")
    val id: String,
    val title: String,
    val email: String,
    val description: String,
    @SerializedName("when")
    val date: String
)

data class TaskList(
    @SerializedName("data")
    val tasks: List<Task>
)