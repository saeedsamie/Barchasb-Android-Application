package com.example.barchasb.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.barchasb.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : Fragment() {

    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // تنظیم دکمه‌ها برای انجام عملیات
        binding.approveButton.setOnClickListener {
            // عملیات تأیید
        }
        binding.rejectButton.setOnClickListener {
            // عملیات رد
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
