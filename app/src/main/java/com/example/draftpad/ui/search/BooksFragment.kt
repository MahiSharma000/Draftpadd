package com.example.draftpad.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.databinding.FragmentBooksBinding

class BooksFragment : Fragment() {
    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!
    private val vm: BooksViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBooksBinding.inflate(inflater)
        binding.booksvm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val catId = BooksFragmentArgs.fromBundle(requireArguments()).category
        vm.getBooks(catId)
        binding.apply {
            vm.bookList.observe(viewLifecycleOwner) { books ->
                this.rvBooks.layoutManager = LinearLayoutManager(context)
                this.rvBooks.adapter = BookAdapter() { book ->
                    val dir =
                        BooksFragmentDirections.actionBooksFragmentToReadFragment(book.id)
                    findNavController().navigate(dir)
                }
                (binding.rvBooks.adapter as BookAdapter).submitList(books)
            }
        }
    }
}

