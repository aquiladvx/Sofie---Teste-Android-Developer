package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.hometask.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.debcomp.aql.sofietesteandroiddeveloper.R
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.addtask.presentation.AddTaskActivity
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.Task
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.model.ResponseStatus
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.hometask.adapter.TaskAdapter
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.hometask.model.TaskViewModel
import com.debcomp.aql.sofietesteandroiddeveloper.infra.BaseActivity
import com.debcomp.aql.sofietesteandroiddeveloper.infra.MyAlertDialog
import com.debcomp.aql.sofietesteandroiddeveloper.infra.SofieCentralApplication
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeActivity : BaseActivity() {
    private lateinit var adapter: TaskAdapter
    private lateinit var viewModel: TaskViewModel
    private lateinit var tasks: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        adapter = TaskAdapter(
            this,
            ::taskClickListener
        )

        rv_all_tasks.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            TaskViewModel.TaskViewModelFactory(SofieCentralApplication.instance)
        ).get(TaskViewModel::class.java)

        setObservers()
        setActions()
        init()
    }

    override fun onResume() {
        init()
        super.onResume()
    }

    private fun init() {
        showLoading()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getAllTasks(getString(R.string.email_test_android_developer))
        }
    }

    private fun taskClickListener(taskId: String) {
        Log.i(TAG, "item id = $taskId")
//        startActivity(DetailTaskActivity.start(this, taskId))
    }

    private fun setActions() {
        fab_add.setOnClickListener {
            startActivity(AddTaskActivity.start(this))
        }

        //Swipe item on RecyclerView
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Toast.makeText(this@HomeActivity, "on Move", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                Toast.makeText(this@HomeActivity, "on Swiped ", Toast.LENGTH_SHORT).show()
                val position = viewHolder.adapterPosition
                removeTask(viewHolder.adapterPosition)

            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv_all_tasks)

    }

    fun removeTask(position: Int) {
        tasks.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun setObservers() {
        viewModel.allTasks.observe(this, Observer { result ->
            hideLoading()
            when(result.status) {
                ResponseStatus.OK -> {
                    tasks = result.response?.tasks as MutableList<Task>
                    adapter.submit(tasks)
                }
                ResponseStatus.ERROR -> {
                    MyAlertDialog.showWarningDialog(getString(R.string.error_response), this)
                }
                ResponseStatus.NETWORK_CONNECTION_FAILED -> {
                    MyAlertDialog.showWarningDialog(getString(R.string.error_network), this)
                }
            }

        })
    }

    companion object {
        private val TAG = HomeActivity::class.java.name
    }
}