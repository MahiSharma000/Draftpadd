package com.example.draftpad.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentInappropriateContentBinding


class InappropriateContentFragment : Fragment() {
    private var _binding: FragmentInappropriateContentBinding? = null
    private val binding get() = _binding!!
    private val vm: ReportViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInappropriateContentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rt=InappropriateContentFragmentArgs.fromBundle(requireArguments()).reportType
        val bi=InappropriateContentFragmentArgs.fromBundle(requireArguments()).bookId
        binding.apply {
            btnReport.setOnClickListener {

            }
        }
    }

    companion object {

    }
}