package com.example.draftpad.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.auth.AuthSignUpViewModel
import com.example.draftpad.databinding.FragmentCopyrightInfringementBinding

class CopyrightInfringementFragment : Fragment() {
    private var _binding: FragmentCopyrightInfringementBinding? = null
    private val binding get() = _binding!!
    private val vm: ReportViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCopyrightInfringementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rt=CopyrightInfringementFragmentArgs.fromBundle(requireArguments()).reportTypre
        val bi=CopyrightInfringementFragmentArgs.fromBundle(requireArguments()).bookId
        binding.apply {
            reportBtn.setOnClickListener {
            }
        }
    }

    companion object {

    }
}