package com.example.draftpad.ui.read

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.databinding.FragmentChapterBinding


class ChapterFragment : Fragment() {
    private val vm: ChapterViewModel by activityViewModels()
    private var _binding: FragmentChapterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChapterBinding.inflate(inflater)
        binding.chaptervm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ChapterFragmentArgs.fromBundle(requireArguments()).storyId.let{
            vm.setBookId(it)
        }
        vm.bookId.observe(viewLifecycleOwner) {
            vm.getChapterOfBook()
        }
        vm.chapter.observe(viewLifecycleOwner) { chapters ->
            binding.rvChapter.layoutManager = LinearLayoutManager(context)
            binding.rvChapter.adapter = ChapterAdapter() { chapter ->
                val dir =
                    ChapterFragmentDirections.actionChapterFragmentToReadStoryFragment(chapter.id)
                findNavController().navigate(dir)
            }
            Log.e("ChapterFragment", chapters.toString())
            (binding.rvChapter.adapter as ChapterAdapter).submitList(chapters)
        }
    }
}