package com.example.barchasb.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.barchasb.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            Toast.makeText(context, "انجام شد", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        binding.skipButton.setOnClickListener {
            Toast.makeText(context, "رد شد", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
