package com.example.taskmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.StringBuilder

class TaskListActivity : AppCompatActivity() {

    lateinit var addTaskButton: Button
    var taskList:ArrayList<String> = ArrayList()
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var taskAdaptor: TaskAdaptor

    lateinit var adaptor: TaskAdaptor
    lateinit var taskListListView: ListView

    fun intializeTaskList () {
        taskList.add("Get milk")
        taskList.add("Attend Android Training")
        taskList.add("Sleep 8 hours")
        taskList.add("Get milk")
        taskList.add("Sleep 8 hours")
        taskList.add("Get milk")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        if (savedInstanceState == null)
            intializeTaskList()
        else {
            var savedList = savedInstanceState.getStringArrayList("TASK_LIST")
            if (savedList != null) {
                taskList = savedList
            }
        }

        taskAdaptor = TaskAdaptor(this, taskList)
        taskListRecyclerView = findViewById(R.id.taskListRecyclerView)

        // attach the layout manager to the recycler view
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        taskListRecyclerView.layoutManager = layoutManager

        // attaching the adaptor to  recycler view
        taskListRecyclerView.adapter = taskAdaptor

        addTaskButton = findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener {
            var intent = Intent(this@TaskListActivity, AddTaskActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK && data != null){
            var task = data.extras?.getString("NEW_TASK_NAME")

            // Conversion to non-optional
            // Another approach is to use let - it only runs when something is not null
            if (task != null)
                taskList.add(task)
        }

        // Refreshing the recycler view about the change in data.
        taskAdaptor.notifyDataSetChanged()
    }

    // Saving the important data in the bundle object created by the android system
    // which it shares as a reference with the current activity, before the activity gets
    // destroyed. After the activity gets destroued and it creates again, this reference is
    // attached to this new activity.

    // Initially the bundle object is null, that is, it is not attached to the activity
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("TASK_LIST", taskList)
    }
}