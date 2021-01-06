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
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.detailtask.presentation.DetailTaskActivity
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.hometask.adapter.TaskAdapter
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.hometask.model.TaskViewModel
import com.debcomp.aql.sofietesteandroiddeveloper.infra.BaseActivity
import com.debcomp.aql.sofietesteandroiddeveloper.infra.MyAlertDialog
import com.debcomp.aql.sofietesteandroiddeveloper.infra.SofieCentralApplication
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_choice_warning.*
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

    private fun taskClickListener(mTask: Task) {
        Log.i(TAG, "item id = $taskId")
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
            startActivity(DetailTaskActivity.start(this, mTask))
        }
    }

    private fun setActions() {
        fab_add.setOnClickListener {
            startActivity(AddTaskActivity.start(this))
        }

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
            pullToRefresh.setOnRefreshListener {
                init()
                pullToRefresh.isRefreshing = false
            }
        }

            /*
                Feature para remocao do item com swipe para a esquerda
                (nao implementado por nao ser possivel excluir itens no servidor)
             */
//        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
//            ItemTouchHelper.SimpleCallback(
//                0,
//                ItemTouchHelper.LEFT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//                val position = viewHolder.adapterPosition
//                removeTask(viewHolder.adapterPosition)
//
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(rv_all_tasks)

    }


    private fun setObservers() {
        viewModel.allTasks.observe(this, Observer { result ->
            hideLoading()
            when (result.status) {
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

        //Construido para uma futura implementacao de remocao de tasks
        viewModel.removeTask.observe(this, Observer { result ->
            hideLoading()
            when (result.status) {
                ResponseStatus.OK -> {
                    Toast.makeText(this, "Tarefa removida com sucesso!", Toast.LENGTH_SHORT).show()
                    tasks.removeAt(result.position)
                    adapter.notifyItemRemoved(result.position)
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

    fun removeTask(position: Int) {
        val dialog = MyAlertDialog.showWarningChoiceDialog(getString(R.string.confirm_delete_task), this)
        dialog.btnOk.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                showLoading()
                viewModel.removeTask(tasks[position], position)
                dialog.dismiss()
            }
        }

        dialog.btnNok.setOnClickListener {
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()

    }

    companion object {
        private val TAG = HomeActivity::class.java.name
    }
}