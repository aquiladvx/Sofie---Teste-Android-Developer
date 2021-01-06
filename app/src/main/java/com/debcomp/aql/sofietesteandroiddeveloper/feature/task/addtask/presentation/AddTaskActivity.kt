package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.addtask.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.debcomp.aql.sofietesteandroiddeveloper.R
import com.debcomp.aql.sofietesteandroiddeveloper.infra.SofieCentralApplication
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.addtask.model.AddTaskViewModel
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.SimpleTask
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.model.ResponseStatus
import com.debcomp.aql.sofietesteandroiddeveloper.infra.BaseActivity
import com.debcomp.aql.sofietesteandroiddeveloper.infra.MyAlertDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddTaskActivity : BaseActivity() {

    private lateinit var viewModel: AddTaskViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_task)
        mView = findViewById<View>(android.R.id.content).rootView

        viewModel = ViewModelProvider(
            this,
            AddTaskViewModel.AddTaskViewModelFactory(SofieCentralApplication.instance)
        ).get(AddTaskViewModel::class.java)

        setupToolBar()
        setObservables()
    }

    private fun setObservables() {
        viewModel.addTask.observe(this, Observer { result ->
            hideLoading()
            when (result.status) {
                ResponseStatus.OK -> {
                    Toast.makeText(this, "Tarefa adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
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

    private fun setupToolBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        supportActionBar?.title = getString(R.string.title_add_task_toolbar)
        supportActionBar?.setIcon(R.drawable.ic_close)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_add_task, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.btn_add_task_confirm -> {
                addTask()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    private fun addTask() {
        hideKeyboard(mView)
        val email = et_email.text.toString()
        val title = et_name_task.text.toString()
        val desc = et_ml_description.text.toString()

        if (email.isNotEmpty() && title.isNotEmpty() && desc.isNotEmpty()) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showLoading()
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addTask(SimpleTask(title, email, desc))
                }
            } else {
                Snackbar.make(et_email, "e-mail inválido.", Snackbar.LENGTH_LONG).show()
            }
        } else {
            Snackbar.make(et_email, "É necessário preencher todos os campos", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        fun start(context: Context): Intent {
            return Intent(context, AddTaskActivity::class.java)
        }
    }
}