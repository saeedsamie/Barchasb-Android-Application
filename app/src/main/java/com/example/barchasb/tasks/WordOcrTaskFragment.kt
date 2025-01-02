package com.example.barchasb.tasks

import TokenManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.barchasb.R
import com.example.barchasb.api.Report
import com.example.barchasb.api.Submission
import com.example.barchasb.databinding.FragmentWordOcrTaskBinding

class WordOcrTaskFragment : Fragment() {

    private var _binding: FragmentWordOcrTaskBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWordOcrTaskBinding.inflate(inflater, container, false)

        taskViewModel.selectedTask.observe(viewLifecycleOwner) { task ->
            if (task != null) {
                binding.taskID.text = task.id.toString()
                binding.taskTitle.text = task.title
                binding.taskDescription.text = task.description
            }
        }

        binding.submitButton.setOnClickListener {
            val inputText = binding.wordOcrEditText.text.toString()
            if (inputText.isNotBlank()) {
                val submission = Submission(
                    user_id = 1,
                    task_id = taskViewModel.selectedTask.value!!.id,
                    content = mapOf("recognized_word" to inputText)
                )
                val token = TokenManager.getToken(requireContext())
                taskViewModel.submitTask("Bearer $token", submission)
                Toast.makeText(context, "Submitted!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_wordOcrTaskFragment_to_taskListFragment)
            }
        }

        binding.reportTaskButton.setOnClickListener {
            val task = taskViewModel.selectedTask.value
            if (task != null) {
                val report = Report(task.id)
                val token = TokenManager.getToken(requireContext())
                taskViewModel.reportTask("Bearer $token", report)
                Toast.makeText(context, "Reported!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_wordOcrTaskFragment_to_taskListFragment)
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
