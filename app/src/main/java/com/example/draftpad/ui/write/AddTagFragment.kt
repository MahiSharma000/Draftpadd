package com.example.draftpad.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentAddTagBinding

class AddTagFragment : Fragment() {
    private var _binding: FragmentAddTagBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTagBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.toolbar?.inflateMenu(R.menu.add_tag_menu)
        toolbar?.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.tagsSave -> {
                    true
                }
                else -> false
            }
        }
        return root
    }
}