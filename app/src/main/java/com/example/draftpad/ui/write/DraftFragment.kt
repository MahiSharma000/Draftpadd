package com.example.draftpad.ui.write

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
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentDraftBinding
import com.example.draftpad.ui.search.BookAdapter


class DraftFragment : Fragment() {
    private var _binding: FragmentDraftBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditStoryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDraftBinding.inflate(inflater, container, false)
        binding.draftvm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            binding.apply {
                val userId = Utils(requireContext()).getUser().id.toInt()
                viewModel.getBooksByStatus(userId, 0)
                viewModel.books.observe(viewLifecycleOwner) { books ->
                    Log.e("DraftFragment", books.toString())
                    draftrv.layoutManager = LinearLayoutManager(context)
                    draftrv.adapter = DraftAdapter() { book ->
                    }
                    Log.e("DraftFragment", books.toString())
                    (draftrv.adapter as DraftAdapter).submitList(books)
                }
            }
        }
    }

    companion object {
        fun newInstance() = DraftFragment()
    }
}