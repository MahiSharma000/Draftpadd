package com.example.draftpad.ui.write

import android.app.AlertDialog
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
import com.example.draftpad.databinding.FragmentAddNewChapterBinding

class AddNewChapter : Fragment() {
    private var _binding: FragmentAddNewChapterBinding? = null
    private val binding get() = _binding!!
    private val vm : WriteViewModel by activityViewModels()
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewChapterBinding.inflate(inflater)
        binding.writevm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bi= AddNewChapterArgs.fromBundle(requireArguments()).bookId.let {
            vm.setBookId(it)
        }
        vm.bookId.observe(viewLifecycleOwner) {
            vm.getBook()
        }

        binding.toolbar.inflateMenu(R.menu.write_story_menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
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
                        vm.createNewChapter(
                            vm.book.value!!.id,
                            binding.newchapterTitle.text.toString(),
                            binding.newchapterContent.text.toString(),
                        0,
                            vm.book.value!!.category_id,
                            Utils(requireContext()).getUser().id.toInt(),
                        )
                        Toast.makeText(requireContext(), "Chapter Saved", Toast.LENGTH_SHORT).show()
                        //findNavController().navigate(R.id.action_addNewChapter_to_editStoryDetailFragment2)

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
                    else{
                        vm.createNewChapter(
                            vm.book.value!!.id,
                            binding.newchapterTitle.text.toString(),
                            binding.newchapterContent.text.toString(),
                            1,
                            vm.book.value!!.category_id,
                            Utils(requireContext()).getUser().id.toInt(),
                        )

                        vm.createnewBook(
                            requireContext(),
                            vm.book.value!!.id,
                            vm.book.value!!.title,
                            vm.book.value!!.description,
                            1,
                            Utils(requireContext()).getUser().id.toInt(),
                            vm.book.value!!.category_id,

                        )

                        Toast.makeText(requireContext(), "Chapter Published", Toast.LENGTH_SHORT).show()
                        //findNavController().navigate(R.id.action_addNewChapter_to_editStoryDetailFragment2)
                    }


                    true
                }
                R.id.action_preview -> {
                    findNavController().navigate(R.id.action_writeStoryFragment_to_readStoryFragment)
                    true
                }
                R.id.action_delete -> {
                    true
                }
                else -> false
            }

        }

    }
    private fun checkFields(): Boolean {
        return binding.newchapterTitle.text.toString().isEmpty() || binding.newchapterContent.text.toString().isEmpty()
    }

    companion object {

    }
}