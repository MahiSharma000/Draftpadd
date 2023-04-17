package com.example.draftpad.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        binding.searchViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.categories.observe(viewLifecycleOwner) { categories ->
                this.rvCategory.layoutManager = LinearLayoutManager(context)
                this.rvCategory.adapter = CategoryAdapter() { category ->
                    val dir =
                        SearchFragmentDirections.actionNavigationSearchToBooksFragment(category.name, category.id)
                    findNavController().navigate(dir)
                }
                (binding.rvCategory.adapter as CategoryAdapter).submitList(categories)
            }

            txtsearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER) || (keyCode == KeyEvent.KEYCODE_SEARCH)) {
                    val query = txtsearch.text.toString()
                    viewModel.setQuery(query)
                    findNavController().navigate(R.id.action_navigation_search_to_searchNextFragment)
                    return@OnKeyListener true
                }
                false
            })
        }
    }
}