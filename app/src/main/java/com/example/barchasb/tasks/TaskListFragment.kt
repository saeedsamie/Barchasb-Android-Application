package com.example.barchasb.tasks

import TokenManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskListAdapter
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ایجاد یک Callback برای مدیریت دکمه بازگشت
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_taskListFragment_to_dashboardFragment)
            }
        }

        // اضافه کردن Callback به سیستم بازگشت
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskAdapter = TaskListAdapter(emptyList()) { task ->
            taskViewModel.selectTask(task) // Set the selected task in ViewModel
            when (task.type) {
                0 -> findNavController().navigate(R.id.action_taskListFragment_to_asrTaskFragment)
                1 -> findNavController().navigate(R.id.action_taskListFragment_to_wordOcrTaskFragment)
            }
        }

        binding.taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.taskRecyclerView.adapter = taskAdapter

        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            tasks?.let { taskAdapter.updateTasks(it) }
        }

        taskViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                taskViewModel.clearError()
            }
        }

        val apiToken = TokenManager.getToken(requireContext())
        if (apiToken != null) {
            taskViewModel.fetchTasks(apiToken)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
