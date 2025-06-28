package com.ashen365.todoapp2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashen365.todoapp2.R
import com.ashen365.todoapp2.data.Todo

class TodoAdapter(
    private val onItemChecked: (Todo) -> Unit,
    private val onItemDeleted: (Todo) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_title)
        private val completedCheckBox: CheckBox = itemView.findViewById(R.id.checkbox_completed)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.button_delete)

        fun bind(todo: Todo) {
            titleTextView.text = todo.title
            completedCheckBox.isChecked = todo.isCompleted

            completedCheckBox.setOnClickListener {
                val updatedTodo = todo.copy(isCompleted = completedCheckBox.isChecked)
                onItemChecked(updatedTodo)
            }

            deleteButton.setOnClickListener {
                onItemDeleted(todo)
            }
        }
    }

    class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}