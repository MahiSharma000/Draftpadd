package com.example.draftpad.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.databinding.FragmentBlankcategoryBinding
import com.example.draftpad.ui.search.CategoryAdapter
import com.example.draftpad.ui.search.SearchViewModel

class Blankcategory : Fragment() {
    private var _binding: FragmentBlankcategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlankcategoryBinding.inflate(inflater)
        binding.editCategoryViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookId = BlankcategoryArgs.fromBundle(requireArguments()).bookId
        val title = BlankcategoryArgs.fromBundle(requireArguments()).bookTitle
        val description = BlankcategoryArgs.fromBundle(requireArguments()).bookDescription
        binding.apply {
            viewModel.categories.observe(viewLifecycleOwner) { categories ->
                this.rveditcategory.layoutManager = LinearLayoutManager(context)
                this.rveditcategory.adapter = CategoryAdapter() { category ->
                    val dir = BlankcategoryDirections.actionBlankcategoryToEditStoryDetailFragment(
                        bookId,
                        title,
                        description,
                        category.id,
                        category.name
                    )
                    findNavController().navigate(dir)

                }
                (binding.rveditcategory.adapter as CategoryAdapter).submitList(categories)
            }
        }
    }
}