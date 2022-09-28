package com.example.taskmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TaskListFragment : Fragment() {
    lateinit var addTaskButton: Button
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var taskAdaptor: TaskAdaptor

    var taskList:ArrayList<String> = ArrayList()

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
        intializeTaskList()

        // better placement in this then oncreateView
        taskAdaptor = TaskAdaptor(requireActivity(), taskList)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var fragmentView = inflater.inflate(R.layout.fragment_task_list, container, false)

        // Get a reference to the recyclerr view that will display the tasks
        taskListRecyclerView = fragmentView.findViewById(R.id.taskListRecyclerView)

        // attach the layout manager to the recycler view
        var layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        taskListRecyclerView.layoutManager = layoutManager

        // attaching the adaptor to  recycler view
        taskListRecyclerView.adapter = taskAdaptor

        addTaskButton = fragmentView.findViewById(R.id.addTaskButton)
        addTaskButton.setOnClickListener {
            Log.i("TaskListFragment", "Add Task Button Tapped")
            var addTaskFragment = AddTaskFragment()
            addTaskFragment.setTargetFragment(this@TaskListFragment, 101)

            fragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.add(R.id.mainLayout, addTaskFragment, "Add task Fragment")
                ?.addToBackStack(null)
                ?.commit()

        }

        return fragmentView
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == AppCompatActivity.RESULT_OK && data != null){
            var task = data.extras?.getString("NEW_TASK_NAME")

            if (task != null)
                taskList.add(task)
        }

        // Refreshing the recycler view about the change in data.
        taskAdaptor.notifyDataSetChanged()
    }
}