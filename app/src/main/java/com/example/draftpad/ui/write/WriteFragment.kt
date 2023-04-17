package com.example.draftpad.ui.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentWriteBinding

class WriteFragment : Fragment() {

    private var _binding: FragmentWriteBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        toolbar = binding.toolbar
        toolbar?.inflateMenu(R.menu.write_menu)
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.user_profile -> {
                    findNavController().navigate(R.id.action_navigation_write_to_userProfileFragment)
                    true
                }
                else -> false
            }
        }
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            txtCreateNewStory.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_write_to_selectCategoryFragment)
            }
            txtEditStory.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_write_to_editStoryFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}