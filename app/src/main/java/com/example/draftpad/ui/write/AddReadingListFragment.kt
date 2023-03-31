package com.example.draftpad.ui.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.Utils
import com.example.draftpad.databinding.FragmentAddReadingListBinding
import com.example.draftpad.ui.read.ChapterViewModel
import com.example.draftpad.ui.read.ReadViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddReadingListFragment : BottomSheetDialogFragment() {
    private val vm: ReadViewModel by activityViewModels()
    private var _binding: FragmentAddReadingListBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddReadingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bi=AddReadingListFragmentArgs.fromBundle(requireArguments()).bookId
        binding.apply {
            txtDownload.setOnClickListener {
                vm.downloadBook(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi
                )
                saveBookOffline()
                //create toast
                Toast.makeText(requireContext(), "Book saved offline", Toast.LENGTH_SHORT).show()
            }
            createReadingList.setOnClickListener {
                vm.addreadlater(
                    Utils(requireContext()).getUser().id.toInt(),
                    bi
                )
                Toast.makeText(requireContext(), "Book added to read later", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // function to save book offline
    fun saveBookOffline(){
        // save book offline

    }

    companion object {
    }
}