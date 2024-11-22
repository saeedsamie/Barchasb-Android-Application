package com.example.barchasb.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskListAdapter: TaskListAdapter

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

        // لیست تسک‌ها
        val tasks = listOf(
            Task(
                "199123", "تبدیل متن به صوت", "با توجه به صوت زیر متن را اصلاح کنید", TaskType.ASR
            ), Task(
                "211",
                "استخراج متن کلمه از تصویر",
                "متن کلمه در تصویر را بنویسید",
                TaskType.WORD_OCR
            ), Task(
                "133243", "تبدیل متن به صوت", "با توجه به صوت زیر متن را اصلاح کنید", TaskType.ASR
            ), Task(
                "201",
                "استخراج متن کلمه از تصویر",
                "متن کلمه در تصویر را بنویسید",
                TaskType.WORD_OCR
            ), Task(
                "266",
                "استخراج متن کلمه از تصویر",
                "متن کلمه در تصویر را بنویسید",
                TaskType.WORD_OCR
            ), Task(
                "266332",
                "استخراج متن کلمه از تصویر",
                "متن کلمه در تصویر را بنویسید",
                TaskType.WORD_OCR
            ), Task(
                "264426",
                "استخراج متن کلمه از تصویر",
                "متن کلمه در تصویر را بنویسید",
                TaskType.WORD_OCR
            ), Task(
                "111357", "تبدیل متن به صوت", "با توجه به صوت زیر متن را اصلاح کنید", TaskType.ASR
            ), Task(
                "135321", "تبدیل متن به صوت", "با توجه به صوت زیر متن را اصلاح کنید", TaskType.ASR
            ), Task(
                "152235", "تبدیل متن به صوت", "با توجه به صوت زیر متن را اصلاح کنید", TaskType.ASR
            )
        )

        // تنظیم آداپتور
        taskListAdapter = TaskListAdapter(tasks) { task ->
            navigateToTask(task)
        }

        binding.taskRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.taskRecyclerView.adapter = taskListAdapter

        return binding.root
    }

    private fun navigateToTask(task: Task) {
        val bundle = Bundle().apply {
            putParcelable("task", task)
        }
        when (task.type) {
            TaskType.ASR -> findNavController().navigate(
                R.id.action_taskListFragment_to_asrTaskFragment, bundle
            )

            TaskType.WORD_OCR -> findNavController().navigate(
                R.id.action_taskListFragment_to_wordOcrTaskFragment, bundle
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
