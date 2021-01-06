package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.service

import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.SimpleTask
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.Task
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.TaskList
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.TaskResponse
import retrofit2.Call
import retrofit2.http.*

/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */
interface TaskService {

    @GET("task")
    fun getAllTasks(
        @Query("email")email: String
    ): Call<TaskList>

    @POST("task")
    fun postTask(
        @Body task: SimpleTask
    ): Call<TaskResponse>

    @DELETE("task/{task_id}")
    fun removeTask(
        @Path(value = "task_id") taskId: String
    ): Call<TaskResponse>

//    @GET("discover/tv")
//    fun getShowByGenre(
//        @Query("api_key")apiKey: String,
//        @Query("language")language: String = "pt-BR",
//        @Query("with_genres")genreId: Int
//    ): Call<ShowList>

}