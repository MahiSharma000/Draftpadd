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
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentChapterBinding
import com.example.draftpad.databinding.FragmentEditChaptersBinding
import com.example.draftpad.ui.read.ChapterAdapter
import com.example.draftpad.ui.read.ChapterFragmentDirections
import com.example.draftpad.ui.read.ChapterViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditChaptersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditChaptersFragment : Fragment() {
    private val vm: ChapterViewModel by activityViewModels()
    private var _binding: FragmentEditChaptersBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

            }
            Log.e("ChapterFragment", chapters.toString())
            (binding.rvEditChapters.adapter as ChapterAdapter).submitList(chapters)
            binding.rvEditChapters.hasFixedSize()

        }
    }


}