package com.example.draftpad.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentWriteStoryBinding

class WriteStoryFragment : Fragment() {
    private var _binding: FragmentWriteStoryBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null
    private var viewModel: WriteViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_story, container, false)
        binding.writevm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.write_story_menu)
        binding.apply {
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_save -> {
                        true
                    }
                    R.id.action_publish -> {
                        true
                    }
                    R.id.action_preview -> {
                        findNavController().navigate(R.id.action_writeStoryFragment_to_readStoryFragment)
                        true
                    }
                    R.id.action_delete -> {
                        true
                    }
                    else -> false
                }
            }
        }
    }

}