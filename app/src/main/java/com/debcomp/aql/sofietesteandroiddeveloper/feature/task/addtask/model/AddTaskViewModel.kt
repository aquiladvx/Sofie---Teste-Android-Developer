package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.addtask.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.SimpleTask
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.Task
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalArgumentException


/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */

class AddTaskViewModel(val app: Application) : AndroidViewModel(app) {

    private val repository = TaskRepository(app)
    val addTask = repository.addTask

    suspend fun addTask(task: SimpleTask) = repository.addTask(task)


    //Factory
    class AddTaskViewModelFactory constructor(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(AddTaskViewModel::class.java)) {
                AddTaskViewModel(this.application) as T
            } else {
                throw IllegalArgumentException("ViewModel not found")
            }
        }

    }
}