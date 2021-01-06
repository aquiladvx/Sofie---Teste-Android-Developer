package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.taskHome.model

import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.TaskResponse
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.model.ResponseStatus


/*
 * Davi Áquila
 * aquiladvx
 *
 * 06/01/2021
 *
 */

data class RemoveTaskResult(
    val status: ResponseStatus,
    val response: TaskResponse?,
    val message: String = "",
    val position: Int = 0
)