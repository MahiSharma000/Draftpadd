package com.example.draftpad.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentEditChapterContentBinding

class EditChapterContent : Fragment() {
    private var _binding: FragmentEditChapterContentBinding ?= null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditChapterContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

    }
}