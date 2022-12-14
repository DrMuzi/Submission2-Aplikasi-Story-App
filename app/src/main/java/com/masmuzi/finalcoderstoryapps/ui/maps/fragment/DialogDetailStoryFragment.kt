package com.masmuzi.finalcoderstoryapps.ui.maps.fragment

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.masmuzi.finalcoderstoryapps.R
import com.masmuzi.finalcoderstoryapps.databinding.FragmentDialogDetailStoryBinding

class DialogDetailStoryFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDialogDetailStoryBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDialogDetailStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = DialogDetailStoryFragmentArgs.fromBundle(arguments as Bundle)

        binding.include.apply {
            storyName.text = bundle.name
            storyDescription.text = bundle.description

            Glide.with(requireActivity())
                .load(bundle.image)
                .placeholder(R.drawable.ic_sample_image)
                .error(R.drawable.ic_sample_image)
                .into(storyImage)

            storyImage.setOnClickListener {
                val toDetailImageFragment =
                    DialogDetailStoryFragmentDirections.actionDialogDetailStoryFragmentToDetailImageFragment2(
                        bundle.image
                    )
                findNavController().navigate(toDetailImageFragment)
            }

            var isExpanded = false

            tvDetail.setOnClickListener {
                TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
                when (isExpanded) {
                    true -> {
                        storyDescription.visibility = View.GONE
                        tvDetail.text = getString(R.string.detail)
                        tvDetail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_keyboard_arrow_down_24, 0, 0, 0)
                        isExpanded = false
                    }
                    false -> {
                        storyDescription.visibility = View.VISIBLE
                        tvDetail.text = getString(R.string.close)
                        tvDetail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_keyboard_arrow_up_24, 0, 0, 0)
                        isExpanded = true
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}