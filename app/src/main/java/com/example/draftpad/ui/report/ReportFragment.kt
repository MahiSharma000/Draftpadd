package com.example.draftpad.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.draftpad.databinding.FragmentReportBinding

class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bi=ReportFragmentArgs.fromBundle(requireArguments()).bookId
        binding.apply {
            txtInappropriate.setOnClickListener {
                val dir=ReportFragmentDirections.actionReportFragmentToInappropriateContentFragment(bi,txtInappropriate.text.toString())
                findNavController().navigate(dir)
            }
            txtCopyright.setOnClickListener {
                val dir=ReportFragmentDirections.actionReportFragmentToCopyrightInfringementFragment(bi,txtCopyright.text.toString())
                findNavController().navigate(dir)
            }
        }
    }
}