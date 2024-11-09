package com.example.barchasb.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskListAdapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)

        val tasks = listOf(
            Task(
                "111",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),
            Task(
                "222",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),
            Task(
                "333",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),
            Task(
                "444",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),
            Task(
                "555",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),
            Task(
                "666",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),
            Task(
                "777",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),
            Task(
                "888",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),
            Task(
                "999",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),
            Task(
                "1000",
                getString(R.string.task_title_template),
                getString(R.string.task_description_template)
            ),


            )

        taskListAdapter = TaskListAdapter(tasks) { task ->
            navigateToASRTask(task)
        }

        binding.taskRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.taskRecyclerView.adapter = taskListAdapter

        return binding.root
    }

    private fun navigateToASRTask(task: Task) {
        val bundle = Bundle().apply {
            putParcelable("task", task) // ارسال شیء Task به عنوان Parcelable
        }
        findNavController().navigate(R.id.action_taskListFragment_to_asrTaskFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
