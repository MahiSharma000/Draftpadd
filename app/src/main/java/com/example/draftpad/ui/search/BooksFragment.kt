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

    private val vm: BookViewModel by activityViewModels()
    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBooksBinding.inflate(inflater)
        binding.bookvm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.setCategoryId(arguments?.getInt("category") ?: 1)
        vm.getBooksOfCategory()
        vm.books.observe(viewLifecycleOwner) { books ->
            binding.rvBook.layoutManager = LinearLayoutManager(context)
            binding.rvBook.adapter = BookAdapter() { book ->
                Log.e("BooksFragment", book.toString())
                (binding.rvBook.adapter as BookAdapter).notifyDataSetChanged()
                val dir = BooksFragmentDirections.actionBooksFragmentToReadFragment(book.id)
                findNavController().navigate(dir)
            }
            Log.e("BooksFragment", books.toString())
            (binding.rvBook.adapter as BookAdapter).submitList(books)
        }

    }
}