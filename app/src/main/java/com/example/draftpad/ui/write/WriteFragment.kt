package com.example.draftpad.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.draftpad.databinding.FragmentWriteBinding

class WriteFragment : Fragment() {

    private var _binding: FragmentWriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(WriteViewModel::class.java)

        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        dashboardViewModel.text.observe(viewLifecycleOwner) {
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            txtCreateNewStory.setOnClickListener {

            }
            txtEditStory.setOnClickListener {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}