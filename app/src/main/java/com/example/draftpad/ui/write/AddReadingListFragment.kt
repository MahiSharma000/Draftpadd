package com.example.draftpad.ui.write

import android.os.Bundle
import android.util.Log
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
import com.google.android.material.snackbar.Snackbar

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
        val userId = Utils(requireContext()).getUser().id.toInt()
        val bi = AddReadingListFragmentArgs.fromBundle(requireArguments()).bookId
        binding.apply {
            txtDownload.setOnClickListener {
                vm.downloadBook(
                    userId,
                    bi
                )
                vm.favouriteResponse.observe(viewLifecycleOwner) {
                    if (it != null) {
                        when (it.status) {
                            "error" -> {
                                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT)
                                    .show()
                            }
                            "success" -> {
                                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
            createReadingList.setOnClickListener {
                try {
                    vm.addreadlater(
                        userId,
                        bi
                    )

                    vm.readLater.observe(viewLifecycleOwner) {
                        if (it != null) {
                            when (it.status) {
                                "ERROR" ->
                                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT)
                                        .show()

                                "OK" -> {
                                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            txtRemoveReadLater.setOnClickListener {
                vm.deletereadLater(
                    userId,
                    bi
                )
                vm.deleteLater.observe(viewLifecycleOwner) {
                    if (it != null) {
                        when (it.status) {
                            "ERROR" -> {
                                Log.e("Delete", "${it.msg}")
                                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT)
                                    .show()
                            }
                            "OK" -> {
                                Log.e("Delete", "${it.msg}")
                                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }

            txtRemoveFavourites.setOnClickListener {
                vm.deletefavourite(
                    userId,
                    bi
                )
                vm.deletefav.observe(viewLifecycleOwner) {
                    if (it != null) {
                        when (it.status) {
                            "ERROR" ->
                                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT)
                                    .show()

                            "OK" -> {
                                Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                }
            }

        }
    }
}