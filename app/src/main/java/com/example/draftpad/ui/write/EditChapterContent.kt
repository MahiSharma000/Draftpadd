package com.example.draftpad.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentEditChapterContentBinding
import com.example.draftpad.databinding.FragmentReadStoryBinding
import com.example.draftpad.ui.read.ReadStoryFragmentArgs
import com.example.draftpad.ui.read.ReadStoryViewModel

class EditChapterContent : Fragment() {
    private var _binding: FragmentEditChapterContentBinding ?= null
    private val binding get() = _binding!!
    private val vm: NewStoryViewModel by activityViewModels()
    private var toolbar: Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditChapterContentBinding.inflate(inflater)
        binding.viewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.write_story_menu)
        var bookTitle = EditChapterContentArgs.fromBundle(requireArguments()).bookTitle
        var bookContent=EditChapterContentArgs.fromBundle(requireArguments()).bookDes
        var cat=EditChapterContentArgs.fromBundle(requireArguments()).category
        ReadStoryFragmentArgs.fromBundle(requireArguments()).chapterId.let {
            vm.setChapterId(it)
        }
        vm.chapterId.observe(viewLifecycleOwner) {
            vm.getChapterById()
        }
        toolbar?.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_save -> {
                    vm.updateChapter(
                        vm.chapter.value!!.id,
                        vm.chapter.value!!.book_Id,
                        binding.editchapterTitle.text.toString(),
                        binding.editchapterContent.text.toString(),
                        0,
                        vm.chapter.value!!.category_id,
                        vm.chapter.value!!.total_likes,
                        vm.chapter.value!!.total_comments,
                        Utils(requireContext()).getUser().id.toInt()
                    )

                    true
                }
                R.id.action_publish -> {
                    vm.updateChapter(
                        vm.chapter.value!!.id,
                        vm.chapter.value!!.book_Id,
                        binding.editchapterTitle.text.toString(),
                        binding.editchapterContent.text.toString(),
                        1,
                        vm.chapter.value!!.category_id,
                        vm.chapter.value!!.total_likes,
                        vm.chapter.value!!.total_comments,
                        Utils(requireContext()).getUser().id.toInt()
                    )
                    vm.createnewBook(
                        requireContext(),
                        bookTitle,
                        bookContent,
                        status = 1,
                        Utils(requireContext()).getUser().id.toInt(),
                        cat
                    )
                    true
                }
                R.id.action_preview -> {

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