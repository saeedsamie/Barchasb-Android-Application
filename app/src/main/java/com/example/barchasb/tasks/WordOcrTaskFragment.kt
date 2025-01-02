package com.example.barchasb.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentWordOcrTaskBinding

class WordOcrTaskFragment : Fragment() {

    private var _binding: FragmentWordOcrTaskBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordOcrTaskBinding.inflate(inflater, container, false)
        taskViewModel.selectedTask.observe(viewLifecycleOwner) { task ->
            if (task != null) {
                binding.taskID.text = task.id.toString()
                binding.taskTitle.text = task.title
                binding.taskDescription.text = task.description
            }
        }
        setupButtons()

        return binding.root
    }

    private fun setupButtons() {
        binding.submitButton.setOnClickListener {
            val inputText = binding.wordOcrEditText.text.toString()
            if (inputText.isNotBlank()) {
                // اینجا می‌توانید متن واردشده را ذخیره یا پردازش کنید
                binding.wordOcrEditText.setText("")
                Toast.makeText(context, "ثبت شد!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_wordOcrTaskFragment_to_taskListFragment)

            } else {
                Toast.makeText(context, "لطفاً متن را وارد کنید!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.skipButton.setOnClickListener {
            Toast.makeText(context, "رد شد", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_wordOcrTaskFragment_to_taskListFragment)
        }
        binding.exitButton.setOnClickListener {
            findNavController().navigate(R.id.action_wordOcrTaskFragment_to_taskListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
