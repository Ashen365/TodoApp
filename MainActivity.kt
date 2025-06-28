package com.ashen365.todoapp2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashen365.todoapp2.data.Todo
import com.ashen365.todoapp2.ui.TodoAdapter
import com.ashen365.todoapp2.ui.TodoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            // Set up ViewModel
            todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

            // Set up RecyclerView
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            // Set up adapter
            val adapter = TodoAdapter(
                onItemChecked = { todo ->
                    todoViewModel.update(todo)
                },
                onItemDeleted = { todo ->
                    todoViewModel.delete(todo)
                }
            )
            recyclerView.adapter = adapter

            // Observe LiveData
            todoViewModel.allTodos.observe(this) { todos ->
                adapter.submitList(todos)
            }

            // Set up add button
            val addButton = findViewById<Button>(R.id.button_add)
            val editText = findViewById<EditText>(R.id.edit_todo)

            addButton.setOnClickListener {
                val text = editText.text.toString().trim()
                if (text.isNotEmpty()) {
                    todoViewModel.insert(Todo(title = text))
                    editText.setText("")
                } else {
                    Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            Log.e("MainActivity", "Error initializing app", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}