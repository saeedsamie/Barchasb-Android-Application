package com.example.barchasb.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.barchasb.R
import com.example.barchasb.databinding.FragmentSignUpBinding
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        // Populate education spinner
        val educationLevels = resources.getStringArray(R.array.education_options)
        val educationAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, educationLevels)
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.educationSpinner.adapter = educationAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle login redirect
        binding.loginRedirect.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        // Handle signup button click
        binding.signupButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val secondTimePassword = binding.secondTimePasswordEditText.text.toString().trim()
            val phone = binding.phone.text.toString().trim()
            val age = binding.age.text.toString().trim()
            val languages = binding.userLanguages.text.toString().trim()
            val education = binding.educationSpinner.selectedItem.toString()

            if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || age.isEmpty() || languages.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            } else if (secondTimePassword != password) {
                Toast.makeText(
                    requireContext(),
                    "Entered passwords are different",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                authViewModel.signup(username, password, phone, age, languages, education)
            }
        }

        // Observe signup state
        observeSignupState()
    }

    private fun observeSignupState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.signupState.collect { state ->
                    when (state) {
                        is SignupState.Idle -> {
                            // No action needed
                        }

                        is SignupState.Loading -> {
                            Toast.makeText(context, "Signing up...", Toast.LENGTH_SHORT).show()
                        }

                        is SignupState.Success -> {
                            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                        }

                        is SignupState.Error -> {
                            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
