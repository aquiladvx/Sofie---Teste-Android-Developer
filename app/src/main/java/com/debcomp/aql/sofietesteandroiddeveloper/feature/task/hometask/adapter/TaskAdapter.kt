package com.debcomp.aql.sofietesteandroiddeveloper.feature.task.hometask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.debcomp.aql.sofietesteandroiddeveloper.R
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.data.entity.Task
import java.util.*


/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */
class TaskAdapter internal constructor(
    context: Context,
    private val listener: (Task) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var tasks = Collections.emptyList<Task>()

    inner class TaskViewHolder(itemView: View, private val listener: (Task) -> Unit):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val taskItemTitle: TextView = itemView.findViewById(R.id.txtItemTaskName)
        private val taskItemEmail: TextView = itemView.findViewById(R.id.txtItemTaskEmail)
        private lateinit var mTask: Task

        fun bind(data: Task) {
            mTask = data
            taskItemTitle.text = data.title
            taskItemEmail.text = data.email

            itemView.setOnClickListener {
                listener.invoke(mTask)
            }
        }

        override fun onClick(p0: View?) {
            listener.invoke(mTask)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = inflater.inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount() = tasks.size

    internal fun submit(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }


}