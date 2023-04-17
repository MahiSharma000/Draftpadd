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
import com.example.draftpad.databinding.FragmentEditChaptersBinding
import com.example.draftpad.ui.read.ChapterAdapter
import com.example.draftpad.ui.read.ChapterViewModel


class EditChaptersFragment : Fragment() {
    private val vm: ChapterViewModel by activityViewModels()
    private var _binding: FragmentEditChaptersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditChaptersBinding.inflate(inflater)
        binding.viewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EditChaptersFragmentArgs.fromBundle(requireArguments()).bookId.let {
            vm.setBookId(it)
        }
        vm.bookId.observe(viewLifecycleOwner) {
            vm.getChapterOfBook()
        }
        vm.chapter.observe(viewLifecycleOwner) { chapters ->
            binding.rvEditChapters.layoutManager = LinearLayoutManager(context)
            binding.rvEditChapters.adapter = ChapterAdapter() { chapter ->
                val dir =
                    EditChaptersFragmentDirections.actionEditChaptersFragmentToEditChapterContent(
                        chapter.id,
                        chapter.book_Id
                    )
                findNavController().navigate(dir)
            }
            (binding.rvEditChapters.adapter as ChapterAdapter).submitList(chapters)
            binding.rvEditChapters.hasFixedSize()

        }
    }


}