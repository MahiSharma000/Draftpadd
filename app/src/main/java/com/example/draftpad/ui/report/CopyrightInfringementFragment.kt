package com.example.draftpad.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentCopyrightInfringementBinding

class CopyrightInfringementFragment : Fragment() {
    private var _binding: FragmentCopyrightInfringementBinding? = null
    private val binding get() = _binding!!
    private val vm: ReportViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCopyrightInfringementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rt = CopyrightInfringementFragmentArgs.fromBundle(requireArguments()).reportTypre
        val bi = CopyrightInfringementFragmentArgs.fromBundle(requireArguments()).bookId
        binding.apply {
            txtNotAnOwner.setOnClickListener {
                vm.postReport(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi,
                    rt,
                    txtNotAnOwner.text.toString()
                )
                Toast.makeText(requireContext(), "Reported", Toast.LENGTH_SHORT).show()
            }
            txtOwner.setOnClickListener {
                vm.postReport(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi,
                    rt,
                    txtOwner.text.toString()
                )
                Toast.makeText(requireContext(), "Reported", Toast.LENGTH_SHORT).show()
            }
        }
    }
}