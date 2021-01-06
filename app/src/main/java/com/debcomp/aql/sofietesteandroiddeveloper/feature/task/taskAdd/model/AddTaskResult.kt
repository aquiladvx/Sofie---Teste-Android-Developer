package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.taskAdd.model

import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.TaskResponse
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.model.ResponseStatus


/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */

data class AddTaskResult(
    val status: ResponseStatus,
    val response: TaskResponse?,
    val message: String = ""
)


