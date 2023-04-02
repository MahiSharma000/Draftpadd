package com.example.draftpad.ui.read

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        var flag = 0
        val uid = Utils(requireContext()).getUser().id.toInt()
        val chapter_id = ReadStoryFragmentArgs.fromBundle(requireArguments()).chapterId
        try {
            chapter_id.let {
                vm.setChapterId(it)
            }
            vm.chapterId.observe(viewLifecycleOwner) {
                vm.getChapterById()

            }
            vm.checkLikes(uid, chapter_id)
            if (vm.checkLike.value!!.status == "OK") {
                binding.imgVote.setImageResource(R.drawable.baseline_star_24)
                flag = 1
            } else {
                binding.imgVote.setImageResource(R.drawable.baseline_vote)
                flag = 0
            }
        } catch (e: Exception) {
            Log.d("Likes", "onViewCreated: ${e.message}")
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
                if (flag == 0) {
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
                        uid
                    )
                    vm.updateLikes(uid, chapter_id)
                    flag = 1
                } else {
                    imgVote.setImageResource(R.drawable.baseline_vote)
                    vm.updateChapter(
                        vm.chapter.value!!.id,
                        vm.chapter.value!!.book_Id,
                        vm.chapter.value!!.title,
                        vm.chapter.value!!.content,
                        1,
                        vm.chapter.value!!.category_id,
                        vm.chapter.value!!.total_likes - 1,
                        vm.chapter.value!!.total_comments,
                        uid
                    )
                    vm.deleteLikes(uid, chapter_id)
                    flag = 0
                }
            }
        }
    }
}