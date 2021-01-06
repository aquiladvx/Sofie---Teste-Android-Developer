package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.taskDetail.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.debcomp.aql.sofietesteandroiddeveloper.R
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.Task
import kotlinx.android.synthetic.main.activity_detail_task.*


lateinit var mTask: Task

class DetailTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setText()
    }

    private fun setText() {
        tv_email.text = mTask.email
        tv_name_task.text = mTask.title
        tv_ml_description.text = mTask.description
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    companion object {
        fun start(context: Context, task: Task): Intent {
            return Intent(context, DetailTaskActivity::class.java).apply {
                mTask = task
            }
        }
    }
}