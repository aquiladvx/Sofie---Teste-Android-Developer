package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.taskHome.model

import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.TaskList
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.model.ResponseStatus


/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */

data class AllTasksResult(
    val status: ResponseStatus,
    val response: TaskList? = TaskList(emptyList()),
    val message: String = ""
)

