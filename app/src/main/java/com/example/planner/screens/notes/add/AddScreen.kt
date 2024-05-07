package com.example.planner.screens.notes.add

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.planner.R
import com.example.planner.databinding.FragmentAddScreenBinding
import com.example.planner.models.Notes
import java.util.Date


class AddScreen : Fragment(R.layout.fragment_add_screen) {
    private lateinit var binding: FragmentAddScreenBinding
    private val viewModel: AppViewModel by viewModels()
    private var priority: String = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        viewModel.showMessage.observe(this) {
            it?.run {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.eventSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                findNavController().popBackStack()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddScreenBinding.bind(view)

        initListeners()
    }

    private fun initListeners() {
        binding.addBtn.setOnClickListener {
            val title = binding.titleEt.text
            val subTitle = binding.subtitleEt.text
            val note = binding.descriptionEt.text
            val currDate = DateFormat.format("d MMMM, yyyy", Date().time)
            Log.d("date", "Current date is $currDate")

            viewModel.addNote(
                Notes(
                    0,
                    title.toString(),
                    subTitle.toString(),
                    note.toString(),
                    currDate.toString(),
                    priority
                )
            )
        }

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.roundGreenBtn.setOnClickListener {
            priority = "1"
            binding.roundGreenBtn.setImageResource(R.drawable.round_shape_green_border)
            binding.roundYellowBtn.setImageResource(R.drawable.round_shape_yellow)
            binding.roundRedBtn.setImageResource(R.drawable.round_shape_red)
        }

        binding.roundRedBtn.setOnClickListener {
            priority = "3"
            binding.roundGreenBtn.setImageResource(R.drawable.round_shape_green)
            binding.roundYellowBtn.setImageResource(R.drawable.round_shape_yellow)
            binding.roundRedBtn.setImageResource(R.drawable.round_shape_red_border)
        }

        binding.roundYellowBtn.setOnClickListener {
            priority = "2"
            binding.roundGreenBtn.setImageResource(R.drawable.round_shape_green)
            binding.roundYellowBtn.setImageResource(R.drawable.round_shape_yellow_border)
            binding.roundRedBtn.setImageResource(R.drawable.round_shape_red)
        }
    }


}