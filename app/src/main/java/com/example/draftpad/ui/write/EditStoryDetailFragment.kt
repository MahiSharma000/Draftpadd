package com.example.draftpad.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentEditStoryDetailBinding

class EditStoryDetailFragment : Fragment() {
    private var _binding: FragmentEditStoryDetailBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditStoryDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.toolbar.inflateMenu(R.menu.edit_story_detail_menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_add -> {
                    findNavController().navigate(R.id.action_editStoryDetailFragment_to_writeStoryFragment)
                    true
                }
                R.id.action_view_as_reader -> {

                    true
                }
                R.id.delete -> {

                    true
                }
                else -> false
            }
        }
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            editChapter.setOnClickListener {
                findNavController().navigate(R.id.action_editStoryDetailFragment_to_editChaptersFragment)
            }
            txtEditCategory.setOnClickListener {
                findNavController().navigate(R.id.action_editStoryDetailFragment_to_selectCategoryFragment)
            }
            txtEditBookDescription.setOnClickListener {
                findNavController().navigate(R.id.action_editStoryDetailFragment_to_bookDescriptionFragment)
            }
            txtTags.setOnClickListener {
                findNavController().navigate(R.id.action_editStoryDetailFragment_to_addTagFragment)
            }
        }
    }

}