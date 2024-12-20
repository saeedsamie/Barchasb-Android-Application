package com.example.barchasb.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ایجاد یک Callback برای مدیریت دکمه بازگشت
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }

        // اضافه کردن Callback به سیستم بازگشت
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val educationLevels = resources.getStringArray(R.array.education_options)
        val educationAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, educationLevels)
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.educationSpinner.adapter = educationAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // استفاده از View Binding برای دکمه‌ها
        binding.loginRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
