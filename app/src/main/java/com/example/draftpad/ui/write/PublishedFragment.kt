package com.example.draftpad.ui.write

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentAuthBinding
import com.example.draftpad.databinding.FragmentEditStoryBinding
import com.example.draftpad.databinding.FragmentPublishedBinding
import com.example.draftpad.ui.search.BookAdapter


class PublishedFragment : Fragment() {
    private var _binding: FragmentPublishedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditStoryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPublishedBinding.inflate(inflater)
        binding.publishedvm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val userId = Utils(requireContext()).getUser().id.toInt()
            viewModel.getBooksByStatus(userId, 1)
            viewModel.books.observe(viewLifecycleOwner) { books ->
                rvPublished.layoutManager = LinearLayoutManager(context)
                rvPublished.adapter = BookAdapter() { book ->
                }
                Log.e("BooksFragment", books.toString())
                (rvPublished.adapter as BookAdapter).submitList(books)
            }

        }
    }

    companion object {
          fun newInstance() = PublishedFragment()
    }
}