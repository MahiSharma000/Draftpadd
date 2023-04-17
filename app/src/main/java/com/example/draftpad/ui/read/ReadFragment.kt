package com.example.draftpad.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.draftpad.R
import com.example.draftpad.databinding.FragmentReadBinding

@Suppress("UNREACHABLE_CODE")
class ReadFragment : Fragment() {
    private val vm: ReadViewModel by activityViewModels()
    private var _binding: FragmentReadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReadBinding.inflate(inflater)
        binding.readVm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ReadFragmentArgs.fromBundle(requireArguments()).bookId.let {
            vm.setBookId(it)
        }
        binding.toolbar.inflateMenu(R.menu.report_menu)
        vm.bookId.observe(viewLifecycleOwner) {
            vm.getSelectedBook()
        }

        binding.apply {

            btnRead.setOnClickListener {
                val action =
                    ReadFragmentDirections.actionReadFragmentToChapterFragment((vm.book.value?.id ?: 1))
                findNavController().navigate(action)
            }
            txtAuthorProfile.setOnClickListener {
                val dir =ReadFragmentDirections.actionReadFragmentToAuthorProfileFragment(vm.book.value!!.user_id )
                findNavController().navigate(dir)
            }
            imgAdd.setOnClickListener {
               val dir = ReadFragmentDirections.actionReadFragmentToAddReadingListFragment((vm.book.value?.id ?: 1))
                findNavController().navigate(dir)

            }
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.report -> {
                        val dir= ReadFragmentDirections.actionReadFragmentToReportFragment(vm.book.value!!.id!!)
                        findNavController().navigate(dir)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
