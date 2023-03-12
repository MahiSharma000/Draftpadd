package com.example.draftpad.ui.read

import android.app.AlertDialog
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
import com.example.draftpad.auth.AuthApiStatus
import com.example.draftpad.databinding.FragmentBooksBinding
import com.example.draftpad.databinding.FragmentCommentBinding
import com.example.draftpad.ui.search.BookAdapter
import com.example.draftpad.ui.search.BookViewModel
import com.example.draftpad.ui.search.BooksFragmentDirections


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
        CommentFragmentArgs.fromBundle(requireArguments()).chapterId.let{
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
                if(txtComment.text.isEmpty()){
                    imgSend.isEnabled = false
                }
                else{
                    imgSend.isEnabled = true
                    txtComment.text.toString()
                }

            }
            vm.status.observe(viewLifecycleOwner) {
                when (it) {
                    CommentApiStatus.LOADING -> {
                        binding.imgSend.isEnabled = false
                    }
                    CommentApiStatus.DONE -> {
                        binding.imgSend.isEnabled = true
                        findNavController().navigate(R.id.action_signUpFragment_to_authFragment)
                    }
                    CommentApiStatus.ERROR -> {
                        binding.imgSend.isEnabled = true
                    }
                    else -> {
                        binding.imgSend.isEnabled = true
                    }
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