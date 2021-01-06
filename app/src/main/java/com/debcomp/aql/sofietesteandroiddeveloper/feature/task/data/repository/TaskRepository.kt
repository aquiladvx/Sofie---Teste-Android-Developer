package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.addtask.model.AddTaskResult
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.SimpleTask
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.TaskList
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.TaskResponse
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.model.ResponseStatus
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.service.TaskService
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.hometask.model.AllTasksResult
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.hometask.model.RemoveTaskResult
import com.debcomp.aql.sofietesteandroiddeveloper.infra.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */

class TaskRepository(val app: Application) {

    val allTasks    = MutableLiveData<AllTasksResult>()
    val addTask     = MutableLiveData<AddTaskResult>()
    val removeTask  = MutableLiveData<RemoveTaskResult>()

    @WorkerThread
    suspend fun getAllTasks(email: String) {
        if (networkAvailable()) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.WEB_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit
                .create(TaskService::class.java)
            val serviceData = service
                .getAllTasks(email)

            serviceData.enqueue(object : Callback<TaskList> {
                override fun onFailure(call: Call<TaskList>, t: Throwable) {
                    Log.e("Error Call", t.message!!)
                    allTasks.postValue(AllTasksResult(ResponseStatus.ERROR, message = t.message!!))
                }

                override fun onResponse(call: Call<TaskList>, response: Response<TaskList>) {
                    allTasks.postValue(
                        AllTasksResult(
                            ResponseStatus.OK,
                            response = response.body()
                        )
                    )
                }

            })
        } else {
            allTasks.postValue(AllTasksResult(ResponseStatus.NETWORK_CONNECTION_FAILED))
        }
    }

    @WorkerThread
    suspend fun addTask(task: SimpleTask) {
        if (networkAvailable()) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.WEB_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit
                .create(TaskService::class.java)
            val serviceData = service.postTask(task)

            serviceData.enqueue(object : Callback<TaskResponse> {
                override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                    addTask.postValue(AddTaskResult(ResponseStatus.ERROR, null, t.message!!))
                }

                override fun onResponse(
                    call: Call<TaskResponse>,
                    response: Response<TaskResponse>
                ) {
                    if (response.body()!!.success) {
                        addTask.postValue(AddTaskResult(ResponseStatus.OK, response = response.body()))
                    } else {
                        addTask.postValue(AddTaskResult(ResponseStatus.ERROR, null))
                    }
                }
            })
        } else {
            addTask.postValue(AddTaskResult(ResponseStatus.NETWORK_CONNECTION_FAILED, null))
        }
    }

    @WorkerThread
    suspend fun removeTask(id: String, position: Int) {
        if (networkAvailable()) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.WEB_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit
                .create(TaskService::class.java)
            val serviceData = service.removeTask(id)

            serviceData.enqueue(object : Callback<TaskResponse> {
                override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                    removeTask.postValue(RemoveTaskResult(ResponseStatus.ERROR, null, t.message!!))
                }

                override fun onResponse(
                    call: Call<TaskResponse>,
                    response: Response<TaskResponse>
                ) {
                    if (response.body()!!.success) {
                        removeTask.postValue(RemoveTaskResult(ResponseStatus.OK, response = response.body(), position = position))
                    } else {
                        removeTask.postValue(RemoveTaskResult(ResponseStatus.ERROR, null))
                    }
                }
            })
        } else {
            removeTask.postValue(RemoveTaskResult(ResponseStatus.NETWORK_CONNECTION_FAILED, null))
        }
    }


    private fun networkAvailable(): Boolean {
        val connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}