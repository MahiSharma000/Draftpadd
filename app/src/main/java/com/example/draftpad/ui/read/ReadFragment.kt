package com.example.draftpad.ui.read

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentBooksBinding
import com.example.draftpad.databinding.FragmentReadBinding
import com.example.draftpad.ui.search.BookAdapter
import com.example.draftpad.ui.search.BookViewModel
import com.example.draftpad.ui.search.BooksFragmentDirections


class ReadFragment : Fragment() {

    private val vm: ReadViewModel by activityViewModels()
    private var _binding: FragmentReadBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReadBinding.inflate(inflater)
        binding.readVm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.setBookId(arguments?.getInt("book") ?: 1)
        vm.getSelectedBook()

    }

}
