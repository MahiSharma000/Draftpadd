package com.example.draftpad.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentWriteStoryBinding

class WriteStoryFragment : Fragment() {
    private var _binding: FragmentWriteStoryBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWriteStoryBinding.inflate(inflater, container, false)
        binding.toolbar.inflateMenu(R.menu.write_story_menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_save -> {

                    true
                }
                R.id.action_publish -> {
                    true
                }
                R.id.action_preview -> {
                    findNavController().navigate(R.id.action_createNewStoryFragment_to_readStoryFragment)
                    true
                }
                R.id.action_delete -> {
                    true
                }
                else -> false
            }
        }
        return binding.root
    }

}