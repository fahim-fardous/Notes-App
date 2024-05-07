package com.example.planner.screens.notes.details


import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.planner.R
import com.example.planner.databinding.FragmentDetailsScreenBinding
import com.example.planner.models.Notes
import com.example.planner.screens.notes.add.AppViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Date


class DetailsScreen : Fragment(R.layout.fragment_details_screen) {
    private lateinit var priority: String
    private lateinit var binding: FragmentDetailsScreenBinding
    private val viewModel: AppViewModel by viewModels()
    private val args: DetailsScreenArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsScreenBinding.bind(view)

        //initViews()
        initListeners()
        loadData()
    }

    private fun initObserver() {
        viewModel.eventSuccess.observe(this) { isSuccess ->
            isSuccess?.run {
                if (isSuccess) {
                    findNavController().popBackStack()
                }
            }
        }
        viewModel.note.observe(this) { item ->
            item?.run {
                binding.titleEt.setText(item.title)
                binding.subtitleEt.setText(item.subTitle)
                priority = item.priority
                when (item.priority) {
                    "1" -> {
                        priority = "1"
                        binding.roundGreenBtn.setImageResource(R.drawable.round_shape_green_border)
                        binding.roundYellowBtn.setImageResource(R.drawable.round_shape_yellow)
                        binding.roundRedBtn.setImageResource(R.drawable.round_shape_red)
                    }

                    "2" -> {
                        priority = "2"
                        binding.roundGreenBtn.setImageResource(R.drawable.round_shape_green)
                        binding.roundYellowBtn.setImageResource(R.drawable.round_shape_yellow_border)
                        binding.roundRedBtn.setImageResource(R.drawable.round_shape_red)
                    }

                    "3" -> {
                        priority = "3"
                        binding.roundGreenBtn.setImageResource(R.drawable.round_shape_green)
                        binding.roundYellowBtn.setImageResource(R.drawable.round_shape_yellow)
                        binding.roundRedBtn.setImageResource(R.drawable.round_shape_red_border)
                    }
                }
                binding.descriptionEt.setText(item.notes)

            }
        }

    }

    private fun initViews() {

    }

    private fun initListeners() {

        binding.editFab.setOnClickListener {
            viewModel.updateNote(
                Notes(
                    id = args.noteId,
                    title = binding.titleEt.text.toString(),
                    subTitle = binding.subtitleEt.text.toString(),
                    priority = priority,
                    date = DateFormat.format("d MMMM, yyyy", Date().time).toString(),
                    notes = binding.descriptionEt.text.toString()
                )
            )
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

        binding.topBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.deleteFab.setOnClickListener {
            val bottomSheet = BottomSheetDialog(requireContext())
            bottomSheet.setContentView(R.layout.dialog_delete)

            val yesButton = bottomSheet.findViewById<TextView>(R.id.yes_btn)
            val noButton = bottomSheet.findViewById<TextView>(R.id.no_btn)

            yesButton?.setOnClickListener {
                viewModel.deleteNote(args.noteId)
                bottomSheet.dismiss()
            }
            noButton?.setOnClickListener {
                bottomSheet.dismiss()
            }
            bottomSheet.show()
        }
    }

    private fun loadData() {
        viewModel.getNote(args.noteId)
    }


}