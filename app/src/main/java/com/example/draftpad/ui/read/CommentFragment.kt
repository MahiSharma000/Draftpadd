package com.example.draftpad.ui.read

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentCommentBinding
import com.google.android.material.snackbar.Snackbar


class CommentFragment : Fragment() {

    private val vm: CommentViewModel by activityViewModels()
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBinding.inflate(inflater)
        binding.commentVm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getUserProfile(Utils(requireContext()).getUser().id.toInt())
        val chapterId = CommentFragmentArgs.fromBundle(requireArguments()).chapterId
        chapterId.let {
            vm.setCommentId(it)
        }
        vm.comId.observe(viewLifecycleOwner) {
            vm.getAllComments()
        }

        vm.comments.observe(viewLifecycleOwner) { comments ->
            binding.rvComment.layoutManager = LinearLayoutManager(context)
            binding.rvComment.adapter = CommentAdapter() {}
            Log.e("CommentFragment", comments.toString())
            (binding.rvComment.adapter as CommentAdapter).submitList(comments)
        }

        binding.apply {
            imgSend.setOnClickListener {
                if (txtComment.text.isEmpty()) {
                    Snackbar.make(
                        requireView(),
                        "Please enter a comment",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    vm.createComment(
                        txtComment.text.toString(),
                        Utils(requireContext()).getUser().id.toInt(),
                        chapterId
                    )
                    Toast.makeText(requireContext(), "Comment posted", Toast.LENGTH_SHORT).show()
                    txtComment.text.clear()
                    vm.getAllComments()

                }

            }

        }
        vm.postResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it.status) {
                    "Success" -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("Success")
                            .setMessage(it.msg)
                            .setPositiveButton("OK") { dialog, which ->
                                dialog.dismiss()
                            }
                            .show()
                    }
                }
            }
        }
    }
}