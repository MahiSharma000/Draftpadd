package com.example.draftpad.ui.read

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentReadStoryBinding


class ReadStoryFragment : Fragment() {
    private val vm: ReadStoryViewModel by activityViewModels()
    private var _binding: FragmentReadStoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReadStoryBinding.inflate(inflater)
        binding.viewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ReadStoryFragmentArgs.fromBundle(requireArguments()).chapterId.let {
            vm.setChapterId(it)
        }
        vm.chapterId.observe(viewLifecycleOwner) {
            vm.getChapterById()
        }
        binding.apply {
            imgComments.setOnClickListener {
                val chapter_id = vm.chapterId.value!!
                val dir =
                    ReadStoryFragmentDirections.actionReadStoryFragmentToCommentFragment(chapter_id)
                findNavController().navigate(dir)
            }
            imgNoAd.setOnClickListener {
                findNavController().navigate(R.id.action_readStoryFragment_to_premiumFragment)
            }
            imgShare.setOnClickListener {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_SUBJECT, "Check out this cool application")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Your friend has sent you this interesting story Read it on Draftpad!"
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            imgVote.setOnClickListener {

                imgVote.setImageResource(R.drawable.baseline_star_24)
                vm.updateChapter(
                    vm.chapter.value!!.id,
                    vm.chapter.value!!.book_Id,
                    vm.chapter.value!!.title,
                    vm.chapter.value!!.content,
                    1,
                    vm.chapter.value!!.category_id,
                    vm.chapter.value!!.total_likes.plus(1),
                    vm.chapter.value!!.total_comments,
                    Utils(requireContext()).getUser().id.toInt()
                )

            }

        }
    }

    companion object {

    }
}