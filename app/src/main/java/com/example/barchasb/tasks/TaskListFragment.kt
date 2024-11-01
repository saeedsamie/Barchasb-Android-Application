package com.example.barchasb.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barchasb.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // پیکربندی RecyclerView
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        // اینجا یک Adapter به RecyclerView اختصاص دهید
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
