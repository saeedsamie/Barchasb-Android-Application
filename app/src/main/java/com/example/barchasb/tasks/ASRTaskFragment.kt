package com.example.barchasb.tasks

import TokenManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.barchasb.R
import com.example.barchasb.api.Report
import com.example.barchasb.api.Submission
import com.example.barchasb.databinding.FragmentAsrTaskBinding
import java.util.concurrent.TimeUnit

class ASRTaskFragment : Fragment() {

    private var _binding: FragmentAsrTaskBinding? = null
    private val binding get() = _binding!!

    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isPlaying = false
    private var isMediaPlayerReleased = true // کنترل وضعیت آزادسازی MediaPlayer

    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAsrTaskBinding.inflate(inflater, container, false)

        taskViewModel.selectedTask.observe(viewLifecycleOwner) { task ->
            if (task != null) {
                binding.taskID.text = task.id.toString()
                binding.taskTitle.text = task.title
                binding.taskDescription.text = task.description
            }
        }

        binding.submitButton.setOnClickListener {
            val inputText = binding.editableText.text.toString()
            if (inputText.isNotBlank()) {
                val submission = Submission(
                    user_id = 1,
                    task_id = taskViewModel.selectedTask.value!!.id,
                    content = mapOf("transcription" to inputText)
                )
                val token = TokenManager.getToken(requireContext())
                taskViewModel.submitTask("Bearer $token", submission)
                Toast.makeText(context, "Submitted!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.asrTaskFragment_to_action_taskListFragment)
            }
        }

        binding.reportTaskButton.setOnClickListener {
            val task = taskViewModel.selectedTask.value
            if (task != null) {
                val report = Report(task.id)
                val token = TokenManager.getToken(requireContext())
                taskViewModel.reportTask("Bearer $token", report)
                Toast.makeText(context, "Reported!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.asrTaskFragment_to_action_taskListFragment)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emotions = resources.getStringArray(R.array.emotion_options)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, emotions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.emotionSpinner.adapter = adapter

        val languages = resources.getStringArray(R.array.language_options)
        val languageAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languageSpinner.adapter = languageAdapter

        initializeMediaPlayer()

        // دکمه Play/Pause
        binding.playPauseButton.setOnClickListener { toggleAudio() }

        // به‌روزرسانی موقعیت SeekBar و زمان
        handler.post(updateSeekBar)

        // اجازه به کاربر برای تغییر موقعیت پخش از طریق SeekBar
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    binding.currentTimeTextView.text = formatTime(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initializeMediaPlayer() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.asr_sample).apply {
            isMediaPlayerReleased = false
            binding.seekBar.max = duration
            binding.totalTimeTextView.text = formatTime(duration) // به‌روزرسانی زمان کل
        }
    }

    private val updateSeekBar = object : Runnable {
        override fun run() {
            if (isMediaPlayerReleased || mediaPlayer == null) {
                // اگر mediaPlayer آزاد شده، از اجرای دوباره جلوگیری می‌شود
                handler.removeCallbacks(this)
                return
            }

            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    binding.seekBar.progress = player.currentPosition
                    binding.currentTimeTextView.text = formatTime(player.currentPosition)
                }
                handler.postDelayed(this, 1000) // به‌روزرسانی هر ثانیه
            }
        }
    }

    private fun toggleAudio() {
        if (isPlaying) {
            pauseAudio()
        } else {
            startAudio()
        }
    }

    private fun startAudio() {
        if (!isMediaPlayerReleased) {
            mediaPlayer?.start()
            binding.playPauseButton.text = "Pause"
            isPlaying = true
            handler.post(updateSeekBar) // اطمینان از شروع به‌روزرسانی
        }
    }

    private fun pauseAudio() {
        if (!isMediaPlayerReleased) {
            mediaPlayer?.pause()
            binding.playPauseButton.text = "Start"
            isPlaying = false
        }
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds.toLong()) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateSeekBar) // متوقف کردن به‌روزرسانی
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMediaPlayer()
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        isMediaPlayerReleased = true // به‌روزرسانی وضعیت پس از آزادسازی
        handler.removeCallbacks(updateSeekBar) // توقف کامل updateSeekBar
    }

    override fun onPause() {
        super.onPause()
        releaseMediaPlayer()
    }
}
