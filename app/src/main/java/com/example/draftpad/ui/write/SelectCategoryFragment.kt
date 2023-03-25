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
import com.example.draftpad.databinding.FragmentSelectCategoryBinding
import com.example.draftpad.ui.search.CategoryAdapter
import com.example.draftpad.ui.search.SearchFragmentDirections
import com.example.draftpad.ui.search.SearchViewModel

class SelectCategoryFragment : Fragment() {
    private var _binding: FragmentSelectCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectCategoryBinding.inflate(inflater)
        binding.editCategoryViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.categories.observe(viewLifecycleOwner) { categories ->
                this.rveditcategory.layoutManager = LinearLayoutManager(context)
                this.rveditcategory.adapter = CategoryAdapter() { category ->
                    val dir = SelectCategoryFragmentDirections.actionSelectCategoryFragmentToCreateNewStoryFragment(category.id,category.name)
                    findNavController().navigate(dir)

                }
                Log.e("SearchFragment", categories.toString())
                (binding.rveditcategory.adapter as CategoryAdapter).submitList(categories)
            }
        }
    }

}