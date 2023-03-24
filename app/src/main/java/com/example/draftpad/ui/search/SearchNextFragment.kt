package com.example.draftpad.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.databinding.FragmentSearchNextBinding

class SearchNextFragment : Fragment() {
    private val viewModel: SearchViewModel by activityViewModels()
    private var _binding: FragmentSearchNextBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchNextBinding.inflate(inflater)
        //binding.viewModel = viewModel
        //binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding.txtsearchByName.addTextChangedListener {
            val action =
                SearchNextFragmentDirections.actionSearchNextFragmentToProfileFragment(it.toString())
            findNavController().navigate(action)
        }*/
    }
    //function that return name passed in the argument
    fun getName(): String {
        return binding.txtsearchByName.text.toString()
    }
}

