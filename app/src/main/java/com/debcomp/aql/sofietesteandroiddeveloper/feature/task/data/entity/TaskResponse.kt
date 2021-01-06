package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity

import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.model.ResponseStatus
import com.google.gson.annotations.SerializedName


/*
 * Davi Áquila
 * aquiladvx
 *
 * 06/01/2021
 *
 */

data class TaskResponse(
    val success: Boolean,
    @SerializedName("data")
    val task: Task
)
