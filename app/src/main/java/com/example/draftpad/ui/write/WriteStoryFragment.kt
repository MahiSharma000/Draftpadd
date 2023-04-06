package com.example.draftpad.ui.write

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentWriteStoryBinding

class WriteStoryFragment : Fragment() {
    private var _binding: FragmentWriteStoryBinding? = null
    private val binding get() = _binding!!
    private var toolbar: Toolbar? = null
    private val viewModel : WriteViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_story, container, false)
        binding.writevm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bi=WriteStoryFragmentArgs.fromBundle(requireArguments()).bookId
        val cat = WriteStoryFragmentArgs.fromBundle(requireArguments()).categoryId
        binding.toolbar.inflateMenu(R.menu.write_story_menu)
        binding.apply {
            var status : Int
            val title = chapterTitle.text.toString()
            val content = chapterContent.text.toString()
            val bookTitle = WriteStoryFragmentArgs.fromBundle(requireArguments()).bookTitle
            val bookContent = WriteStoryFragmentArgs.fromBundle(requireArguments()).bookDescription
            toolbar.setOnMenuItemClickListener { item ->

                    when (item.itemId) {
                        R.id.action_save -> {
                            if(checkFields() == true){
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Error")
                                    .setMessage("Please fill all the fields")
                                    .setPositiveButton("OK") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    .show()
                            }
                            else{
                                viewModel?.createNewChapter(
                                    bi,
                                    title,
                                    content,
                                    status = 0,
                                    cat ,
                                    Utils(requireContext()).getUser().id.toInt(),
                                )
                                findNavController().navigate(R.id.action_writeStoryFragment_to_navigation_write)
                            }

                            true
                        }
                        R.id.action_publish -> {
                            if(checkFields()==true){
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Error")
                                    .setMessage("Please fill all the fields")
                                    .setPositiveButton("OK") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    .show()
                            }
                            else{Toast.makeText(context, "Published ${bi}", Toast.LENGTH_SHORT).show()
                                viewModel.createNewChapter(
                                    bi,
                                    chapterTitle.text.toString(),
                                    chapterContent.text.toString(),
                                    status = 1,
                                    cat,
                                    Utils(requireContext()).getUser().id.toInt(),
                                )
                                viewModel.createnewBook(
                                    requireContext(),
                                    bi,
                                    bookTitle,
                                    bookContent,
                                    status = 1,
                                    Utils(requireContext()).getUser().id.toInt(),
                                    cat
                                )
                                findNavController().navigate(R.id.action_writeStoryFragment_to_navigation_write)}


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
    fun checkFields(): Boolean {
        return binding.chapterTitle.text.toString().isEmpty() || binding.chapterContent.text.toString().isEmpty()
    }

}