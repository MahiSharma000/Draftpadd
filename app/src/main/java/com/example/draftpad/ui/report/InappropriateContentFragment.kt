package com.example.draftpad.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.draftpad.R
import com.example.draftpad.Utils
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
            txtExplicitContent.setOnClickListener {
                vm.postReport(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi,
                    rt,
                    txtExplicitContent.text.toString()
                )

                Toast.makeText(requireContext(), "Reported", Toast.LENGTH_SHORT).show()
            }

            txtViolence.setOnClickListener {
                vm.postReport(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi,
                    rt,
                    txtViolence.text.toString()
                )
                Toast.makeText(requireContext(), "Reported", Toast.LENGTH_SHORT).show()
            }
            txtHate.setOnClickListener {
                vm.postReport(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi,
                    rt,
                    txtHate.text.toString()
                )
                Toast.makeText(requireContext(), "Reported", Toast.LENGTH_SHORT).show()
            }
            txtPersonalContent.setOnClickListener {
                vm.postReport(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi,
                    rt,
                    txtPersonalContent.text.toString()
                )
                Toast.makeText(requireContext(), "Reported", Toast.LENGTH_SHORT).show()
            }
            txtSelfHarm.setOnClickListener {
                vm.postReport(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi,
                    rt,
                    txtSelfHarm.text.toString()
                )
                Toast.makeText(requireContext(), "Reported", Toast.LENGTH_SHORT).show()
            }
            txtSpam.setOnClickListener {
                vm.postReport(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi,
                    rt,
                    txtSpam.text.toString()
                )
                Toast.makeText(requireContext(), "Reported", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

    }
}